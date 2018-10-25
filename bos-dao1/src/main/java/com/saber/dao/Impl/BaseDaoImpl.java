package com.saber.dao.Impl;

import com.saber.dao.BaseDao;
import com.saber.domain.PageBean;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {
    @Resource
    public void setMySessionFactory(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    //获得运行期的泛型 BaseDaoImpl<T>
    private Class clazz;

    public BaseDaoImpl() {
        //获得当前类型的 带有泛型的父类
        ParameterizedType pClass = (ParameterizedType) this.getClass().getGenericSuperclass();
        //获得运行期的泛型类型
        clazz = (Class) pClass.getActualTypeArguments()[0];
    }


    @Override
    public void save(T t) {
        this.getHibernateTemplate().save(t);
    }
    public void delete(Serializable id) {
        //先获取
        T t = this.getById(id);
        //再删
        getHibernateTemplate().delete(t);
    }

    public void delete(T t) {
        getHibernateTemplate().delete(t);
    }

    public void update(T t) {
        getHibernateTemplate().update(t);
    }

    public T getById(Serializable id) {
        return (T) getHibernateTemplate().get(clazz, id);
    }

    public void saveOrUpdate(T t) {
        this.getHibernateTemplate().saveOrUpdate(t);

    }

    public List<T> findAll() {
        String hql = "FROM "+clazz.getSimpleName();
        return (List<T>) this.getHibernateTemplate().find(hql);
    }

    public Integer getTotalCount(DetachedCriteria dc) {
        //设置查询的聚合函数 ，也就是总记录数
        dc.setProjection(Projections.rowCount());
        List<Long> list = (List<Long>) getHibernateTemplate().findByCriteria(dc);
        //清空之前设置的聚合函数
        dc.setProjection(null);

        if(list != null &&  list.size() > 0) {
            Long count = list.get(0);
            return count.intValue();
        }else {
            return null;
        }
    }

    public List<T> getList(DetachedCriteria dc, Integer start, Integer pageSize) {
        //firstResult :起始位置
        List<T> list = (List<T>) getHibernateTemplate().findByCriteria(dc, start, pageSize);
        return list;
    }

    /**
     * 通用分页查询方法
     */
    @Override
    public void pageQuery(PageBean pageBean) {
        int currentPage =pageBean.getCurrentPage();
        int pageSize =pageBean.getPageSize();

        //select count(*) from xxx;
        DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();

        detachedCriteria.setProjection(Projections.rowCount());

        //总数量
        List<Long> countList = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);

        Long count =countList.get(0);
        pageBean.setTotal(count.intValue());
        //清空离线查询的条件
        detachedCriteria.setProjection(null);

        //指定hibernate框架封装对象的方式
        detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);

        //查询 -- 当前分需要展示的数据集合
        int firstResult=(currentPage -1)*pageSize;
        int maxResults =pageSize;

        List rows =this.getHibernateTemplate().findByCriteria(detachedCriteria,firstResult,maxResults);

        pageBean.setRows(rows);
    }

    @Override
    public List<T> findByCriteria(DetachedCriteria dc) {
        return (List<T>) this.getHibernateTemplate().findByCriteria(dc);
    }
}
