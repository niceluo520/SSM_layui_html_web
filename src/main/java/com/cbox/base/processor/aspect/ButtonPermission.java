package com.cbox.base.processor.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.Mapping;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ButtonPermission {
    String authid();// 按钮权限标识，必须跟库表s_sysresource中的authid字段中的值保持一致才能生效。

    String name();// 描述名称
}