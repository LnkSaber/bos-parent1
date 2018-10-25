package com.saber.service.Impl;

import com.saber.dao.DecidedzoneDao;
import com.saber.dao.SubareaDao;
import com.saber.domain.Decidedzone;
import com.saber.domain.PageBean;
import com.saber.domain.Subarea;
import com.saber.service.DecidedzoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.event.PaintEvent;

@Transactional
@Service
public class DecidedzoneServiceImpl implements DecidedzoneService {

    @Autowired
    private DecidedzoneDao decidedzoneDao;

    @Autowired
    private SubareaDao subareaDao;
    /**
     * 添加定区，同时关联分区
     */
    @Override
    public void save(Decidedzone t, String[] subareaid) {
        decidedzoneDao.save(t);

        for (String id: subareaid) {
            Subarea subarea =subareaDao.getById(id); //持久化对象
            //			model.getSubareas().add(subarea); 一个（定区），已经放弃维护外键权利，只能在多放负责维护
            subarea.setDecidedzone(t);

        }
    }

    @Override
    public void pageQuery(PageBean pageBean) {
        decidedzoneDao.pageQuery(pageBean);
    }
}
