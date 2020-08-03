package com.cbox.business.common.dictionary.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cbox.business.common.page.bean.BaseTreeVO;

@Mapper
public interface TreeDictMapper {

    /** 籍贯树areaTree **/
    public List<BaseTreeVO> getTreeNodeAreaTree(Map<String, Object> param);

    /** 菜单资源树 **/
    public List<BaseTreeVO> getTreeForResource(Map<String, Object> param);

}
