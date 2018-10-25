package com.saber.service;

import com.saber.domain.Function;
import com.saber.domain.PageBean;

import java.util.List;

public interface FunctionService {
    /**
     * 查询所有权限，返回json数据
     * @return
     */
    List<Function> findAll();
    /**
     * 添加权限
     * @param t
     */
    void save(Function t);
    /**
     * 权限分页查询
     * @param pageBean
     */
    void pageQuery(PageBean pageBean);

    /**
     * 根据当前登录用户人查询对应的菜单数据 ，返回json
     */
    List<Function> findMenu();
}
