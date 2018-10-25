package com.saber.service.Impl;

import com.saber.dao.RoleDao;
import com.saber.domain.Function;
import com.saber.domain.PageBean;
import com.saber.domain.Role;
import com.saber.service.RoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public void save(Role t, String functionid) {
        roleDao.save(t);
        if(StringUtils.isNotBlank(functionid)){
            String[] split =functionid.split(",");
            for (String id:split){
                //手动构造一个权限对象，致使id 对象为托管
                Function function =new Function(id);
                //角色关联权限
                t.getFunctions().add(function);
            }
        }
    }

    @Override
    public void pageQuery(PageBean pageBean) {
        roleDao.pageQuery(pageBean);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }
}
