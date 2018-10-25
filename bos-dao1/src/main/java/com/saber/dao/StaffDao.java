package com.saber.dao;

import com.saber.domain.PageBean;
import com.saber.domain.Staff;

public interface StaffDao extends BaseDao<Staff> {

    void executeUpdate(String id);
}
