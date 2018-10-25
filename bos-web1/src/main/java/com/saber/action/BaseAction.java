package com.saber.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.saber.domain.PageBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
    //模型对象
    public T t;
    @Override
    public T getModel() {
        return t;
    }


    /*重构分页=============S*/
    protected PageBean pageBean =new PageBean();
    //创建离线提交查询对象
    DetachedCriteria detachedCriteria =null;

    //属性驱动，接收页面提交的分页参数
    public void setPage(int page){
        pageBean.setCurrentPage(page);
    }
    public void serRows(int rows){
        pageBean.setPageSize(rows);
    }
    /*重构分页=============E*/

    /**
     * 将指定java对象转为json ，并相应到客户端页面
     *
     */
    public void javaJson(Object o,String [] excludes){
        //json数据到前台
        //使用json-lib将pageBean对象转为json ，通过输出流写回页面
        //JSONObject -- 将单一的对象转换为json
        //JSONArray --  将数组或者是集合对象转为json
        //指定哪一些属性不需要转json
        JsonConfig jsonConfig =new JsonConfig();
        jsonConfig.setExcludes(excludes);

        String json = JSONObject.fromObject(o, jsonConfig).toString();
        //设置utf-8
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        try {
            ServletActionContext.getResponse().getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 将指定javaList<对象>转为json ，并相应到客户端页面
     *
     */
    public void javaJson(List o, String[] exclueds) {
        //json数据到前台
        //使用json-lib将pageBean对象转为json ，通过输出流写回页面
        //JSONObject -- 将单一的对象转换为json
        //JSONArray --  将数组或者是集合对象转为json
        //指定哪一些属性不需要转json
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(exclueds);

        //String json = JSONObject.fromObject(pagaBean).toString();
        String json = JSONArray.fromObject(o,jsonConfig).toString();
        //设置utf-8
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        try {
            ServletActionContext.getResponse().getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //在构造方法中，动态获取实体类型，通过反射创建model对象
    public BaseAction(){
        //获得当前类型的 带有泛型的父类
        ParameterizedType pClass= (ParameterizedType) this.getClass().getGenericSuperclass();
        //获得运行期的泛型类型
        Class<T> entityClass = (Class<T>) pClass.getActualTypeArguments()[0];

        //  分页PageBean，创建离线查询对象
        detachedCriteria =DetachedCriteria.forClass(entityClass);
        pageBean.setDetachedCriteria(detachedCriteria);

        //通过反射创建对象
        try {
            t =entityClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
