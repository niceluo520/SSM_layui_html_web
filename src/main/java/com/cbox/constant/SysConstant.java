package com.cbox.constant;

/**
 * @ClassName: SysConstant
 * @Function: 系统使用的常量，通常需要与配置文件中的数据保持一致。
 * 
 * @author cbox
 * @date 2020年3月10日 下午8:19:06
 * @version 1.0
 */
public class SysConstant {

    /** ehcache名称 */
    public static final String EHCACHE_NAME = "dataCache";// 与chcache.xml中的name保持一致

    /** 默认密码 */
    public static final String DEFAULT_PASSSWD = "123456";// 新建用户的默认密码

    /** 默认设置的时间信息 */
    public static final int COOKIE_EXPIRY_TIME = 3600 * 24 * 30;// cookie的过期时间，默认为1个月

    /** 管理员的标识 */
    public static final String SUPER_ADMIN = "1";




}
