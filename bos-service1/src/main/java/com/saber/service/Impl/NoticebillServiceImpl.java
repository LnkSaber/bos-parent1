package com.saber.service.Impl;

import com.saber.dao.DecidedzoneDao;
import com.saber.dao.NoticebillDao;
import com.saber.dao.WorkbillDao;
import com.saber.domain.*;
import com.saber.service.NoticebillService;
import com.saber.service.impl.CustomerService;
import com.saber.utils.BOSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@Transactional
public class NoticebillServiceImpl  implements NoticebillService {

    //注入定区
    @Autowired
    private DecidedzoneDao decidedzoneDao;

    //业务通知单
    @Autowired
    private NoticebillDao noticebillDao;

    //工单
    @Autowired
    private WorkbillDao workbillDao;

    //Crm服务，proxy代理对象
    @Autowired
    private CustomerService proxy;



    //保存一个业务通知单，并尝试自动分单
    @Override
    public void save(Noticebill t) {

        User user = BOSUtils.getLoginUser();

        t.setUser(user);

        noticebillDao.save(t);

        //获取客户的取件地址
        String pickaddress = t.getPickaddress();

        //远程调用crm服务，根据取件地址查询定区id
        String decidedzoneIdByAddress = proxy.findDecidedzoneIdByAddress(pickaddress);

        if(decidedzoneIdByAddress!=null){
            //查询到定区id，可以完成自动分单

            //根据定区id查询定区信息
            Decidedzone decidedzone = decidedzoneDao.getById(decidedzoneIdByAddress);

            //获取取派员信息
            Staff staff = decidedzone.getStaff();

            //业务通知单关联取派员对象
            t.setStaff(staff);

            //设置分单类型为：自动分单
            t.setOrdertype(Noticebill.ORDERTYPE_AUTO);

            //为取派员产生一个工单
            Workbill workbill = new Workbill();

            workbill.setAttachbilltimes(0);//追加单次数
            workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//创建时间，当前系统时间
            workbill.setNoticebill(t);//工单关联页面通知单
            workbill.setPickstate(Workbill.PICKSTATE_NO);//取件状态
            workbill.setRemark(t.getRemark());//备注信息
            workbill.setStaff(staff);//工单关联取派员
            workbill.setType(Workbill.TYPE_1);//工单类型
            //调用dao层保存工单
            workbillDao.save(workbill);
        }
        else {
            //没有查询到定区id，不能完成自动分单
            t.setOrdertype(Noticebill.ORDERTYPE_MAN);
        }

    }
}
