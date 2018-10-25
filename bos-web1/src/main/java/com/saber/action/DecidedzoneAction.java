package com.saber.action;

import com.saber.domain.Decidedzone;
import com.saber.service.DecidedzoneService;
import com.saber.service.impl.Customer;
import com.saber.service.impl.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone>{
    @Autowired
    private DecidedzoneService decidedzoneService;

    //注入Crm代理对象，通过Webservice来获取的
    @Autowired
    private CustomerService proxy;

    //	接收参数，属性驱动。接收分区多个id
    private String[] subareaid;

    //属性驱动接收参数，接收页面提交多个客户ID
    private List<Integer> customerIds;

    /**
     * 远程调用crm服务，将客户关联到定区
     * @return
     */
    public String assigncustomerstodecidedzone(){
        proxy.assigncustomerstodecidedzone(t.getId(),customerIds);
        return "list";
    }

    /**
     * 远程调用crm服务，获取未关联到定区的客户
     */
    public String findListNotAssociation() {
        List<Customer> list=proxy.findListNotAssociation();
        this.javaJson(list,new String[]{});
        return NONE;
    }
    /**
     * 远程调用crm服务，获取已经关联到定区的客户
     *
     */
    public String findListHasAssociation(){
        String id =t.getId();
        List<Customer> list =proxy.findListHasAssociation(id);
        this.javaJson(list,new String[]{});
        return NONE;
    }
    /**
     * 添加定区
     */
    public String add(){
        decidedzoneService.save(t,subareaid);
        return "list";
    }
    /**
     * 分页查询
     */
    public String pageQuery(){
        decidedzoneService.pageQuery(pageBean);
        this.javaJson(pageBean,new String[]{"currentPage","pageSize","detachedCriteria","decidedzones","subareas"});
        return NONE;
    }

    public String[] getSubareaid() {
        return subareaid;
    }
    public void setSubareaid(String[] subareaid) {
        this.subareaid = subareaid;
    }
    public List<Integer> getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(List<Integer> customerIds) {
        this.customerIds = customerIds;
    }
}
