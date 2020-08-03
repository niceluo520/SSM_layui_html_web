package com.cbox.base.processor.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
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
import com.cbox.business.system.user.userlogin.util.LoginUtil;

@Aspect
@Component
public class ButtonPermissionAspect {
    private static final Logger log = LoggerFactory.getLogger("ButtonPermissionAspect");

    @Autowired
    private LoginUtil loginUtil;

    @Pointcut("@annotation(com.cbox.base.processor.aspect.ButtonPermission)")
    public void permissionMethodPointcut() {
    }

    @Around("permissionMethodPointcut()")
    public Object interceptor(ProceedingJoinPoint point) throws Throwable {
        Method e = ((MethodSignature) point.getSignature()).getMethod();
        ButtonPermission buttonPermission = (ButtonPermission) e.getAnnotation(ButtonPermission.class);

        String comment = "";
        String code = "";

        if (null != buttonPermission) {
            code = buttonPermission.authid();
            comment = buttonPermission.name() + "的";
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        if (this.loginUtil.checkButtonPermission(request, code)) {
            Object returnValue = point.proceed();
            return returnValue;
        } else {

            boolean isAjax = loginUtil.isAjax(request);
            if (isAjax) {
                JSONObject json = new JSONObject();
                json.put("retCode", -1);
                json.put("retMsg", "对不起，您没有" + comment + "操作权限");
                json.put("retData", "");
                log.error(json.getString("retMsg"));
                return json;
            } else {
                // 就像controller跳转页面一样返回就行，跳转到无权限的提示页面
                return "/common/unPermission";
            }

        }

    }

}