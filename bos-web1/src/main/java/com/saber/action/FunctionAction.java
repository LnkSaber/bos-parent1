package com.saber.action;

import com.saber.domain.Function;
import com.saber.service.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function> {
    @Autowired
    private FunctionService functionService;

    /**
     * 查询所有权限，返回json数据
     */
    public String listajax(){
        List<Function> list=functionService.findAll();
        this.javaJson(list,new String[]{"roles","parentFunction"});
        return NONE;
    }
    /**
     * 添加权限
     */
    public String add(){
        functionService.save(t);
        return "list";
    }

    /**
     * 权限分页查询
     */
    public String pageQuery(){
        String page =t.getPage();
        pageBean.setCurrentPage(Integer.parseInt(page));
        functionService.pageQuery(pageBean);
        this.javaJson(pageBean, new String[] {"roles","parentFunction","children"});
        return NONE;
    }

    /**
     * 根据当前登录用户人查询对应的菜单数据 ，返回json
     */
    public String findMenu(){
      List<Function> list=  functionService.findMenu();
      this.javaJson(list,new String[]{"roles","parentFunction","children"});
      return NONE;
    }
}
