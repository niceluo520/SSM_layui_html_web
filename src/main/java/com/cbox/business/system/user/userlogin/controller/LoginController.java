package com.cbox.business.system.user.userlogin.controller;

import java.net.URLDecoder;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbox.base.common.controller.BaseController;
import com.cbox.base.protocol.ResponseBodyVO;
import com.cbox.base.protocol.ServerRspUtil;
import com.cbox.base.util.CookieUtil;
import com.cbox.base.util.RSAUtil;
import com.cbox.base.util.StrUtil;
import com.cbox.business.system.user.userlogin.bean.LoginReqVO;
import com.cbox.business.system.user.userlogin.bean.SysUserVO;
import com.cbox.business.system.user.userlogin.service.LoginService;
import com.cbox.business.system.user.userlogin.service.SysUserService;
import com.cbox.business.system.user.userlogin.util.LoginUtil;
import com.cbox.constant.SysConstant;

/**
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private LoginUtil loginUtil;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 跳转到登录页
     */
    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
        addRSAParam(model);// RSA公钥参数
        return "/business/system/user/userlogin/login";
    }

    /**
     * 登录
     */
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    // @PlatOperateLog(value = "用户登录", logType = LogType.LOGIN)
    public Map<String, Object> doLogin(LoginReqVO evt, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

        ResponseBodyVO<SysUserVO> resp = new ResponseBodyVO<SysUserVO>();

        // 验证验证码
        // String veryCode = evt.getVeryCode();
        // if (StrUtil.isNull(veryCode)) {
        // return ServerRspUtil.errorToMap("验证码为空");
        // } else {
        // String validateC = (String) request.getSession().getAttribute(VeryCodeController.IMG_CODE_SESSION_KEY);
        // if (validateC == null) {
        // return ServerRspUtil.errorToMap("请重新获取验证码");
        // }
        //
        // String validateCL = validateC.toLowerCase();
        // String veryCodeL = veryCode.toLowerCase();
        // if (!validateCL.equals(veryCodeL)) {
        // return ServerRspUtil.errorToMap("验证码错误");
        // }
        // // 清除session中的验证码
        // request.getSession().removeAttribute(VeryCodeController.IMG_CODE_SESSION_KEY);
        // }

        // 解密 账号、密码
        PrivateKey pk = RSAUtil.getKeyPair().getPrivate();
        String accountId = RSAUtil.decryptJsData(pk, evt.getAccountId());
        String password = RSAUtil.decryptJsData(pk, evt.getPassword());

        accountId = URLDecoder.decode(accountId, "UTF-8");
        password = URLDecoder.decode(password, "UTF-8");

        // 登录处理
        resp = loginService.doLogin(accountId, password);
        if (resp.success()) {
            // 设置cookie，token通过cookie返回到客户端
            this.setCookie(resp.getBody(), request, response);
        }

        return ServerRspUtil.genResponseMap(resp);
    }

    /**
     * 跳转到首次登陆修改密码页面
     */
    @RequestMapping(value = "/first", method = RequestMethod.GET)
    public String first(Model model, HttpServletRequest request, HttpServletResponse response) {
        // 权限由token来保障，在拦截器中校验。
        addRSAParam(model);// RSA公钥参数
        return "/business/system/user/userlogin/first";
    }

    /** 首次登陆强制修改密码 */
    @RequestMapping(value = "/doEditPwd", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doEditPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> param = copyParam(request);

        if (StrUtil.isNull(param.get("password"))) {
            return ServerRspUtil.errorToMap("关键参数丢失");
        }
        /** 解密密码 */
        PrivateKey pk = RSAUtil.getKeyPair().getPrivate();
        String password = RSAUtil.decryptJsData(pk, param.get("password").toString());
        password = URLDecoder.decode(password, "UTF-8");

        /** 弱密码验证 */
        String reg = "^(?![\\d]+$)(?![a-zA-Z]+$)(?![^\\da-zA-Z]+$).{6,12}$";
        if (!password.matches(reg)) {
            return ServerRspUtil.errorToMap("密码必须为6-12位数字与字母的组合");
        }

        SysUserVO sysUserVO = loginUtil.getUserInfo(request);
        if (sysUserVO == null) {
            return ServerRspUtil.errorToMap("关键参数丢失");
        }

        String user_id = sysUserVO.getUser_id();

        // 因为重置密码需要使用到秘钥，但是缓存中没有，所以需要重新取用户对象。
        Map<String, Object> param1 = new HashMap<String, Object>();
        param1.put("user_id", user_id);
        SysUserVO userAllVO = sysUserService.querySysUser(param1);

        ResponseBodyVO<SysUserVO> resp = loginService.doEditPwd(userAllVO, user_id, password);

        return ServerRspUtil.genResponseMap(resp);

    }

    /**
     * 退出登陆
     */
    @RequestMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        loginUtil.removeLoginUserInfo(request, response);
        return "redirect:" + LoginUtil.LOGIN_PATH;
    }

    /**
     * 跳转到无权限页
     */
    @RequestMapping("/unPermission")
    public String unPermission(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        model.addAttribute("errorMsg", "对不起，您没有访问系统的权限！");
        return "/common/unpermission";
    }

    /** 添加RSA的（公钥）参数到Model，返回给前端页面 */
    private void addRSAParam(Model model) {
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
    }

    /** 设置token到cookie，以通过cookie返回给客户端 */
    private void setCookie(SysUserVO sysUserVO, HttpServletRequest request, HttpServletResponse response) {
        if (sysUserVO != null) {
            String loginToken = sysUserVO.getLoginToken();
            CookieUtil.setCookie(request, response, LoginUtil.TOKEN_COOKIE_KEY, loginToken, SysConstant.COOKIE_EXPIRY_TIME); // 将ticket放入cookie
        }
    }
}
