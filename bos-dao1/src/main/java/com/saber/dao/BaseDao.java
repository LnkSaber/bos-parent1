package com.saber.dao;

import com.saber.domain.PageBean;
import com.saber.domain.Staff;
import com.saber.domain.Subarea;
import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.util.List;

/**
 * Dao方法抽取
 *
 */
public interface BaseDao<T> {



    void saveOrUpdate(T t);
    /**
     * 增
     */
    void save(T t);
    /**
     * 删：通过id
     * 所有能够做为id的类型 都有一个共同的特点，都是Serializae的实现类
     * 八大基本数据类型包装类都实现了Serializable接口
     */
    void delete(Serializable id);
    /**
     * 删：对象
     */
    void delete(T t);
    /**
     * 改
     */
    void update(T t);
    /**
     * 查:通过id查
     */
    T getById(Serializable id);
    /**
     * 查询所有
     * @return
     */
    List<T> findAll();

    /**
     * 查:查询符合条件总记录数
     */
    Integer getTotalCount(DetachedCriteria dc);
    /**
     * 查：查询分页列表的数据
     */
    List<T> getList(DetachedCriteria dc, Integer start, Integer pageSize);
    /**
     * 通用分页查询方法
     */
    void pageQuery(PageBean pageBean);

    /**
     * 通过提交查询所有符合的数据
     * @param dc
     * @return
     */
    List<T> findByCriteria(DetachedCriteria dc);
}
