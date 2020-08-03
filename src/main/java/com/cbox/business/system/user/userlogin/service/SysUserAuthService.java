package com.cbox.business.system.user.userlogin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbox.base.common.service.BaseCRUDService;
import com.cbox.base.util.ConvertUtil;
import com.cbox.base.util.StrUtil;
import com.cbox.business.system.user.userlogin.bean.SysUserVO;

@Service
@Transactional
public class SysUserAuthService extends BaseCRUDService {

    /** 得到用户所有的角色。支持直接给用户授予角色，以及通过分组、机构、职位来授予角色 */
    public Map<String, String> getUserRoles(SysUserVO userVO) {
        
        // 当前用户的所有角色
        Map<String, String> mapRoleIds = new HashMap<String, String>();// 用map的好处是能去重
        

        // 得到所有授权信息
        Map<String, Object> mapParam0 = new HashMap<String, Object>();
        mapParam0.put("user_id", userVO.getRec_id());
        List<Map<String, Object>> listUserAuth = this.query("s_sysuser_auth", mapParam0);

        mapParam0 = new HashMap<String, Object>();
        List<Map<String, Object>> listRoleRelation = this.query("s_sysrole_relation", mapParam0);

        Map<String, Object> mapOrg = this.getFilterMap(listRoleRelation, "type", "org", "organization_id");
        Map<String, Object> mapJob = this.getFilterMap(listRoleRelation, "type", "job", "job_id");
        Map<String, Object> mapGroup = this.getFilterMap(listRoleRelation, "type", "group", "group_id");

        // Step1：分类处理授权信息，得到role
        if (listUserAuth != null && listUserAuth.size() > 0) {
            int iAuthLen = listUserAuth.size();
            for (int i = 0; i < iAuthLen; i++) {
                Map<String, Object> mapUserAuthVO = listUserAuth.get(i);

                String authType = StrUtil.getMapValue(mapUserAuthVO, "auth_type");
                if ("role".equals(authType)) {
                    // 直接获取
                    mapRoleIds.put(StrUtil.getMapValue(mapUserAuthVO, "role_id"), "");
                } else {
                    // 关联 role_relation获取

                    if ("org".equals(authType)) {

                        String orgId = StrUtil.getMapValue(mapUserAuthVO, "organization_id");
                        this.getOrgRole(mapRoleIds, mapOrg, mapGroup, orgId);

                    } else if ("job".equals(authType)) {

                        String key = StrUtil.getMapValue(mapUserAuthVO, "job_id");
                        if (mapJob.containsKey(key)) {
                            mapRoleIds.put(StrUtil.getMapValue(mapJob, key), "");
                        }
                    } else if ("group".equals(authType)) {

                        String key = StrUtil.getMapValue(mapUserAuthVO, "group_id");
                        if (mapGroup.containsKey(key)) {
                            mapRoleIds.put(StrUtil.getMapValue(mapGroup, key), "");
                        }
                    }

                }

            }
        }

        // Step2：用户默认的组织机构权限，得到role
        String userOrgId = userVO.getOrganization_id();
        if (StrUtil.isNotNull(userOrgId)) {
            this.getOrgRole(mapRoleIds, mapOrg, mapGroup, userOrgId);
        }

        return mapRoleIds;
    }

    /** 得到组织机构拥有的角色 */
    private void getOrgRole(Map<String, String> mapRoleIds, Map<String, Object> mapOrg, Map<String, Object> mapGroup, String orgId) {

        if (mapOrg.containsKey(orgId)) {
            mapRoleIds.put(StrUtil.getMapValue(mapOrg, orgId), "");
        } else {
            // 如果组织机构没有配置role权限，则查找组织机构的分组，通过分组去找role
            Map<String, Object> mapParam0 = new HashMap<String, Object>();
            mapParam0.put("organization_id", orgId);
            List<Map<String, Object>> listGroupOrg = this.query("s_sysgroup_org", mapParam0);
            if (listGroupOrg != null && listGroupOrg.size() > 0) {
                for (int j = 0; j < listGroupOrg.size(); j++) {
                    Map<String, Object> mapGroupOrg = listGroupOrg.get(j);
                    String key2 = StrUtil.getMapValue(mapGroupOrg, "group_id");
                    if (mapGroup.containsKey(key2)) {
                        mapRoleIds.put(StrUtil.getMapValue(mapGroup, key2), "");
                    }
                }
            }

        }
    }

    /** 按条件过滤得到Map */
    private Map<String, Object> getFilterMap(List<Map<String, Object>> listData, String filterColmun, String filterVal, String keyColumn) {

        List<Map<String, Object>> listOrg = listData.stream().filter(a -> a.get(filterColmun).equals(filterVal)).collect(Collectors.toList());
        Map<String, Object> mapResult = ConvertUtil.list2Map(listOrg, keyColumn);

        if (mapResult == null) {
            mapResult = new HashMap<String, Object>();
        }

        return mapResult;
    }
}
