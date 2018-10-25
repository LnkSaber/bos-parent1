package com.saber.service;

import com.saber.domain.PageBean;
import com.saber.domain.Subarea;

import java.util.List;

public interface SubareaService {
    /**
     * 分区添加保存
     * @param t
     */
    void add(Subarea t);

    /**
     * 分区分页查询
     * @param pagaBean
     */
    void pageQuery(PageBean pageBean);

    /**
     * 查询所有分区数据
     * @return
     */
    List<Subarea> finaAll();

    /**
     * 查询所有未关联到定区的分区
     * @return
     */
    List<Subarea> findListNotAssociation();

    /**
     * 根据定区id查询关联的分区
     * @param decidedzoneId
     * @return
     */
    List<Subarea> findListByDecidedzoneId(String decidedzoneId);

    /**
     * 图形统计
     * @return
     */
    List<Object> findSubareasGroupByProvince();
}
