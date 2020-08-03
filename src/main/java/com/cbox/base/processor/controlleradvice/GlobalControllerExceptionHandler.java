package com.cbox.base.processor.controlleradvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

/**
 * 定义全局异常处理
 * 
 * @Description:@RestControllerAdvice 是@controlleradvice 与@ResponseBody 的组合注解
 * @author cbox
 * @date 2017年9月12日 下午2:06:38
 *
 */
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler
    public Object handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        e.printStackTrace();
        logger.error(e.getMessage());
        String accept = request.getHeader("Accept");
        String requestType = request.getHeader("X-Requested-With");
        boolean ajax = (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) ? true : false;

        if (ajax || accept.contains("json")) {
            JSONObject json = new JSONObject();
            json.put("retCode", "-1");
            json.put("retMsg", "系统异常");
            json.put("retData", e.getMessage());
            return json;
        } else {
            ModelAndView mv = new ModelAndView("common/500");
            request.setAttribute("errorMsg", e.getMessage());
            return mv;
        }
    }
}
