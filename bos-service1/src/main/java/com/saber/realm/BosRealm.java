package com.saber.realm;

import com.saber.dao.FunctionDao;
import com.saber.dao.UserDao;
import com.saber.domain.Function;
import com.saber.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

public class BosRealm extends AuthorizingRealm {
    @Autowired
    private UserDao userDao;
    @Autowired
    private FunctionDao functionDao;

    //认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("realm中的认证方法执行了");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username=token.getUsername();
        System.out.println(username);

        //根据用户名查询数据库中的密码
        User user =userDao.finUserByUsername(username);
        if (user==null){
            //用户名不存在
            return null;
        }
        //如果能查询到，再由框架比对数据库中查询到的密码和页面提交的密码是否一致
        AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
        return info;
    }
    //授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取当前登录用户查询数据库，获取实际对应的权限
        User user= (User) SecurityUtils.getSubject().getPrincipal();

        List<Function> list =null;
        //根据当前登录用户查询数据库，获取实际对应的权限
        if(user.getUsername().equals("saber")){
            DetachedCriteria detachedCriteria =DetachedCriteria.forClass(Function.class);
            //超级管理员内置用户，查询所有权限数据
            list =functionDao.findByCriteria(detachedCriteria);
        }
        else {
            //其他用户登录
            list =functionDao.findFunctionByUserId(user.getId());
        }
        // 后期需要修改为根据当前登录用户查询数据库 ，获取实际对应的权限
       for(Function function :list){
           info.addStringPermissions(Collections.singleton(function.getCode()));
       }

        return info;
    }
}
