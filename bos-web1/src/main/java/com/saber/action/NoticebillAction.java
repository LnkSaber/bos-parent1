package com.saber.action;

import com.saber.domain.Noticebill;
import com.saber.service.NoticebillService;
import com.saber.service.impl.Customer;
import com.saber.service.impl.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill> {
	
	//注入crm客户端代理对象
	@Autowired
	private CustomerService proxy;
	
	/**
	 * 远程调用crm服务，根据手机号查询客户信息，
	 */
	public String findCustomerByTelephone() {
		
		String telephone = t.getTelephone();
		
		Customer customer = proxy.findCustomerByTelephone(telephone);
		
		this.javaJson(customer, new String[] {});
		
		return NONE;
	}
	
	@Autowired
	private NoticebillService noticebillService;
	
	/**
	 * 保存一个业务通知单，并尝试自动分单
	 */
	public String query() {
		
		noticebillService.save(t);
		
		return NONE;
	}
	

}
