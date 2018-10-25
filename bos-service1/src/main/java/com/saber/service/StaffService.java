package com.saber.service;

import com.saber.domain.PageBean;
import com.saber.domain.Staff;

import java.util.List;

public interface StaffService {
    /***
    *@author 吾王：林Saber
    *@date：2018/10/8  
    *@param：[t]
    *@method：save
    *@return：void
    *@purpose： 
    */
    void save(Staff t);

    /**
     * 分页查询
     */
    void pageQuery(PageBean pageBean);
    /**
     * 取派员批量删除
     * @param ids
     */
    void deleteBach(String ids);

    Staff findById(String id);

    void update(Staff staff);

    List<Staff> findListNotDelete();
}
