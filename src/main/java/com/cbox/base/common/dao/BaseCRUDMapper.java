package com.cbox.base.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BaseCRUDMapper {

    /**
     * 保存
     */
    int save(Map<String, Object> tableMap);

    /**
     * 更新（根据rec_id）
     */
    int update(Map<String, Object> tableMap);
    
    /**
     * 更新（根据传入Map条件）
     */
    int updateConditions(Map<String, Object> tableMap);

    /**
     * 逻辑删除
     */
    int delete(Map<String, Object> tableMap);

    /**
     * 物理删除
     */
    int deleteEmpty(Map<String, Object> tableMap);

    /**
     * 查询列表
     */
    List<Map<String, Object>> query(Map<String, Object> tableMap);
    
    /**
     * 模糊查询
     */
    List<Map<String, Object>> queryLike(Map<String, Object> tableMap);
    
    /**
     * 查询详情
     */
    Map<String, Object> queryOne(Map<String, Object> tableMap);

    /**
     * 查询总记录数
     */
    int count(Map<String, Object> tableMap);
    
    int countLike(Map<String, Object> tableMap);
    
    /**
     * 是否存在
     * @param tableMap
     * @return
     */
    int isExist(Map<String, Object> tableMap);

}
