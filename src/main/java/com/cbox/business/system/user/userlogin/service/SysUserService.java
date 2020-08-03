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
import com.cbox.base.util.StrUtil;
import com.cbox.business.system.user.userlogin.bean.SysResourceVO;
import com.cbox.business.system.user.userlogin.bean.SysUserVO;
import com.cbox.business.system.user.userlogin.dao.SysUserMapper;

/**
 * Created by Administrator on 2017/2/7 0007.
 */
@Service
@Transactional
public class SysUserService extends BaseCRUDService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysResourceService sysResourceService;

    /**
     * querySysUser:查询得到用户的信息。
     *
     * @date: 2020年3月16日 下午8:37:56
     * @author cbox
     * @param params： 输入key=user_id
     * @return
     */
    public SysUserVO querySysUser(Map<String, Object> params) {

        SysUserVO userVO = sysUserMapper.querySysUser(params);

        return userVO;
    }

    /** 加载权限信息 */
    public void loadAuthinfo(SysUserVO userVO) {

        if (null != userVO) {
            List<SysResourceVO> listRes = sysResourceService.getUserResources(userVO);
            userVO.setListUserResources(listRes);

            Map<String, String> mapUserMenuUrl = new HashMap<String, String>();// 用户有权限的菜单Url，key=url。用来做菜单权限判断
            Map<String, String> mapUserBtnAuthid = new HashMap<String, String>();// 用户有权限的按钮authid，key=authid。用来做按钮权限判断
            if (ObjUtil.isNotNull(listRes)) {
                for (SysResourceVO resVO : listRes) {
                    String url = resVO.getUrl();
                    if (StrUtil.isNotNull(url)) {
                        mapUserMenuUrl.put(url, "");
                    }

                    List<SysResourceVO> listHasResources = resVO.getListHasBtnResources();
                    if (ObjUtil.isNotNull(listHasResources)) {
                        for (SysResourceVO btnResVO : listHasResources) {
                            if (StrUtil.isNotNull(btnResVO.getAuthid())) {
                                mapUserBtnAuthid.put(btnResVO.getAuthid(), "");
                            }
                        }

                    }
                }
            }

            userVO.setMapUserMenuUrl(mapUserMenuUrl);
            userVO.setMapUserBtnAuthid(mapUserBtnAuthid);
        }

    }

    /**
     * getUserMenus: 得到用户的菜单。规则：①只取顶级模块；②设置子菜单childMenus
     *
     * @date: 2020年3月16日 下午8:39:04
     * @author cbox
     * @param userInfo
     * @return
     */
    public List<SysResourceVO> getUserMenus(SysUserVO userInfo) {
        List<SysResourceVO> listMenus = new ArrayList<SysResourceVO>();
        List<SysResourceVO> listUserResources = userInfo.getListUserResources();

        if (listUserResources != null && listUserResources.size() > 0) {
            for (int i = 0; i < listUserResources.size(); i++) {
                SysResourceVO resVO = listUserResources.get(i);

                // 只取顶级的模块
                if ("0".equals(resVO.getParent_id())) {
                    listMenus.add(resVO);
                }
            }
        }

        // 设置子菜单childMenus。只有这里需要用到子菜单
        if (ObjUtil.isNotNull(listMenus)) {
            Map<String, List<SysResourceVO>> mapMenuResources = sysResourceService.groupToMapByParentId(listUserResources);

            for (SysResourceVO menuVO : listMenus) {
                String recId = menuVO.getRec_id();

                if (mapMenuResources.containsKey(recId)) {
                    menuVO.setChildMenus(mapMenuResources.get(recId));
                }
            }
        }

        return listMenus;

    }

}
