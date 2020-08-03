package com.cbox.business.system.user.userlogin.controller;

import java.util.HashMap;
import java.util.List;
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
import com.cbox.base.protocol.ServerRspUtil;
import com.cbox.base.util.ObjUtil;
import com.cbox.business.system.user.userlogin.bean.SysResourceVO;
import com.cbox.business.system.user.userlogin.bean.SysUserVO;
import com.cbox.business.system.user.userlogin.service.SysUserService;
import com.cbox.business.system.user.userlogin.util.LoginUtil;

@Controller
public class IndexController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private SysUserService userService;

    @Autowired
    private LoginUtil loginUtil;

    /** 首页 */
    @RequestMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        return "redirect:/home";
    }

    /** 登录后跳转至首页 */
    @RequestMapping("/home")
    public String home(Model model, HttpServletRequest request) {
        /** 读取当前登陆用户的权限 */
        SysUserVO sysuser = getSysuser(request);

        Map<String, Object> params = new HashMap<String, Object>(1);
        params.put("rec_id", sysuser.getRec_id());
        // Map<String, Object> userInfo = userService.findByUserId(params);
        model.addAttribute("userInfo", sysuser);
        model.addAttribute("userRecId", sysuser.getRec_id());
        // model.addAttribute("projectName", loginService.getProjectName());
        return "/home/mainFrame";
    }

    /** 框架首页的 主页*/
    @RequestMapping("/home/main")
    public String homeMain(Model model, HttpServletRequest request) {
        return "/home/main";
    }

    @RequestMapping(value = "/menus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> menus(HttpServletRequest request) {
        /** 读取当前登陆用户 */
        SysUserVO userInfo = loginUtil.getUserInfo(request);

        // 当前用户拥有的所有菜单
        List<SysResourceVO> listUserMenus = userService.getUserMenus(userInfo);

        if (ObjUtil.isNotNull(listUserMenus)) {

            return ServerRspUtil.successToMap("获取用户菜单成功", listUserMenus);
        } else {
            return ServerRspUtil.errorToMap("获取用户菜单失败");
        }
    }

}
