package com.cbox.business.common.dictionary.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbox.base.common.controller.BaseController;
import com.cbox.base.protocol.ServerRspUtil;
import com.cbox.business.common.dictionary.service.SelectDictService;

/**
 * @ClassName: SelectDictController
 * @Function: 获取数据字典数据-选项类型数据
 * 
 * @author cbox
 * @version 1.0
 */
@Controller
@RequestMapping("/dict/select")
public class SelectDictController extends BaseController {

    @Autowired
    private SelectDictService selectDictService;

    /** 用户角色选择userRole **/
    @ResponseBody
    @RequestMapping(value = "getOptionUserRole", method = RequestMethod.POST)
    public Map<String, Object> getOptionUserRole(HttpServletRequest request) {
        Map<String, Object> param = copyParam(request);
        List<Map<String, Object>> list = selectDictService.getOptionUserRole(param);
        if (null != list) {
            return ServerRspUtil.successToMap("获取用户角色选择成功", list);
        }
        return ServerRspUtil.errorToMap("获取用户角色选择失败");
    }

    /** 用户学历userEdu **/
    @ResponseBody
    @RequestMapping(value = "getOptionUserEdu", method = RequestMethod.POST)
    public Map<String, Object> getOptionUserEdu(HttpServletRequest request) {
        Map<String, Object> param = copyParam(request);
        List<Map<String, Object>> list = selectDictService.getOptionUserEdu(param);
        if (null != list) {
            return ServerRspUtil.successToMap("获取用户学历成功", list);
        }
        return ServerRspUtil.errorToMap("获取用户学历失败");
    }

}
