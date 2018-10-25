package com.saber.dao.Impl;

import com.saber.dao.RegionDao;
import com.saber.domain.Region;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements RegionDao {

    @Override
    public void save(List<Region> regionList) {
        //文件上传，解析Excel文件，并保存
            for (Region region: regionList) {
            getHibernateTemplate().saveOrUpdate(region);
        }
    }


    /**
     * 根据q参数进行模糊查询
     */
    @Override
    public List<Region> findListByQ(String q) {
        String  hql = "from Region r where r.shortcode like ? or r.citycode like ? "
                + "or r.province like ? or r.city like ? or r.district like ?";

        List<Region> list = (List<Region>) this.getHibernateTemplate().find(hql, "%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%");

        return list;
    }
}
