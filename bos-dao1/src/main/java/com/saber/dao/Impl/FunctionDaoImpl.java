package com.saber.dao.Impl;

import com.saber.dao.FunctionDao;
import com.saber.domain.Function;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements FunctionDao {
    @Override
    public List<Function> findFunctionByUserId(String id) {
        String hql ="SELECT distinct f FROM Function f LEFT OUTER JOIN f.roles r LEFT OUTER JOIN  r.users u WHERE u.id =?";
        return  (List<Function>) this.getHibernateTemplate().find(hql, id);
    }

    //admin用户
    @Override
    public List<Function> findAllMenu() {
        String hql = "FROM Function f WHERE f.generatemenu = '1' ORDER BY f.zindex DESC";

        List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql);
        return list;
    }

    //根据用户id查询对应的权限
    @Override
    public List<Function> findAllMenuById(String id) {
        String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles r LEFT OUTER JOIN r.users u WHERE u.id = ?";
        List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql, id);
        return list;
    }
}
