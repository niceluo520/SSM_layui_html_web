package com.cbox.base.processor.aspect;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.cbox.base.util.GlobalRecIdUtil;
import com.cbox.base.util.IpUtils;
import com.cbox.business.common.log.service.SysOperateLogService;
import com.cbox.business.system.user.userlogin.bean.SysUserVO;
import com.cbox.business.system.user.userlogin.util.LoginUtil;

@Aspect
@Component
public class PlatOperateLogAspect {
    private static final Logger log = LoggerFactory.getLogger("PlatOperateLog");

    @Autowired
    private SysOperateLogService sysOperateLogService;

    @Pointcut("@annotation(com.cbox.base.processor.aspect.PlatOperateLog)")
    public void operateMethodPointcut() {
    }

    @AfterReturning(pointcut = "operateMethodPointcut()", returning = "returnValue")
    public void log(JoinPoint point, Object returnValue) {
        try {
            Method e = ((MethodSignature) point.getSignature()).getMethod();
            PlatOperateLog platOperate = (PlatOperateLog) e.getAnnotation(PlatOperateLog.class);
            String operateDesc = platOperate.value();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String requestUri = request.getRequestURI();
            String requestParam = this.getRequestParam(request);
            LoginUtil loginUtil = new LoginUtil();
            SysUserVO userVO = loginUtil.getUserInfo(request);
            String staffId = "";
            String staffName = "";
            if (userVO != null) {
                staffId = userVO.getUser_id();
                staffName = userVO.getUser_name();
            }

            JSONObject json = new JSONObject();
            json.put("rec_id", GlobalRecIdUtil.nextRecId());
            json.put("operate_url", requestUri);
            json.put("operate_desc", operateDesc);
            json.put("in_param", requestParam);
            json.put("out_param", returnValue.toString());
            json.put("rec_person", staffId);
            json.put("rec_person_name", staffName);
            json.put("operate_ip", IpUtils.getIpAddress(request));
            json.put("operate_result", this.getOperateResult(returnValue));
            log.info(json.toString());
            sysOperateLogService.addOperateLog(json);
        } catch (Exception arg15) {
            arg15.printStackTrace();
        }

    }

    private String getRequestParam(HttpServletRequest request) {
        String requestParam = "";
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            requestParam = request.getQueryString();
        } else if ("POST".equalsIgnoreCase(request.getMethod())) {
            Enumeration<?> en = request.getParameterNames();
            String name = "";
            String value = "";
            HashMap<String, String> temp = new HashMap<String, String>();

            while (en.hasMoreElements()) {
                name = (String) en.nextElement();
                value = request.getParameter(name);
                temp.put(name, value);
            }

            requestParam = temp.toString();
        }

        return requestParam;
    }

    @SuppressWarnings("rawtypes")
    private String getOperateResult(Object returnValue) {
        if (returnValue == null || returnValue.equals("")) {
            return "";
        }
        String result = "0";
        if (returnValue instanceof String) {
            String str = returnValue.toString();
            if (str.startsWith("{") && str.endsWith("}")) {
                JSONObject e = JSONObject.parseObject(str);
                if (e.containsKey("retCode")) {
                    result = String.valueOf(e.get("retCode"));
                }
            }
        } else if (returnValue instanceof HashMap) {
            HashMap rmap = (HashMap) returnValue;
            if (rmap.containsKey("retCode")) {
                result = String.valueOf(rmap.get("retCode"));
            }
        }
        return result;
    }

}