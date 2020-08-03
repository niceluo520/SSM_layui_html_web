package com.cbox.business.system.user.userlist.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName: UserlistMapper
 * @Function: 用户管理-列表
 * 
 * @author cbox
 * @version 1.0
 */
@Mapper
public interface UserlistMapper {

    /** 加载列表数据-用户查询列表 **/
    public List<Map<String, Object>> doLoadListList0(Map<String, Object> param);

}
