package com.saber.service.Impl;

import com.saber.dao.RegionDao;
import com.saber.domain.PageBean;
import com.saber.domain.Region;
import com.saber.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class RegionServiceImpl implements RegionService {
    @Autowired
    private RegionDao regionDao;
    @Override
    public void save(List<Region> regionList) {
        regionDao.save(regionList);
    }

    @Override
    public void pageQuery(PageBean pageBean) {
        regionDao.pageQuery(pageBean);
    }

    @Override
    public List<Region> findAll() {
        return regionDao.findAll();
    }

    @Override
    public List<Region> findListByQ(String q) {
        return regionDao.findListByQ(q);
    }
}
