package com.cbox.business.common.route.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cbox.base.util.ObjUtil;
import com.cbox.base.util.StrUtil;
import com.cbox.business.system.user.userlogin.bean.SysUserVO;
import com.cbox.business.system.user.userlogin.util.LoginUtil;
import com.cbox.constant.SysConstant;

@Controller("authController")
@RequestMapping({ "/auth" })
public class AuthController {
    @Autowired
    private LoginUtil loginUtil;

    @RequestMapping(value = "/getPageBtnAuth", method = RequestMethod.POST)
    @ResponseBody
    public String list(HttpServletRequest request, String btns) {
        JSONObject json = new JSONObject();

        if (StrUtil.isNotNull(btns)) {
            String[] buttons = btns.split(",");

            SysUserVO sysUserVO = loginUtil.getUserInfo(request);
            if (sysUserVO.getIs_admin().equals(SysConstant.SUPER_ADMIN)) {
                for (String btn : buttons) {
                    json.put(btn, true);
                }
            } else {
                Map<String, String> mapUserBtnAuthid = sysUserVO.getMapUserBtnAuthid();
                for (String btn : buttons) {
                    if (ObjUtil.isNotNull(mapUserBtnAuthid) && mapUserBtnAuthid.containsKey(btn)) {
                        json.put(btn, true);
                    } else {
                        json.put(btn, false);
                    }
                }
            }
        }

        return json.toString();
    }
}