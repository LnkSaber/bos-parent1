package com.saber.service;

import com.saber.domain.PageBean;
import com.saber.domain.Region;

import java.util.List;

public interface RegionService {
    void save(List<Region> regionList);

    void pageQuery(PageBean pageBean);

    List<Region> findAll();

    List<Region> findListByQ(String q);
}
