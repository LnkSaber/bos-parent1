package com.saber.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.saber.domain.User;
import com.saber.utils.BOSUtils;
import org.aopalliance.intercept.Invocation;

/**
 * 自定义拦截器，实现用户未登录自动跳转到登录页面
 *
 */
public class BosLoginInterceptor extends MethodFilterInterceptor {
    @Override
    //拦截器方法
    protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
        ActionProxy proxy = actionInvocation.getProxy();
        String actionName = proxy.getActionName();
        String namespace = proxy.getNamespace();

        String url = actionName +  namespace;
        System.out.println(url);

        //从Session域中获取用户对象
        User user= BOSUtils.getLoginUser();
        if (user==null){
            //没有登录，跳转到登录页面
            return "login";
        }
        return actionInvocation.invoke();
    }
}
