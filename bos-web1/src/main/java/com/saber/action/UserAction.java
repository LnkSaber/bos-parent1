package com.saber.action;

import com.saber.domain.User;
import com.saber.service.UserService;
import com.saber.utils.BOSUtils;
import com.saber.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User>{
    //属性驱动，接收页面输入的验证码
    private String checkcode;

    @Autowired
    private UserService userService;
    User user1=new User();
    /**
     * 用户登录
     */
    public String login(){

        //从session中获取生成的验证码
        String code= (String) ServletActionContext.getRequest().getSession().getAttribute("key");

        //校验验证码是否输入正确
        if (StringUtils.isNoneBlank(checkcode)&&checkcode.equals(code)){
            //验证码输入正确
            //使用shiro框架提供的方式进行认证
            //获得当前登录用户对象
            Subject subject = SecurityUtils.getSubject();

            AuthenticationToken token= new UsernamePasswordToken(t.getUsername(), MD5Utils.md5(t.getPassword()));

            try {
                subject.login(token);//用户名密码令牌
                 User user = (User) subject.getPrincipal();
                //登录成功，将user对象放入session，跳转到首页
                ServletActionContext.getRequest().getSession().setAttribute("loginUser",user);
                return "home";
            } catch (AuthenticationException e) {
                e.printStackTrace();
                return LOGIN;
            }


            }
        else {
            //输入的验证码错误，设置提示信息，跳转到登录页面
            this.addActionError("输入的验证码有误");
            return LOGIN;
        }


    }

    public String login_before(){

        //从session中获取生成的验证码
        String code= (String) ServletActionContext.getRequest().getSession().getAttribute("key");

        //校验验证码是否输入正确
        if (StringUtils.isNoneBlank(checkcode)&&checkcode.equals(code)){
            //验证码输入正确
            User user=userService.login(t);
            if (user!=null){
                //登录成功，将user对象放入session，跳转到首页
                ServletActionContext.getRequest().getSession().setAttribute("loginUser",user);
                return "home";
            }

            else {
                //登录失败，设置提示信息，跳转到登录首页
                this.addActionError("用户名或密码有误");
                return LOGIN;
            }
        }
        else {
            //输入的验证码错误，设置提示信息，跳转到登录页面
            this.addActionError("输入的验证码有误");
            return LOGIN;
        }


    }

    /**
     * 用户注销
     */
    public String logout(){
        System.out.println("UserActin logout");
        ServletActionContext.getRequest().getSession().invalidate();
        return LOGIN;
    }

    /**
     * 修改密码
     * @throws IOException
     */
    public String editPassword() throws IOException {
        //获取当前登录用户
        User user= BOSUtils.getLoginUser();
        System.out.println(user);
        String temp ="1";
        System.out.println(t);
        try {
            userService.editPassword(user.getId(),t.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            temp="0";
        }
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        ServletActionContext.getResponse().getWriter().print(temp);
        return NONE;
    }


    private String [] roleIds;

    public String[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }

    /**
     * 添加用户
     */
    public String add(){
        System.out.println(roleIds);
        userService.save(t,roleIds);
        return "list";
    }

    /**
     * 用户数据分页查询
     * @return
     */
    public String pageQuery(){
        userService.pageQuery(pageBean);
        this.javaJson(pageBean,new String[]{"noticebills","roles"});
        return  NONE;
    }



    public String getCheckcode() {
        return checkcode;
    }
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
}
