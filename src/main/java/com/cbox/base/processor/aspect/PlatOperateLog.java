package com.cbox.base.processor.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.Mapping;

import com.cbox.base.processor.aspect.type.BusinessType;
import com.cbox.base.processor.aspect.type.LogType;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface PlatOperateLog {
    /**
     * 模块
     */
    String value() default "";

    /**
     * 功能
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * 类型
     */
    public LogType logType() default LogType.OPERATE;

}