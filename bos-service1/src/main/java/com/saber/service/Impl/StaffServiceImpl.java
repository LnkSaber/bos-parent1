package com.saber.service.Impl;

import com.saber.dao.StaffDao;
import com.saber.domain.PageBean;
import com.saber.domain.Staff;
import com.saber.service.StaffService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffDao staffDao;
    @Override
    public void save(Staff t) {
        staffDao.save(t);
    }

    @Override
    public void pageQuery(PageBean pageBean) {
      staffDao.pageQuery(pageBean);
    }

    //取派员批量删除
    @Override
    public void deleteBach(String ids) {
        if(StringUtils.isNoneBlank(ids)){
            String [] staffids= ids.split(",");
            for (String id: staffids) {
                staffDao.executeUpdate(id);
            }
        }
    }

    @Override
    public Staff findById(String id) {
        return staffDao.getById(id);
    }

    @Override
    public void update(Staff staff) {
        staffDao.saveOrUpdate(staff);
    }

    @Override
    public List<Staff> findListNotDelete() {
        DetachedCriteria dc =DetachedCriteria.forClass(Staff.class);
        //添加过滤条件，当deltag等于0
        dc.add(Restrictions.eq("deltag","0"));
        //dc.add(Restrictions.ne("deltag", "1"));

        return staffDao.findByCriteria(dc);
    }
}
