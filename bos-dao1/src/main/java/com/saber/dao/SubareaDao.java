package com.saber.dao;

import com.saber.domain.Subarea;

import java.util.List;

public interface SubareaDao extends BaseDao<Subarea> {

    List<Object> findSubareasGroupByProvince();
}
