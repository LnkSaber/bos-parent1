package com.saber.dao.Impl;


import com.saber.dao.BaseDao;
import com.saber.dao.SubareaDao;
import com.saber.domain.Subarea;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubareaDaoImpl extends BaseDaoImpl<Subarea> implements SubareaDao {
    @Override
    public List<Object> findSubareasGroupByProvince() {
        String hql = "select  r.province , count(*)  from Subarea s left outer join  s.region r group by r.province";
        List<Object> list = (List<Object>) this.getHibernateTemplate().find(hql);

        return list;
    }
}
