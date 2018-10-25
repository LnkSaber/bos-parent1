package com.saber.service.Impl;

import com.saber.dao.FunctionDao;
import com.saber.domain.Function;
import com.saber.domain.PageBean;
import com.saber.domain.User;
import com.saber.service.FunctionService;
import com.saber.utils.BOSUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class FunctionServiceImpl implements FunctionService {

    @Autowired
    private FunctionDao functionDao;

    @Override
    //查询所有权限，返回json数据
    public List<Function> findAll() {
        return functionDao.findAll();
    }

    //添加权限
    @Override
    public void save(Function t) {
        Function function=t.getParentFunction();
        if(function!=null&&function.getId().equals("")) {
            t.setParentFunction(null);
        }
        functionDao.save(t);
    }

    //权限分页查询
    @Override
    public void pageQuery(PageBean pageBean) {
        functionDao.pageQuery(pageBean);
    }

    //根据当前登录用户人查询对应的菜单数据 ，返回json
    @Override
    public List<Function> findMenu() {
        User user = BOSUtils.getLoginUser();
        List <Function> list=null;
        if(user.getUsername().equals("saber")){
            //如果是超级管理员内置用户，查询所有菜单
            list =functionDao.findAllMenu();
        }
        else {
            list=functionDao.findAllMenuById(user.getId());
        }
        return list;
    }
}
