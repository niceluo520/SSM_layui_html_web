package com.cbox.business.system.user.userform.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbox.base.common.controller.BaseController;
import com.cbox.base.processor.aspect.ButtonPermission;
import com.cbox.base.protocol.ResponseBodyVO;
import com.cbox.base.protocol.ServerRspUtil;
import com.cbox.base.util.GlobalRecIdUtil;
import com.cbox.base.util.StrUtil;
import com.cbox.business.system.user.userform.service.UserformService;

/**
 * @ClassName: UserformController
 * @Function: 用户添加表单
 * 
 * @author cbox
 * @version 1.0
 */
@Controller
@RequestMapping("/system/userform")
public class UserformController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserformService userformService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String userform(Model model, HttpServletRequest request) {
        Map<String, Object> param = copyParam(request);
        model.addAllAttributes(param);
        return "/business/system/user/userform/userform";
    }

    @RequestMapping(value = "view", method = RequestMethod.GET)
    public String userformView(Model model, HttpServletRequest request) {
        model.addAllAttributes(copyParam(request));
        return "/business/system/user/userform/userformView";
    }

    /************************* 表单[新增用户表单oform]相关方法  Begin ************************/

    /**
     * loadFormOform:加载表单数据-新增用户表单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "loadFormOform", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> loadFormOform(HttpServletRequest request) {
        Map<String, Object> param = copyParam(request);

        ResponseBodyVO<Object> resp = userformService.doLoadFormOform(param);

        return ServerRspUtil.genResponseMap(resp);
    }

    /**
     * doAddOform:添加-新增用户表单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "doAddOform", method = RequestMethod.POST)
    @ResponseBody
    @ButtonPermission(authid = "userlist_toAdd", name = "新增")
    public Map<String, Object> doAddOform(HttpServletRequest request) {
        Map<String, Object> param = copyParam(request);

        // 创建人、更新人(取当前用户的Id)
        String userId = getSysuser(request).getUser_id();
        param.put("rec_id", GlobalRecIdUtil.nextRecId());
        param.put("rec_person", userId);
        param.put("rec_updateperson", userId);

        ResponseBodyVO<Object> resp = userformService.doAddOform(param);

        if (resp.success()) {
            resp.setRetMsg("保存成功！");
        }
        return ServerRspUtil.genResponseMap(resp);
    }

    /**
     * doEditOform:修改-新增用户表单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "doEditOform", method = RequestMethod.POST)
    @ResponseBody
    @ButtonPermission(authid = "userlist_toUpdate", name = "修改")
    public Map<String, Object> doEditOform(HttpServletRequest request) {
        Map<String, Object> param = copyParam(request);
        if (StrUtil.isNull(param.get("rec_id"))) {
            return ServerRspUtil.errorToMap("关键参数丢失");
        }

        // 操作人(取当前用户的Id)
        String userId = getSysuser(request).getUser_id();
        param.put("rec_updateperson", userId);

        ResponseBodyVO<Object> resp = userformService.doEditOform(param);

        if (resp.success()) {
            resp.setRetMsg("编辑操作表单成功");
        }
        return ServerRspUtil.genResponseMap(resp);
    }

    /************************* End 表单[新增用户表单oform] ******************************/

}
