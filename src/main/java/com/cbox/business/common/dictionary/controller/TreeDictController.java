package com.cbox.business.common.dictionary.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbox.base.common.controller.BaseController;
import com.cbox.base.util.StrUtil;
import com.cbox.business.common.dictionary.service.TreeDictService;

/**
 * @ClassName: TreeDictController
 * @Function: 获取数据字典数据-树类型数据
 * 
 * @author cbox
 * @version 1.0
 */
@Controller
@RequestMapping("/dict/tree")
public class TreeDictController extends BaseController {

    @Autowired
    private TreeDictService treeDictService;

    /** 籍贯树areaTree **/
    @ResponseBody
    @RequestMapping(value = "getTreeNodeAreaTree", method = RequestMethod.POST)
    public String getTreeNodeAreaTree(HttpServletRequest request) {
        Map<String, Object> param = copyParam(request);
        if (StrUtil.isNotNull(param.get("id"))) {
            return "";
        }
        return treeDictService.getTreeNodeAreaTree(param);
    }

    /** 菜单树 **/
    @ResponseBody
    @RequestMapping(value = "getTreeForResource", method = RequestMethod.POST)
    public String getTreeForResource(HttpServletRequest request) {
        Map<String, Object> param = copyParam(request);
        if (StrUtil.isNotNull(param.get("id"))) {
            return "";
        }
        return treeDictService.getTreeForResource(param);
    }

}
