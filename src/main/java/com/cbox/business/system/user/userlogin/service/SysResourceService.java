package com.cbox.business.system.user.userlogin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbox.base.common.service.BaseCRUDService;
import com.cbox.base.util.ObjUtil;
import com.cbox.business.system.user.userlogin.bean.SysResourceVO;
import com.cbox.business.system.user.userlogin.bean.SysRoleResourceVO;
import com.cbox.business.system.user.userlogin.bean.SysUserVO;
import com.cbox.business.system.user.userlogin.dao.SysResourceMapper;
import com.cbox.constant.SysConstant;

@Service("sysResourceService")
@Transactional
public class SysResourceService extends BaseCRUDService {

    @Autowired
    private SysResourceMapper resourceMapper;

    @Autowired
    private SysUserAuthService sysAuthService;

    /**
     * getUserMenus:得到执行用户的资源数据
     *
     * @date: 2020年3月12日 上午11:41:30
     * @author cbox
     * @param user
     * @return
     */
    public List<SysResourceVO> getUserResources(SysUserVO userVO) {



        List<SysResourceVO> listAllMenus = getAllSysMenus();

        if (SysConstant.SUPER_ADMIN.equals(userVO.getIs_admin())) {
            // 超级管理员，拥有最大权限。
            return listAllMenus;
        } else {
            Map<String, SysResourceVO> mapUserResources = new HashMap<String, SysResourceVO>();

            // Step1：得到用户的角色，并转成list
            Map<String, String> mapRoleIds = sysAuthService.getUserRoles(userVO);
            List<String> listRoleIds = ObjUtil.mapToListByKey(mapRoleIds);

            if (ObjUtil.isNotNull(listRoleIds)) {
                // Step2：根据角色得到所有关联的资源id，以及每个资源的子资源权限配置
                List<SysRoleResourceVO> listRoleResources = resourceMapper.getResourcesByRoles(listRoleIds);

                // Step3：计算实际的资源数据
                if (ObjUtil.isNotNull(listRoleResources)) {

                    // 把listAllMenus转成map方便使用
                    Map<String, SysResourceVO> mapAllMenus = ObjUtil.listToMap(listAllMenus, "rec_id");

                    this.calUserBtnResource(listRoleResources, mapAllMenus, mapUserResources);

                }
            }

            List<SysResourceVO> listUserResources = new ArrayList<SysResourceVO>();
            // 按照sort排序，生成用户有权限的资源
            if (ObjUtil.isNotNull(listAllMenus)) {
                for (SysResourceVO menuVO : listAllMenus) {
                    String resourceId = menuVO.getRec_id();
                    if (mapUserResources.containsKey(resourceId)) {
                        listUserResources.add(mapUserResources.get(resourceId));
                    }
                }
            }

            return listUserResources;
        }

    }

    /**
     * calUserBtnResource: 计算用户拥有的按钮权限
     *
     * @date: 2020年3月16日 下午8:47:56
     * @author cbox
     * @param listRoleResources 用户所有的角色拥有的资源信息（包括页面信息和页面内的按钮权限信息）
     * @param mapAllMenus 页面所有的menu（包含页面内的按钮权限信息）
     * @param mapUserResources 输出（用户拥有的资源权限）
     */
    private void calUserBtnResource(List<SysRoleResourceVO> listRoleResources, Map<String, SysResourceVO> mapAllMenus, Map<String, SysResourceVO> mapUserResources) {

        for (SysRoleResourceVO roleResourceVO : listRoleResources) {

            String resourceId = roleResourceVO.getResource_id();
            String hasPermissionIds = roleResourceVO.getHaspermission_ids();// 有权限的子资源集合。默认为*。
            String noPermissionIds = roleResourceVO.getNopermission_ids();// 无权限的子资源集合。默认为*，全无为all。

            SysResourceVO resVO = mapUserResources.get(resourceId); // 当前已经存在的资源。如果有多个resourceId设置信息重叠，会自动按最小权限计算。
            if(resVO==null) {
                resVO = mapAllMenus.get(resourceId);// 当前关联的资源数据
            }
            
            // 重新计算子资源权限
            if (resVO != null) {                
                Map<String, SysResourceVO> mapSubHasResources = this.convertListToMap(resVO.getListHasBtnResources());

                List<SysResourceVO> listSubHasResources = new ArrayList<SysResourceVO>();// 实际的有效子资源
                /* 优先级设定：有权限>无权限，即如果有权限有配置，则只生效有权限的，忽略无权限的配置。 */
                if ("*".equals(hasPermissionIds)) {
                    if ("*".equals(noPermissionIds)) {
                        // 全部有权限，无需更改
                    } else if ("all".equals(noPermissionIds)) {
                        // 全部没有权限
                        resVO.setListHasBtnResources(listSubHasResources);
                    } else {
                        // 按没权限的过滤
                        String[] resIds = noPermissionIds.split(",");
                        for (int j = 0; j < resIds.length; j++) {
                            if (mapSubHasResources.containsKey(resIds[j])) {
                                mapSubHasResources.remove(resIds[j]);
                            }
                        }

                        listSubHasResources=ObjUtil.mapToListByValue(mapSubHasResources);
                        resVO.setListHasBtnResources(listSubHasResources);
                    }

                } else {
                    // 按照有权限的控制
                    String[] resIds = hasPermissionIds.split(",");
                    for (int j = 0; j < resIds.length; j++) {
                        SysResourceVO subResVO = mapSubHasResources.get(resIds[j]);
                        if (subResVO != null) {
                            listSubHasResources.add(subResVO);
                        }
                    }
                    resVO.setListHasBtnResources(listSubHasResources);
                }

                mapUserResources.put(resourceId, resVO);
            }
        }

    }



    /**
     * getAllSysMenus: 得到所有的资源数据，自动把子资源归类合并进来。
     *
     * @date: 2020年3月12日 上午11:30:46
     * @author cbox
     * @return
     */
    private List<SysResourceVO> getAllSysMenus() {

        // 从数据库中获取菜单数据
        List<SysResourceVO> listMenus = resourceMapper.getMenuResources();

        // 从数据库中获取button资源数据，并按parentId为key归类
        List<SysResourceVO> listBtnResources = resourceMapper.getBtnResources();
        Map<String, List<SysResourceVO>> mapBtnResources = this.groupToMapByParentId(listBtnResources);


        boolean hasBtnRes = false;
        if (listBtnResources != null && listBtnResources.size() > 0) {
            hasBtnRes = true;
        }

        // 把每个菜单的资源关联上
        if (ObjUtil.isNotNull(listMenus)) {
            int iMenuLen = listMenus.size();
            for (int i = 0; i < iMenuLen; i++) {
                SysResourceVO menuVO = listMenus.get(i);
                String recId = menuVO.getRec_id();

                // 关联子资源
                if (hasBtnRes) {
                    if (mapBtnResources.containsKey(recId)) {
                        menuVO.setListHasBtnResources(mapBtnResources.get(recId));// 默认当前用户拥有页面所有资源权限
                    }
                }

            }
        }

        return listMenus;
    }


    /** 把List按照parentId归类为map */
    public Map<String, List<SysResourceVO>> groupToMapByParentId(List<SysResourceVO> listBtnResources) {
        Map<String, List<SysResourceVO>> mapBtnResources = new HashMap<String, List<SysResourceVO>>();// key-parentId

        if (listBtnResources != null && listBtnResources.size() > 0) {

            int iBtnLength = listBtnResources.size();
            String oldParentId = "";
            List<SysResourceVO> listBtn = new ArrayList<SysResourceVO>();
            for (int i = 0; i < iBtnLength; i++) {
                SysResourceVO btnResourceVO = listBtnResources.get(i);
                String parentId = btnResourceVO.getParent_id();

                if (!oldParentId.equals(parentId) && i > 0) {
                    mapBtnResources.put(oldParentId, listBtn);
                    oldParentId = parentId;
                    listBtn = new ArrayList<SysResourceVO>();
                }

                listBtn.add(btnResourceVO);
                oldParentId = parentId;

                if (i == iBtnLength - 1) {
                    mapBtnResources.put(oldParentId, listBtn);
                }
            }
        }

        return mapBtnResources;
    }

    /** 把list按照rec_id转成map */
    private Map<String, SysResourceVO> convertListToMap(List<SysResourceVO> listRes) {

        Map<String, SysResourceVO> mapRes = new HashMap<String, SysResourceVO>();
        if (listRes != null && listRes.size() > 0) {
            for (SysResourceVO resVO : listRes) {
                mapRes.put(resVO.getRec_id(), resVO);
            }
        }

        return mapRes;

    }

}
