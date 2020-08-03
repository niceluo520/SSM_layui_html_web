package com.cbox.business.common.route.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cbox.base.common.controller.BaseController;
import com.cbox.constant.BusiConstant;

@Controller
@RequestMapping("/cbox")
public class RouteController extends BaseController {

    @RequestMapping(value = "commService", method = RequestMethod.POST)
    public String route(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> params) {
        // 错误信息
        String sbErrInfo = "";
        String sv_header = String.valueOf(params.get("sv_header"));
        String sv_method = "";
        String svParams = String.valueOf(params.get("sv_params"));
        // 校验接口入参
        if (StringUtils.isBlank(sv_header) || StringUtils.isBlank(svParams) || "null".equals(sv_header) || "null".equals(svParams)) {
            sbErrInfo = "Reqparam [sv_header] or [sv_params] is missed.\n";
            setRspbody(response, BusiConstant.ERROR_MISSINGPARAM, sbErrInfo, "");
            return null;
        }
        JSONObject jObject = JSON.parseObject(JSON.toJSONString(params.get("sv_header")));
        sv_method = (String) jObject.get("sv_method");
        if (StringUtils.isBlank(sv_method)) {
            sbErrInfo = "Reqparam [sv_method] is missed.\n";
            setRspbody(response, BusiConstant.ERROR_MISSINGPARAM, sbErrInfo, "");
            return null;
        }
        sv_method = sv_method.replaceAll("\\.", "/");
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(params.get("sv_params")));
        // System.out.println("sv_method===>" + (String) jObject.get("sv_method"));
        // System.out.println("params===>" + jsonObject.toString());
        request.setAttribute("params", jsonObject.toString());
        return "forward:/" + sv_method;
    }

    /**
     * @author cbox
     * @description 输出信息
     * @date 2019/8/2 14:57
     **/
    protected static void setRspbody(HttpServletResponse response, String retCode, String retMsg, Object retData) {
        JSONObject json = new JSONObject();
        json.put(BusiConstant.RET_CODE, retCode);
        json.put(BusiConstant.RET_MSG, retMsg);
        json.put(BusiConstant.RET_DATA, retData);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }
}
