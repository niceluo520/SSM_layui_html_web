package com.cbox.base.processor.interceptor;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.cbox.base.cache.ProjectCache;
import com.cbox.base.util.CookieUtil;
import com.cbox.base.util.ObjUtil;
import com.cbox.base.util.StrUtil;
import com.cbox.business.system.user.userlogin.bean.SysUserVO;
import com.cbox.business.system.user.userlogin.util.JwtUtil;
import com.cbox.business.system.user.userlogin.util.LoginUtil;
import com.cbox.constant.BusiConstant;
import com.cbox.constant.SysConstant;

import io.jsonwebtoken.Claims;

public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    // 更新用户最后在线时间间隔分钟
    public static int interval = 10;

    private LoginUtil loginUtil;
    private JwtUtil jwtUtil;

    public AuthInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        if (this.loginUtil == null) {
            WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            this.loginUtil = context.getBean(LoginUtil.class);
            this.jwtUtil = context.getBean(JwtUtil.class);
        }

        String token = CookieUtil.getCookie(request, LoginUtil.TOKEN_COOKIE_KEY);
        boolean isLive = loginUtil.checkToken(token);
        boolean isAjax = loginUtil.isAjax(request);
        if (isLive) {
            loginUtil.refreshTokenExpiryTime(token);// 更新过期时间

            Claims claims = jwtUtil.parseJWT(token);
            if (claims != null) {
                // 取出token中的数据

            }

            SysUserVO sysUserVO = loginUtil.getUserInfo(token);

            if (sysUserVO.getIs_admin().equals(SysConstant.SUPER_ADMIN)) {
                // 如果是管理员，则拥有最高权限，无需进行权限判断
            } else {
                // 如果是普通用户，则需要判断其访问权限：判断用户是否拥有访问页面及页面ajax请求权限
                if (!hasPermission(request, sysUserVO)) {
                    // 无权限
                    if (isAjax) {
                        JSONObject json = new JSONObject();
                        json.put(BusiConstant.RET_CODE, BusiConstant.ERROR_PERMISSION);
                        json.put(BusiConstant.RET_MSG, "对不起，您没有访问权限");
                        json.put(BusiConstant.RET_DATA, "");
                        response.setContentType("application/json;charset=UTF-8");
                        PrintWriter writer = response.getWriter();
                        writer.print(json);
                    } else {
                        response.sendRedirect(request.getContextPath() + BusiConstant.UNPERMISSION_PAGE);
                    }
                    return false;
                }
            }

        } else {
            // 过期了，提示重新登录。
            if (isAjax) {
                JSONObject json = new JSONObject();
                json.put(BusiConstant.RET_CODE, BusiConstant.ERROR_TOKEN);
                json.put(BusiConstant.RET_MSG, "对不起，登录已过期请您重新登录");
                json.put(BusiConstant.RET_DATA, "");
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                writer.print(json);
            } else {
                // URL访问，直接跳转登录页面
                response.sendRedirect(request.getContextPath() + LoginUtil.LOGIN_PATH);
            }

            return false;
        }

        return true;

    }

    /**
     * 是否拥有访问页面的权限
     */
    protected boolean hasPermission(HttpServletRequest request, SysUserVO sysUserVO) {
        boolean bPermission = true;

        String urlPath = request.getRequestURI();
        if (StrUtil.isNotNull(urlPath)) {
            int index = urlPath.indexOf("/", 2);
            if (index > -1) {
                urlPath = urlPath.substring(index);
            }

            // 拦截器只对菜单权限进行校验。按钮权限通过AOP(ButtonPermissions)进行校验
            bPermission = checkPermission(urlPath, sysUserVO.getMapUserMenuUrl());

        }

        return bPermission;
    }

    /** 判断用户是否有访问菜单的权限 */
    private boolean checkPermission(String urlPath, Map<String, String> mapUserMenuUrl) {

        boolean bPermission = true;

        // 只对系统菜单范围内的请求进行权限判断
        List<String> cacheAllMenu = ProjectCache.getInstance().getListCacheAllMenu();
        if (null != cacheAllMenu && cacheAllMenu.size() > 0) {
            if (cacheAllMenu.contains(urlPath)) {
                // 是菜单权限，则进行用户权限判断
                if (ObjUtil.isNull(mapUserMenuUrl) || !mapUserMenuUrl.containsKey(urlPath)) {
                    bPermission = false;
                }
            }
        }

        return bPermission;

    }

    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object obj, ModelAndView m) throws Exception {
    }

    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object obj, Exception e) throws Exception {
    }
}