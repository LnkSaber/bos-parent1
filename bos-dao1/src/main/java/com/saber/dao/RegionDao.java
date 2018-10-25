package com.saber.dao;

import com.saber.domain.Region;

import java.util.List;

public interface RegionDao extends BaseDao<Region> {
    /**
     * 文件上传，解析Excel文件，并保存
     * @param regionList
     */
    void save(List<Region> regionList);

    List<Region> findListByQ(String q);
}
