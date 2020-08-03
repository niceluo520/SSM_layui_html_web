package com.cbox.business.common.dictionary.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbox.base.common.service.BaseCRUDService;
import com.cbox.business.common.dictionary.dao.TreeDictMapper;
import com.cbox.business.common.page.bean.BaseTreeVO;
import com.cbox.business.common.page.util.BaseTreeUtil;

/**
 * @ClassName: TreeDictService
 * @Function: 获取数据字典数据-树类型数据
 * 
 * @author cbox
 * @version 1.0
 */
@Service
@Transactional
public class TreeDictService extends BaseCRUDService {

    @Autowired
    private TreeDictMapper treeDictMapper;

    /** 籍贯树areaTree **/
    public String getTreeNodeAreaTree(Map<String, Object> param) {
        List<BaseTreeVO> list = treeDictMapper.getTreeNodeAreaTree(param);
        return BaseTreeUtil.toNodesForDTree(list);
    }

    /** 菜单树 **/
    public String getTreeForResource(Map<String, Object> param) {
        List<BaseTreeVO> list = treeDictMapper.getTreeForResource(param);
        return BaseTreeUtil.toNodesForDTree(list);
    }

}
