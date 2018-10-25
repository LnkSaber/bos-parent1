package com.saber.action;

import com.saber.domain.Role;
import com.saber.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {

    @Autowired
    private RoleService roleService;

    //属性驱动，接收权限id
    private String functionid;

    public void setFunctionid(String functionid) {
        this.functionid = functionid;
    }
    /**
     * 添加角色
     * @return
     */
    public String add(){
        roleService.save(t,functionid);
        return "list";
    }
    /**
     * 分页查询
     */
    public String pageQuery(){
        roleService.pageQuery(pageBean);
        this.javaJson(pageBean,new String[]{"functions","users"});
        return NONE;
    }
    /**
     * 查询所有角色数据
     */
    public String listajax(){
        List<Role> list=roleService.findAll();
        this.javaJson(list,new String[]{"functions","users"});
        return NONE;
    }

}
