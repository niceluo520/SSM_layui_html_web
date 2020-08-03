package com.cbox.constant;

/**
 * 业务使用到的公共常量
 * 
 * @author: cbox 2019年11月8日
 * @version: 1.0
 */
public class BusiConstant {

    // 无权限的页面
    public static final String UNPERMISSION_PAGE = "/login/unPermission";

    /** 用户状态 */
    public static final String USERSTATUS_NORMAL = "1";// 正常状态
    public static final String USERSTATUS_BLOCKED = "0";// 锁定状态

    /** 服务端返回信息定义 */
    public static final String RET_CODE = "retCode";// 返回编码
    public static final String RET_MSG = "retMsg";// 返回消息
    public static final String RET_DATA = "retData";// 返回数据，对象格式
    public static final String RET_LIST = "list";// 返回列表
    public static final String RET_PAGE = "page";// 返回分页参数

    // RET_CODE 服务端返回编码定义
    public static final String SUCCESS = "0";// 成功
    public static final String ERROR_SYSTEM = "-1";// 系统异常
    public static final String ERROR_BUSINESS = "-2";// 其他业务失败
    public static final String ERROR_INVALIDPARAM = "-3";// 非法的参数
    public static final String ERROR_MISSINGPARAM = "-4";//  缺少必选参数
    public static final String ERROR_TOKEN = "-5";// 登录已过期
    public static final String ERROR_PERMISSION = "-5";// 权限不足
    public static final String ERROR_ADDFAIL = "-10";// 新增失败
    public static final String ERROR_EDITFAIL = "-11";// 修改失败
    public static final String ERROR_DELFAIL = "-12";// 删除失败
    
    // RET_DATA 返回数据是列表时的数据格式Map<String,Object>的key
    public static final String PAGE_ITEMS = "items"; // 列表数据
    public static final String PAGE_TOTAL = "total"; // 总记录数
    public static final String PAGE_INFO = "pageInfo";  // 分页数据（当前页、每页数量）体现在这里
    // public static final String PAGE_SIZE = "pagesize";// 默认页面记录数变量
    // public static final String PAGE_NUM = "pagenum";// 当前页数
   
}
