package com.cbox.business.system.user.userlogin.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cbox.base.cache.EhcacheUtil;
import com.cbox.base.cache.ProjectCache;
import com.cbox.base.util.CookieUtil;
import com.cbox.base.util.ObjUtil;
import com.cbox.base.util.StrUtil;
import com.cbox.business.system.user.userlogin.bean.SysResourceVO;
import com.cbox.business.system.user.userlogin.bean.SysUserVO;
import com.cbox.business.system.user.userlogin.dao.SysResourceMapper;
import com.cbox.constant.SysConstant;

@Service
public class LoginUtil {
    private static final Logger logger = LoggerFactory.getLogger(LoginUtil.class);

    @Autowired
    private SysResourceMapper resourceMapper;

    public static String LOGIN_PATH;// 登录页面
    public static String TOKEN_COOKIE_KEY;// token在cookie中的关键字
    public static int TOKEN_EXPIRY_TIME;// token的过期时间，单位：秒。

    @Value("${login.path:/login/index}")
    public void setLoginPath(String loginPath) {
        LoginUtil.LOGIN_PATH = loginPath;
    }

    @Value("${token.cookie.name:token_cookie_key}")
    public void setTokenCookieKey(String tokenCookieKey) {
        LoginUtil.TOKEN_COOKIE_KEY = tokenCookieKey;
    }

    @Value("${token.expiry.time:3600}")
    public void setTokenExpiryTime(int tokenExpiryTime) {
        LoginUtil.TOKEN_EXPIRY_TIME = tokenExpiryTime;
    }

    /** 检验token是否有效：不为空+在缓存中存在+不过期 */
    public boolean checkToken(String token) {
        if (StrUtil.isNotNull(token) && EhcacheUtil.getInstance().checkValid(token)) {
            return true;
        }

        return false;
    }

    /** 刷新token的过期时间。 */
    public void refreshTokenExpiryTime(String token) {
        EhcacheUtil.getInstance().set(token, TOKEN_EXPIRY_TIME);
    }

    /** 从请求中获取用户信息，实质上是通过请求中token从缓存中获取用户信息的。 */
    public SysUserVO getUserInfo(HttpServletRequest request) {

        String token = CookieUtil.getCookie(request, LoginUtil.TOKEN_COOKIE_KEY);
        return getUserInfo(token);
    }

    /** 通过token获取用户信息 */
    public SysUserVO getUserInfo(String token) {

        Object obj = EhcacheUtil.getInstance().get(token);
        if (obj == null) {
            return null;
        } else {
            SysUserVO userVO = JSONObject.parseObject(obj.toString(), SysUserVO.class);
            return userVO;
        }

    }

    /**
     * refreshProjectCache: 刷新项目缓存
     *
     * @date: 2020年3月15日 下午10:47:05
     * @author cbox
     * @param isRefresh 是否强制刷新
     */
    public void refreshProjectCache(boolean isRefresh) {

        List<String> listCacheAllMenu = ProjectCache.getInstance().getListCacheAllMenu();

        if (isRefresh || ObjUtil.isNull(listCacheAllMenu)) {
            // 重新加载

            List<String> listAllMenu = new ArrayList<String>();
            // 从数据库中获取菜单数据
            List<SysResourceVO> listMenus = resourceMapper.getMenuResources();
            if (ObjUtil.isNotNull(listMenus)) {
                for (int i = 0; i < listMenus.size(); i++) {
                    SysResourceVO resVO = listMenus.get(i);
                    if (StrUtil.isNotNull(resVO.getUrl())) {// 过滤掉空的
                        listAllMenu.add(resVO.getUrl());
                    }
                }
            }

            ProjectCache.getInstance().setListCacheAllMenu(listAllMenu);
        }
    }

    /** 移除登录用户信息 */
    public void removeLoginUserInfo(HttpServletRequest request, HttpServletResponse response) {

        // 清除缓存中的token
        String token = CookieUtil.getCookie(request, LoginUtil.TOKEN_COOKIE_KEY);
        EhcacheUtil.getInstance().remove(token);

        // 清除response中的cookie
        Cookie cookie = new Cookie(TOKEN_COOKIE_KEY, (String) null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

    }

    /**
     * checkButtonPermission:校验页面按钮权限
     *
     * @date: 2020年3月16日 上午11:06:01
     * @author cbox
     * @param request
     * @param authid 按钮权限唯一编码
     * @return
     */
    public boolean checkButtonPermission(HttpServletRequest request, String authid) {

        boolean bPermission = false;

        SysUserVO sysUserVO = this.getUserInfo(request);
        if (null != sysUserVO) {
            if (SysConstant.SUPER_ADMIN.equals(sysUserVO.getIs_admin())) {
                bPermission = true;
            } else {
                Map<String, String> mapUserBtnAuthid = sysUserVO.getMapUserBtnAuthid();
                if (ObjUtil.isNotNull(mapUserBtnAuthid) && mapUserBtnAuthid.containsKey(authid)) {
                    bPermission = true;
                }
            }
        }

        return bPermission;
    }

    /**
     * 判断当前请求是否为异步请求.
     */
    public boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }

}