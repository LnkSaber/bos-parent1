package com.saber.service.Impl;

import com.saber.dao.SubareaDao;
import com.saber.domain.PageBean;
import com.saber.domain.Subarea;
import com.saber.service.SubareaService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class SubareaServiceImpl implements SubareaService {
    @Autowired
    private SubareaDao subareaDao;
    @Override
    public void add(Subarea t) {
        subareaDao.save(t);
    }

    @Override
    public void pageQuery(PageBean pageBean) {
        subareaDao.pageQuery(pageBean);
    }

    @Override
    public List<Subarea> finaAll() {
        return subareaDao.findAll();
    }

    @Override
    public List<Subarea> findListNotAssociation() {
        DetachedCriteria dc= DetachedCriteria.forClass(Subarea.class);
        //当Subarea里面的属性：decidedzone等于null     查询分区中未关联的定区
        dc.add(Restrictions.isNull("decidedzone"));

        return subareaDao.findByCriteria(dc);
    }

    //根据定区id查询关联的分区
    @Override
    public List<Subarea> findListByDecidedzoneId(String decidedzoneId) {

        DetachedCriteria detachedCriteria =DetachedCriteria.forClass(Subarea.class);

        //添加过滤条件
        detachedCriteria.add(Restrictions.eq("decidedzone.id",decidedzoneId));

        return subareaDao.findByCriteria(detachedCriteria);
    }

    @Override
    public List<Object> findSubareasGroupByProvince() {
        return subareaDao.findSubareasGroupByProvince();
    }
}
