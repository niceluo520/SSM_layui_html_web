package com.cbox.business.common.route.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/** 
 * @ClassName: ErrorController 
 * @Function:  
 * 
 * @author cbox 
 * @date 2018年12月30日 下午8:56:11 
 * @version 1.0  
 */
@Controller
@RequestMapping("/error")
public class HttpErrorController {

    @RequestMapping("")
    public String handleError() {
        return "common/error";
    }

    @RequestMapping(value = "404")
    public String handle404() {
        return "common/404";
    }

}
