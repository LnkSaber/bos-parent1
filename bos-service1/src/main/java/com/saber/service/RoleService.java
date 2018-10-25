package com.saber.service;


import com.saber.domain.PageBean;
import com.saber.domain.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface RoleService {

    /**
     * 添加角色
     * @param t
     * @param functionid
     */
    void save(Role t, String functionid);
    /**
     * 分页查询
     * @param pageBean
     */
    void pageQuery(PageBean pageBean);

    /**
     * 查询所有角色数据
     * @return
     */
    List<Role> findAll();
}
