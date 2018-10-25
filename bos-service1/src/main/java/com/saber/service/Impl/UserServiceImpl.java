package com.saber.service.Impl;

import com.saber.dao.UserDao;
import com.saber.domain.PageBean;
import com.saber.domain.Role;
import com.saber.domain.User;
import com.saber.service.UserService;
import com.saber.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User login(User model) {
        //使用md5加密，
        //添加加密的密码
        model.setPassword(MD5Utils.md5(model.getPassword()));
        return   userDao.finUserBy(model);

    }

    @Override
    public void editPassword(String id, String password) {
        //使用md5加密
        password =MD5Utils.md5(password);
        userDao.executeUpdate(id,password);
    }

    @Override
    public void save(User t, String[] roleIds) {
        String md5=MD5Utils.md5(t.getPassword());
        t.setPassword(md5);
        userDao.save(t);
        if (roleIds !=null&&roleIds.length>0){
            for(String id:roleIds){
                Role role =new Role(id);
                t.getRoles().add(role);
            }
        }
    }

    @Override
    public void pageQuery(PageBean pageBean) {
      userDao.pageQuery(pageBean);
    }
}
