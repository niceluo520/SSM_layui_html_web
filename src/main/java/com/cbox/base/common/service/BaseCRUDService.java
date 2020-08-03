package com.cbox.base.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbox.base.common.dao.BaseCRUDMapper;

@Service
@Transactional
public class BaseCRUDService {

    @Autowired
    private BaseCRUDMapper baseCRUDMapper;

    /**
     * rec_id 为空时保存 rec_id 不为空时更新
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
     * 更新带条件
     */
    public int update(Map<String, Object> conditions, String tableName, Map<String, Object> params) {
        Map<String, Object> tableMap = new HashMap<String, Object>();
        tableMap.put("tableName", tableName);
        tableMap.put("params", params);
        tableMap.put("conditions", conditions);
        return baseCRUDMapper.updateConditions(tableMap);
    }

    /**
     * 逻辑伪删除
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
     * 查询列表精准
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
     * 查询列表精准
     * 
     * @param tableName 库表名
     * @param params 等于参数
     * @param noEqualParams 不等于参数
     * @return
     */
    public List<Map<String, Object>> query(String tableName, Map<String, Object> params, Map<String, Object> noEqualParams) {
        String orders = "";
        if (null != params.get("orders") && StringUtils.isNotBlank(params.get("orders").toString())) {
            orders = params.get("orders").toString();
            params.remove("orders");
        }
        Map<String, Object> tableMap = new HashMap<String, Object>();
        tableMap.put("tableName", tableName);
        tableMap.put("params", params);
        tableMap.put("noEqualParams", noEqualParams);
        tableMap.put("orders", orders);
        return baseCRUDMapper.query(tableMap);
    }

    /**
     * 查询列表模糊
     */
    public List<Map<String, Object>> queryLike(String tableName, Map<String, Object> params) {
        Map<String, Object> tableMap = new HashMap<String, Object>();
        tableMap.put("tableName", tableName);
        tableMap.put("params", params);
        return baseCRUDMapper.queryLike(tableMap);
    }

    /**
     * 查询单条数据 精准
     */
    public Map<String, Object> queryOne(String tableName, Map<String, Object> params) {
        Map<String, Object> tableMap = new HashMap<String, Object>();
        tableMap.put("tableName", tableName);
        tableMap.put("params", params);
        return baseCRUDMapper.queryOne(tableMap);
    }

    /**
     * 查询指定字段列的单条数据 精准
     * 
     * @param tableName 表名
     * @param params
     * @param columns 查询字段列集合
     * @return
     */
    public Map<String, Object> queryOne(String tableName, Map<String, Object> params, List<String> columns) {
        Map<String, Object> tableMap = new HashMap<String, Object>();
        tableMap.put("tableName", tableName);
        tableMap.put("params", params);
        tableMap.put("columns", columns);
        return baseCRUDMapper.queryOne(tableMap);
    }

    /**
     * 查询总记录数 精准
     */
    public int count(String tableName, Map<String, Object> params) {
        Map<String, Object> tableMap = new HashMap<String, Object>();
        tableMap.put("tableName", tableName);
        tableMap.put("params", params);
        return baseCRUDMapper.count(tableMap);
    }

    /**
     * 查询总记录数 模糊
     */
    public int countLike(String tableName, Map<String, Object> params) {
        Map<String, Object> tableMap = new HashMap<String, Object>();
        tableMap.put("tableName", tableName);
        tableMap.put("params", params);
        return baseCRUDMapper.countLike(tableMap);
    }

    /**
     * 查询记录是否存在
     */
    public boolean isExist(String tableName, Map<String, Object> params) {
        Map<String, Object> tableMap = new HashMap<String, Object>();
        tableMap.put("tableName", tableName);
        tableMap.put("params", params);
        if (baseCRUDMapper.isExist(tableMap) > 0)
            return true;
        else
            return false;
    }

    /**
     * 
     * getDist:根据字典类型获取数据字典
     *
     * @date: 2017年10月13日 下午3:17:40
     * @author cbox
     * @param type
     * @return
     */
    public List<Map<String, Object>> getDist(String type) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dict_type", type);
        Map<String, Object> tableMap = new HashMap<String, Object>();
        tableMap.put("tableName", "isc_dictionary");
        tableMap.put("params", params);
        tableMap.put("orders", "sort asc");
        List<Map<String, Object>> res = baseCRUDMapper.query(tableMap);
        for (Map<String, Object> map : res) {
            map.remove("rec_id");
            map.remove("dict_type");
            map.remove("rec_status");
            map.remove("rec_person");
            map.remove("rec_time");
            map.remove("rec_updateperson");
            map.remove("rec_updatetime");
        }
        return res;
    }

}
