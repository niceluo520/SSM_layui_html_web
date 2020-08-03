package com.cbox.base.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbox.base.common.dao.BaseCRUDMapper;

@Service
public class BaseComService {

    @Autowired
    private BaseCRUDMapper baseCRUDMapper;

    /**
     * 保存
     */
    public int save(String rec_id, String tableName, Map<String, Object> params) {
        Map<String, Object> tableMap = new HashMap<String, Object>();
        tableMap.put("tableName", tableName);
        tableMap.put("params", params);
        if (StringUtils.isNotBlank(rec_id)) {
            tableMap.put("rec_id", rec_id);
            return baseCRUDMapper.update(tableMap);
        }
        return baseCRUDMapper.save(tableMap);
    }

    /**
     * 更新
     */
    public int update(Map<String, Object> conditions, String tableName, Map<String, Object> params) {
        Map<String, Object> tableMap = new HashMap<String, Object>();
        tableMap.put("tableName", tableName);
        tableMap.put("params", params);
        tableMap.put("conditions", conditions);
        return baseCRUDMapper.updateConditions(tableMap);
    }

    /**
     * 删除
     * 
     * @param userName
     */
    public int delete(String tableName, String userName, Map<String, Object> params) {
        Map<String, Object> tableMap = new HashMap<String, Object>();
        tableMap.put("tableName", tableName);
        tableMap.put("params", params);
        tableMap.put("rec_updateperson", userName);
        return baseCRUDMapper.delete(tableMap);
    }

    /**
     * 物理删除
     * 
     * @param userName
     */
    public int delete(String tableName, Map<String, Object> params) {
        Map<String, Object> tableMap = new HashMap<String, Object>();
        tableMap.put("tableName", tableName);
        tableMap.put("params", params);
        return baseCRUDMapper.deleteEmpty(tableMap);
    }

    /**
     * 查询列表
     */
    public List<Map<String, Object>> query(String tableName, Map<String, Object> params) {
        String orders = "";
        if (null != params.get("orders") && StringUtils.isNotBlank(params.get("orders").toString())) {
            orders = params.get("orders").toString();
            params.remove("orders");
        }
        Map<String, Object> tableMap = new HashMap<String, Object>();
        tableMap.put("tableName", tableName);
        tableMap.put("params", params);
        tableMap.put("orders", orders);
        return baseCRUDMapper.query(tableMap);
    }

    /**
     * 模糊查询列表
     */
    public List<Map<String, Object>> queryLike(String tableName, Map<String, Object> params) {
        Map<String, Object> tableMap = new HashMap<String, Object>();
        tableMap.put("tableName", tableName);
        tableMap.put("params", params);
        return baseCRUDMapper.queryLike(tableMap);
    }

    /**
     * 查询详情
     */
    public Map<String, Object> queryOne(String tableName, Map<String, Object> params) {
        Map<String, Object> tableMap = new HashMap<String, Object>();
        tableMap.put("tableName", tableName);
        tableMap.put("params", params);
        return baseCRUDMapper.queryOne(tableMap);
    }

    /**
     * 查询总记录数
     */
    public int count(String tableName, Map<String, Object> params) {
        Map<String, Object> tableMap = new HashMap<String, Object>();
        tableMap.put("tableName", tableName);
        tableMap.put("params", params);
        return baseCRUDMapper.count(tableMap);
    }

}
