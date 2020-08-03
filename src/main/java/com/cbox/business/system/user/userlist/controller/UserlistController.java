package com.cbox.business.system.user.userlist.controller;

import java.util.HashMap;
import java.util.List;
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
import com.cbox.base.processor.aspect.PlatOperateLog;
import com.cbox.base.processor.aspect.type.LogType;
import com.cbox.base.protocol.ResponseBodyVO;
import com.cbox.base.protocol.ServerRspUtil;
import com.cbox.base.util.StrUtil;
import com.cbox.business.common.page.bean.PageLayuiVO;
import com.cbox.business.system.user.userform.service.UserformService;
import com.cbox.business.system.user.userlist.service.UserlistService;
import com.cbox.constant.BusiConstant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: UserlistController
 * @Function: 用户管理-列表
 * 
 * @author cbox
 * @version 1.0
 */
@Controller
@RequestMapping("/system/userlist")
public class UserlistController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserlistService userlistService;
    @Autowired
    private UserformService userformService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String userlist(Model model, HttpServletRequest request) {
        Map<String, Object> param = copyParam(request);
        model.addAllAttributes(param);
        return "/business/system/user/userlist/userlist";
    }

    /************************* 列表[用户查询列表list0]相关方法 Begin ************************/

    /**
     * loadListList0:加载列表数据-用户查询列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "loadListList0", method = RequestMethod.POST)
    @ResponseBody
    @PlatOperateLog(value = "用户列表查询", logType = LogType.OPERATE)
    public Map<String, Object> loadListList0(HttpServletRequest request, PageLayuiVO pageBean) {

        if (pageBean == null) {
            return ServerRspUtil.errorToMap("分页参数为空");
        }
        // int a = 10 / 0; //测试ControllerAdvice，实际应用时注释掉。
        PageHelper.startPage(pageBean.getPage(), pageBean.getLimit());
        Map<String, Object> param = copyParam(request);
        List<Map<String, Object>> resp = userlistService.doLoadListList0(param);

        Map<String, Object> retData = new HashMap<String, Object>();
        retData.put(BusiConstant.PAGE_ITEMS, resp);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(resp);
        retData.put(BusiConstant.PAGE_TOTAL, pageInfo.getTotal());
        retData.put(BusiConstant.PAGE_INFO, pageBean);

        return ServerRspUtil.successToMap("查询成功", retData);
    }

    /**
     * toAddList0:去添加-用户查询列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "toAddList0", method = RequestMethod.GET)
    @ButtonPermission(authid = "userlist_toAdd", name = "新增")
    public String toAddList0(Model model, HttpServletRequest request) {
        model.addAllAttributes(copyParam(request));
        return "/business/system/user/userform/userform";
    }

    /**
     * toUpdateList0:去修改-用户查询列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "toUpdateList0", method = RequestMethod.GET)
    @ButtonPermission(authid = "userlist_toUpdate", name = "修改")
    public String toUpdateList0(Model model, HttpServletRequest request) {
        model.addAllAttributes(copyParam(request));
        return "/business/system/user/userform/userform";
    }

    /**
     * toViewList0:去查看-用户查询列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "toViewList0", method = RequestMethod.GET)
    public String toViewList0(Model model, HttpServletRequest request) {
        model.addAllAttributes(copyParam(request));
        return "/business/system/user/userform/userformView";
    }

    /**
     * toDeleteList0:删除-用户查询列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "toDeleteList0", method = RequestMethod.POST)
    @ResponseBody
    @ButtonPermission(authid = "userlist_toDelete", name = "删除")
    public Map<String, Object> toDeleteList0(HttpServletRequest request) {
        Map<String, Object> param = copyParam(request);
        if (StrUtil.isNull(param.get("rec_id"))) {
            return ServerRspUtil.errorToMap("关键参数丢失");
        }

        // 操作人(取当前用户的Id)
        String userId = getSysuser(request).getUser_id();
        param.put("rec_updateperson", userId);

        ResponseBodyVO<Object> resp = userformService.doDeleteOform(param);

        if (resp.success()) {
            resp.setRetMsg("删除成功");
        }
        return ServerRspUtil.genResponseMap(resp);
    }

    /**
     * doDelCheckList0:删除校验-用户查询列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "doDelCheckList0", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doDelCheckList0(HttpServletRequest request) {
        Map<String, Object> param = copyParam(request);
        if (StrUtil.isNull(param.get("rec_id"))) {
            return ServerRspUtil.errorToMap("关键参数丢失");
        }

        // 操作人(取当前用户的Id)
        String userId = getSysuser(request).getUser_id();
        param.put("rec_updateperson", userId);

        ResponseBodyVO<Object> resp = userlistService.doDelCheckList0(param);

        if (resp.success()) {
            resp.setRetMsg("校验成功");
        }
        return ServerRspUtil.genResponseMap(resp);
    }

    /************************* End 列表[用户查询列表list0] ******************************/

}
