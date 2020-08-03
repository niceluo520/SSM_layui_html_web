package com.cbox.business.common.dictionary.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbox.base.common.service.BaseCRUDService;
import com.cbox.business.common.dictionary.dao.SelectDictMapper;

/**
 * @ClassName: SelectDictService
 * @Function: 获取数据字典数据-选项类型数据
 * 
 * @author cbox
 * @version 1.0
 */
@Service
public class SelectDictService extends BaseCRUDService {

    @Autowired
    private SelectDictMapper selectDictMapper;

    /** 用户角色选择userRole **/
    public List<Map<String, Object>> getOptionUserRole(Map<String, Object> param) {
        return selectDictMapper.getOptionUserRole(param);
    }

    /** 用户学历userEdu **/
    public List<Map<String, Object>> getOptionUserEdu(Map<String, Object> param) {
        return selectDictMapper.getOptionUserEdu(param);
    }

}
