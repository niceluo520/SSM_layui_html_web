package com.cbox.business.system.user.userpwd.controller;

import java.net.URLDecoder;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbox.base.common.controller.BaseController;
import com.cbox.base.protocol.ResponseBodyVO;
import com.cbox.base.protocol.ServerRspUtil;
import com.cbox.base.util.RSAUtil;
import com.cbox.business.system.user.userlogin.bean.SysUserVO;
import com.cbox.business.system.user.userpwd.service.UserPwdService;

@Controller
@RequestMapping("/system/user")
public class UserPwdController extends BaseController {

    @Autowired
    private UserPwdService userPwdService;

    /** 修改密码页面 */
    @RequestMapping("/toEditPwd")
    public String toEditPwd(Model model, HttpServletRequest request) {
        Map<String, Object> param = copyParam(request);
        model.addAttribute("item", param);

        // RSA 加密
        String module = "";
        String empoent = "";
        try {
            // 公钥
            RSAPublicKey rsap = (RSAPublicKey) RSAUtil.getKeyPair().getPublic();
            module = rsap.getModulus().toString(16);
            empoent = rsap.getPublicExponent().toString(16);
        } catch (Exception e) {
            // FileLogger.getLogger().warn("RAS加密文件异常:", e);
            System.out.println(">>>>>>>>>>>>>RAS加密文件异常<<<<<<<<<<<<<<<");
            e.printStackTrace();
        }
        model.addAttribute("rsa_module", module);
        model.addAttribute("rsa_empoent", empoent);

        return "/business/system/user/userpwd/editPwd";
    }

    /** 修改密码 */
    @RequestMapping(value = "/doEditPwd", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doEditPwd(HttpServletRequest request) throws Exception {
        Map<String, Object> param = copyParam(request);
        if (!param.containsKey("J_password") || StringUtils.isBlank(param.get("J_password").toString())) {
            return ServerRspUtil.errorToMap("关键参数丢失");
        }
        if (!param.containsKey("J_newpassword") || StringUtils.isBlank(param.get("J_newpassword").toString())) {
            return ServerRspUtil.errorToMap("关键参数丢失");
        }
        /** 解密 账号、密码 */
        PrivateKey pk = RSAUtil.getKeyPair().getPrivate();
        String password = RSAUtil.decryptJsData(pk, param.get("J_password").toString());
        String newpassword = RSAUtil.decryptJsData(pk, param.get("J_newpassword").toString());
        String oldPwd = URLDecoder.decode(password, "UTF-8");
        String newPwd = URLDecoder.decode(newpassword, "UTF-8");
        /** 弱密码验证 */
        String reg = "^(?![\\d]+$)(?![a-zA-Z]+$)(?![^\\da-zA-Z]+$).{6,12}$";
        if (!newPwd.matches(reg)) {
            return ServerRspUtil.errorToMap("密码必须为6-12位数字与字母的组合");
        }
        SysUserVO userInfo = getSysuser(request);
        ResponseBodyVO<String> resp = userPwdService.doEditPwd(userInfo, oldPwd, newPwd);
        return ServerRspUtil.genResponseMap(resp);
    }

}
