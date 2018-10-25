package com.saber.utils;

import com.saber.domain.User;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpSession;

/**
 * BOS项目的工具类
 *
 */
public class BOSUtils {
	
	//获取session对象
	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}
	
	//获取登录用户对象
	public static User getLoginUser() {
		return (User) getSession().getAttribute("loginUser");
	}

}
