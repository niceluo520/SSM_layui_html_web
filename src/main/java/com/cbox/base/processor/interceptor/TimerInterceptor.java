package com.cbox.base.processor.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 记录Controller方法执行时间
 */
public class TimerInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger("ControllerTimer");

    private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("WatchExecuteTime");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long beginTime = System.currentTimeMillis(); // 1、开始时间
        startTimeThreadLocal.set(beginTime);
        return true;
    }

    /**
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。<br>
     * postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之 后，也就是在Controller的方法调用之后执行，<br>
     * 但是它会在DispatcherServlet进行视图的渲染之前执行，也就是说在这个方法中你可以对ModelAndView进行操 作。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行，
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        long endTime = System.currentTimeMillis();// 2、结束时间
        long beginTime = startTimeThreadLocal.get();// 得到线程绑定的局部变量（开始时间）
        long executeTime = endTime - beginTime;// 3、消耗的时间

        String execLog = request.getMethod() + "：" + request.getRequestURI();
        // log it
        if (logger.isDebugEnabled()) {
            execLog += ",  Cost：" + executeTime + "ms";
            logger.debug(execLog);
        } else if (executeTime > 500) {// 此处认为处理时间超过500毫秒的请求为慢请求
            execLog += ",  Cost：" + executeTime + "ms";
            logger.info(execLog);
        }
    }

}
