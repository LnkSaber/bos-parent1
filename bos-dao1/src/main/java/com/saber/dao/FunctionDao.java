package com.saber.dao;

import com.saber.domain.Function;

import java.util.List;

public interface FunctionDao  extends BaseDao<Function>{
    /**
     * 根据用户id查询对应的权限
     * @param id
     * @return
     */
    List<Function> findFunctionByUserId(String id);

    /**
     * 如果是超级管理员内置用户，查询所有菜单
     * @return
     */
    List<Function> findAllMenu();

    /**
     * 其他用户。根据用户id查询菜单
     * @param id
     * @return
     */
    List<Function> findAllMenuById(String id);
}
