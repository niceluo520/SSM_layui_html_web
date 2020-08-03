package com.cbox.base.common.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cbox.business.system.user.userlogin.bean.SysUserVO;
import com.cbox.business.system.user.userlogin.util.LoginUtil;

public class BaseController {

    @Autowired
    protected LoginUtil loginUtil;

    /**
     * 当前模块视图的前缀
     */
    private String viewPrefix;

    protected BaseController() {
        setViewPrefix(defaultViewPrefix());
    }

    /**
     * 设置当前模块视图的前缀
     */
    public void setViewPrefix(String viewPrefix) {
        if (viewPrefix.startsWith("/")) {
            viewPrefix = viewPrefix.substring(1);
        }
        this.viewPrefix = viewPrefix;
    }

    public String getViewPrefix() {
        return viewPrefix;
    }

    /**
     * 获取视图名称：即prefixViewName + "/" + suffixName
     *
     * @return
     */
    public String viewName(String suffixName) {
        if (!suffixName.startsWith("/")) {
            suffixName = "/" + suffixName;
        }
        return getViewPrefix() + suffixName;
    }

    /**
     * 初始化默认当前模块视图的前缀 1、获取当前类头上的@RequestMapping中的value作为前缀
     * 
     * @return
     */
    protected String defaultViewPrefix() {
        String currentViewPrefix = "";
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(getClass(), RequestMapping.class);
        if (requestMapping != null && requestMapping.value().length > 0) {
            currentViewPrefix = requestMapping.value()[0];
        }

        return currentViewPrefix;
    }

    public String getParameter(HttpServletRequest request, String key) {
        return getParameter(request, key, "");
    }

    public String getParameter(HttpServletRequest request, String key, String defaultValue) {
        String value = request.getParameter(key);
        if (StringUtils.isBlank(value)) {
            value = defaultValue;
        }
        return value;
    }

    /**
     * 获取系统当前登录用户
     * 
     * @return Sysuser
     */
    protected SysUserVO getSysuser(HttpServletRequest request) {
        return loginUtil.getUserInfo(request);
    }

    /**
     * 获取当前用户id
     * 
     * @return String
     */
    protected String getSysUserId(HttpServletRequest request) {
        SysUserVO sysUserVO = loginUtil.getUserInfo(request);
        return sysUserVO.getUser_id();
    }

    /**
     * 获取当前用户rec_id
     * 
     * @return String
     */
    protected String getSysUserRecId(HttpServletRequest request) {
        SysUserVO sysUserVO = loginUtil.getUserInfo(request);
        return sysUserVO.getRec_id();
    }

    /** 把request参数转换成map */
    public Map<String, Object> copyParam(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String param = parameterNames.nextElement();
            params.put(param, request.getParameter(param));
        }
        return params;
    }

    public static Map<String, Object> mapStringToMap(String str) {
        str = str.substring(1, str.length() - 1);
        String[] strs = str.split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        for (String string : strs) {
            String key = string.split("=")[0];
            String value = string.split("=")[1];
            map.put(key, value);
        }
        return map;
    }

    protected String validateParams(String paramValue, String is_nullable, String data_length, String pattern, String comment) throws Exception {
        if (Boolean.valueOf(is_nullable)) {
            if (StringUtils.isBlank(paramValue.toString()) || "null".equals(paramValue)) {
                return comment + "不能为空值";
            }
        }
        if (StringUtils.isNotBlank(paramValue.toString()) && !"null".equals(paramValue)) {
            if (StringUtils.isNotBlank(data_length) && (paramValue.length() > Integer.valueOf(data_length))) {
                return comment + "长度需小于" + comment;
            }
            if (StringUtils.isNotBlank(pattern)) {
                Class<?> clz = Class.forName("com.cbox.base.validate.RegexValidateUtil");
                Object o = clz.newInstance();
                boolean b = (Boolean) clz.getMethod(pattern, String.class).invoke(o, paramValue);
                if (!b) {
                    String t_comment = (String) clz.getMethod(pattern + "Tip").invoke(o);
                    return comment + "格式不符，格式要求：" + t_comment;
                }
            }
        }
        return "";
    }

}
