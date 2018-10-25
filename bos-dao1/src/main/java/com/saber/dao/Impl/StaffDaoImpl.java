package com.saber.dao.Impl;

import com.saber.dao.StaffDao;
import com.saber.domain.Staff;
import org.springframework.stereotype.Repository;

@Repository
public class StaffDaoImpl extends BaseDaoImpl<Staff> implements StaffDao {

    //批量删除
    @Override
    public void executeUpdate(String id) {
        //update staff set deltag = "1" where id=?
        //先查询该对应的id对象
        Staff staff =getHibernateTemplate().get(Staff.class,id);
        staff.setDeltag("1");
    }
}
