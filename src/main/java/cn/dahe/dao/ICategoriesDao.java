package cn.dahe.dao;

import cn.dahe.dto.Pager;
import cn.dahe.model.Categories;

import java.util.List;

/**
 * Created by fy on 2017/1/17.
 */
public interface ICategoriesDao extends IBaseDao<Categories>{
    /**
     * 根据父id获取子节点
     * @param pid
     * @param storeId
     * @return
     */
    List<Categories> findByPid(int pid, int storeId);

    /**
     * 查询所有分类
     * @param storeId
     * @return
     */
    List<Categories> findAll(int storeId);

    /**
     * 根据参数查询
     * @param start
     * @param pageSize
     * @param params
     * @return
     */
    Pager<Categories> findByParam(int start, int pageSize, Pager<Object> params);
}
