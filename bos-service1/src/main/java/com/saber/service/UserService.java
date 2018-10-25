package com.saber.service;

import com.saber.domain.PageBean;
import com.saber.domain.User;

public interface UserService {
    /**
     * 用户保存
     * @param  t
     * @return
     */
   User login(User t);

    /**
     * 修改密码
     * @param id
     * @param password
     */
    void editPassword(String id, String password);

    /**
     * 添加用户
     * @param t
     * @param roleIds
     */
    void save(User t, String[] roleIds);

    /**
     * 用户数据分页查询
     * @param pageBean
     */
    void pageQuery(PageBean pageBean);
}
