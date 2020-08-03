package com.cbox.business.system.user.userform.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbox.base.common.service.BaseCRUDService;
import com.cbox.base.protocol.ResponseBodyVO;
import com.cbox.base.protocol.ServerRspUtil;
import com.cbox.base.util.GlobalRecIdUtil;
import com.cbox.base.util.StrUtil;
import com.cbox.business.system.user.userform.dao.UserformMapper;
import com.cbox.business.system.user.userlogin.service.PasswordService;
import com.cbox.constant.SysConstant;

/**
 * @ClassName: UserformService
 * @Function: 用户添加表单
 * 
 * @author cbox
 * @version 1.0
 */
@Service
@Transactional
public class UserformService extends BaseCRUDService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserformMapper userformMapper;

    @Autowired
    private PasswordService passwordService;

    /************************* 表单[新增用户表单oform]相关方法 Begin ************************/

    /**
     * doLoadFormOform:加载表单数据-新增用户表单
     *
     * @param param
     * @return
     */
    public ResponseBodyVO<Object> doLoadFormOform(Map<String, Object> param) {
        Map<String, Object> mapResult = new HashMap<String, Object>();

        // 必传参数校验
        if (StrUtil.isNull(param.get("rec_id"))) {
            return ServerRspUtil.error("提交参数缺失，查询失败");
        }

        // 获取数据 Table:s_sysuser
        List<String> listColumn0 = new ArrayList<String>();
        listColumn0.add("user_id");
        listColumn0.add("user_name");
        listColumn0.add("sex");
        listColumn0.add("mobile_phone");
        listColumn0.add("email");
        listColumn0.add("area_code");
        listColumn0.add("is_admin");
        listColumn0.add("locked");
        listColumn0.add("h_img");
        listColumn0.add("person_sign");
        listColumn0.add("education");
        listColumn0.add("profession");
        listColumn0.add("rec_id");
        Map<String, Object> mapQueryParam0 = new HashMap<String, Object>();
        mapQueryParam0.put("rec_id", param.get("rec_id"));
        Map<String, Object> mapQueryResult0 = this.queryOne("s_sysuser", mapQueryParam0, listColumn0);
        if (null != mapQueryResult0 && !mapQueryResult0.isEmpty()) {
            mapResult.putAll(mapQueryResult0);
        }

        // 获取数据 Table:s_sysuser_auth
        List<String> listColumn1 = new ArrayList<String>();
        listColumn1.add("role_id");
        Map<String, Object> mapQueryParam1 = new HashMap<String, Object>();
        mapQueryParam1.put("user_id", mapQueryResult0.get("rec_id"));
        mapQueryParam1.put("auth_type", "role");
        Map<String, Object> mapQueryResult1 = this.queryOne("s_sysuser_auth", mapQueryParam1, listColumn1);
        if (null != mapQueryResult1 && !mapQueryResult1.isEmpty()) {
            mapResult.putAll(mapQueryResult1);
        }

        return ServerRspUtil.success("查询成功", mapResult);
    }

    /**
     * doAddOform:添加-新增用户表单
     *
     * @param param
     * @return
     */
    public ResponseBodyVO<Object> doAddOform(Map<String, Object> param) {

        int count = 0;

        // Table:s_sysuser
        String addRecId = GlobalRecIdUtil.nextRecId();
        param.put("rec_id", addRecId);
        Map<String, Object> mapParam0 = new HashMap<String, Object>();
        mapParam0.put("user_id", param.get("user_id"));
        mapParam0.put("user_name", param.get("user_name"));
        mapParam0.put("sex", param.get("sex"));
        mapParam0.put("mobile_phone", param.get("mobile_phone"));
        mapParam0.put("email", param.get("email"));
        mapParam0.put("area_code", param.get("area_code"));
        mapParam0.put("is_admin", param.get("is_admin"));
        mapParam0.put("locked", param.get("locked"));
        mapParam0.put("h_img", param.get("h_img"));
        mapParam0.put("person_sign", param.get("person_sign"));
        mapParam0.put("education", param.get("education"));
        mapParam0.put("profession", param.get("profession"));
        mapParam0.put("rec_id", addRecId);

        // 设置密码信息
        String salt = RandomStringUtils.randomAlphanumeric(10);
        mapParam0.put("salt", salt);
        String passwd = passwordService.encryptPassword(param.get("user_id").toString(), SysConstant.DEFAULT_PASSSWD, salt);
        mapParam0.put("password", passwd);

        count = this.save(null, "s_sysuser", mapParam0);

        // Table:s_sysuser_auth
        Map<String, Object> mapParam1 = new HashMap<String, Object>();
        mapParam1.put("role_id", param.get("role_id"));
        mapParam1.put("user_id", param.get("rec_id"));
        mapParam1.put("auth_type", "role");
        mapParam1.put("rec_id", GlobalRecIdUtil.nextRecId());
        count = this.save(null, "s_sysuser_auth", mapParam1);

        return ServerRspUtil.formRspBodyVO(count, "操作成功");
    }

    /**
     * doEditOform:修改-新增用户表单
     *
     * @param param
     * @return
     */
    public ResponseBodyVO<Object> doEditOform(Map<String, Object> param) {

        int count = 0;

        // 必传参数校验
        if (StrUtil.isNull(param.get("rec_id"))) {
            return ServerRspUtil.formRspBodyVO(count, "提交参数缺失，操作失败");
        }

        // Table:s_sysuser
        Map<String, Object> mapParam0 = new HashMap<String, Object>();
        mapParam0.put("user_id", param.get("user_id"));
        mapParam0.put("user_name", param.get("user_name"));
        mapParam0.put("sex", param.get("sex"));
        mapParam0.put("mobile_phone", param.get("mobile_phone"));
        mapParam0.put("email", param.get("email"));
        mapParam0.put("area_code", param.get("area_code"));
        mapParam0.put("is_admin", param.get("is_admin"));
        mapParam0.put("locked", param.get("locked"));
        mapParam0.put("h_img", param.get("h_img"));
        mapParam0.put("person_sign", param.get("person_sign"));
        mapParam0.put("education", param.get("education"));
        mapParam0.put("profession", param.get("profession"));
        Map<String, Object> mapCondition0 = new HashMap<String, Object>();
        mapCondition0.put("rec_id", param.get("rec_id"));
        count = this.update(mapCondition0, "s_sysuser", mapParam0);

        // Table:s_sysuser_auth
        Map<String, Object> mapParam1 = new HashMap<String, Object>();
        mapParam1.put("role_id", param.get("role_id"));
        Map<String, Object> mapCondition1 = new HashMap<String, Object>();
        mapCondition1.put("user_id", param.get("rec_id"));
        mapCondition1.put("auth_type", "role");
        count = this.update(mapCondition1, "s_sysuser_auth", mapParam1);

        return ServerRspUtil.formRspBodyVO(count, "操作成功");
    }

    /**
     * doDeleteOform:删除表单数据数据-新增用户表单
     *
     * @param param
     * @return
     */
    public ResponseBodyVO<Object> doDeleteOform(Map<String, Object> param) {

        // 必传参数校验
        if (StrUtil.isNull(param.get("rec_id"))) {
            return ServerRspUtil.error("提交参数缺失，操作失败");
        }

        // Table:s_sysuser
        Map<String, Object> mapDelete0 = new HashMap<String, Object>();
        mapDelete0.put("rec_id", param.get("rec_id"));
        this.delete("s_sysuser", param.get("rec_updateperson").toString(), mapDelete0);

        // Table:s_sysuser_auth
        Map<String, Object> mapDelete1 = new HashMap<String, Object>();
        mapDelete1.put("user_id", param.get("rec_id"));
        mapDelete1.put("auth_type", "role");
        this.delete("s_sysuser_auth", param.get("rec_updateperson").toString(), mapDelete1);

        return ServerRspUtil.success("操作成功");
    }

    /**
     * checkExistOform:唯一校验-新增用户表单
     *
     * @param param
     * @return
     */
    public boolean checkExistOform(String tableName, Map<String, Object> param) {

        return this.isExist(tableName, param);
    }

    /************************* End 表单[新增用户表单oform] ******************************/

}
