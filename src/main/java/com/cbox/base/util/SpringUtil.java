package com.cbox.base.util;

import javax.servlet.ServletContextEvent;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static Object getBean(String beanId) {
        return applicationContext.getBean(beanId);
    }

    public static Object getBean(String beanId, ServletContextEvent event) {
        setApplicationContext(event);
        return getBean(beanId);
    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        applicationContext = ac;
    }

    /**
     * 用于spring容器还未来得及加载的情况
     */
    public static void setApplicationContext(ServletContextEvent event) {
        applicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
    }
}
