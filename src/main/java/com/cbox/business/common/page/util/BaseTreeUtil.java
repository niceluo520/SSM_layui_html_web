package com.cbox.business.common.page.util;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cbox.base.util.StrUtil;
import com.cbox.business.common.page.bean.BaseTreeVO;

public class BaseTreeUtil {

    private static int size = 50;

    /**
     * 操作树：BaseTree数据转换
     * 
     * @param list tree列表
     * @return tree节点数据
     */
    public static JSONArray toJsonString(List<BaseTreeVO> list) {

        if (list != null) {
            boolean isDefaultOpen = list.size() < size ? true : false;
            JSONArray childs = new JSONArray();
            for (int i = 0; i < list.size(); i++) {
                BaseTreeVO baseTreeVO = list.get(i);
                JSONObject child = new JSONObject();
                child.put("id", baseTreeVO.getId());
                String pId = StrUtil.getNotNullStrValue(baseTreeVO.getPid(), baseTreeVO.getParent_id());
                child.put("pId", pId);
                String code = StrUtil.getNotNullStrValue(baseTreeVO.getCode(), baseTreeVO.getId());
                child.put("code", code);
                child.put("name", baseTreeVO.getName());
                child.put("isParent", baseTreeVO.isParent());
                child.put("title", baseTreeVO.getName());// 节点提示信息

                child.put("open", isDefaultOpen || baseTreeVO.isOpen());
                child.put("checked", baseTreeVO.isChecked());
                child.put("nocheck", baseTreeVO.isNocheck());
                child.put("noEditBtn", baseTreeVO.isNoEditBtn());
                child.put("noRemoveBtn", baseTreeVO.isNoRemoveBtn());

                // 自定义业务参数
                child.put("diy1", baseTreeVO.getDiy1());
                child.put("diy2", baseTreeVO.getDiy2());
                child.put("diy3", baseTreeVO.getDiy3());
                child.put("diy4", baseTreeVO.getDiy4());
                childs.add(child);
            }
            return childs;
        }
        return null;
    }

    /**
     * 操作树：BaseTree数据转换(全局指定树上按钮)
     * 
     * @param list tree列表
     * @return tree节点数据
     */
    /**
     * BaseTree数据转换(全局指定树上按钮)
     * 
     * @param list
     * @param hasCheck
     * @param hasEditBtn
     * @param hasRemoveBtn
     * @return
     */
    public static JSONArray toJsonString(List<BaseTreeVO> list, boolean hasCheck, boolean hasEditBtn, boolean hasRemoveBtn) {

        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                BaseTreeVO baseTreeVO = list.get(i);

                // 全局指定树上的按钮
                baseTreeVO.setNocheck(hasCheck);
                baseTreeVO.setNoEditBtn(hasEditBtn);
                baseTreeVO.setNoRemoveBtn(hasRemoveBtn);
            }
        }

        return toJsonString(list);
    }

    /**
     * toNodesForZTree:数据字典：转换成 ztree 的返回数据结构
     *
     * @date: 2019年10月22日 下午4:09:39
     * @author cbox
     * @param list
     * @return
     */
    public static String toNodesForZTree(List<BaseTreeVO> list) {
        if (list != null) {
            boolean isDefaultOpen = list.size() < size ? true : false;
            JSONArray childs = new JSONArray();
            for (int i = 0; i < list.size(); i++) {
                BaseTreeVO baseTreeVO = list.get(i);
                // System.out.println(baseTreeVO.isOpen());
                JSONObject child = new JSONObject();
                child.put("id", baseTreeVO.getId());
                String pId = StrUtil.getNotNullStrValue(baseTreeVO.getPid(), baseTreeVO.getParent_id());
                child.put("pId", pId);
                String code = StrUtil.getNotNullStrValue(baseTreeVO.getCode(), baseTreeVO.getId());
                child.put("code", code);
                child.put("name", baseTreeVO.getName());
                child.put("isParent", baseTreeVO.isParent());
                child.put("title", baseTreeVO.getName());// 节点提示信息

                child.put("open", isDefaultOpen || baseTreeVO.isOpen());
                child.put("checked", baseTreeVO.isChecked());
                child.put("nocheck", baseTreeVO.isNocheck());

                // is_allparams diy1,req_params diy2,rsp diy3
                child.put("is_allparams", baseTreeVO.getDiy1());
                child.put("req_params", baseTreeVO.getDiy2());
                child.put("rsp", baseTreeVO.getDiy3());
                childs.add(child);
            }
            return childs.toString();
        }
        return null;
    }

    /**
     * toNodesForDTree:数据字典：转换成 dtree 的返回数据结构
     *
     * @date: 2019年10月22日 下午4:09:39
     * @author cbox
     * @param list
     * @return
     */
    public static String toNodesForDTree(List<BaseTreeVO> list) {
        JSONObject json = new JSONObject();

        if (list != null) {
            json.put("code", "0");
            json.put("msg", "获取数据成功");

            boolean bFirstTop = true;

            boolean isDefaultOpen = list.size() < size ? true : false;
            JSONArray childs = new JSONArray();
            for (int i = 0; i < list.size(); i++) {
                BaseTreeVO baseTreeVO = list.get(i);
                // System.out.println(baseTreeVO.isOpen());
                JSONObject child = new JSONObject();
                child.put("id", baseTreeVO.getId());
                String pId = StrUtil.getNotNullStrValue(baseTreeVO.getPid(), baseTreeVO.getParent_id());
                pId = StrUtil.getNotNullStrValue(pId, "0");// 顶层节点默认为0
                child.put("parentId", pId);
                child.put("title", baseTreeVO.getName());// 节点提示信息

                // child.put("code", code);

                if ("0".equals(pId) && bFirstTop) {// 第一个顶部节点，给自动展开
                    baseTreeVO.setOpen(true);
                    bFirstTop = false;
                }

                child.put("spread", isDefaultOpen || baseTreeVO.isOpen());// 节点展开状态

                child.put("checkArr", "0");// 支持复选

                // 自定义返回数据
                JSONObject childCustom = new JSONObject();
                String code = StrUtil.getNotNullStrValue(baseTreeVO.getCode(), baseTreeVO.getId());
                childCustom.put("code", code);// 传递的编码，可以是多个的组合
                String title = StrUtil.getNotNullStrValue(baseTreeVO.getTitle(), baseTreeVO.getName());
                childCustom.put("title", title);// 展示的长名称

                child.put("basicData", childCustom);

                childs.add(child);
            }

            json.put("data", childs);

        } else {
            json.put("code", "-1");
            json.put("msg", "获取数据失败");
        }

        return JSONObject.toJSONString(json);// 转成字符串返回

    }

}
