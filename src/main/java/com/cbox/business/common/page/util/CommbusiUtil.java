package com.cbox.business.common.page.util;

import java.text.SimpleDateFormat;

import com.cbox.base.util.StrUtil;

/**
 * @ClassName: CommbusiUtil
 * @Function: 公共业务处理工具类
 * 
 * @author cbox
 * @date 2019年11月12日 上午11:20:43
 * @version 1.0
 */
public class CommbusiUtil {

    /**
     * parseToSpecifiedFormat: 把标准格式的日期字符串，转化为指定格式的日期字符串
     *
     * @date: 2019年11月12日 上午11:24:13
     * @author cbox
     * @param standardDateStr
     * @param format
     * @return
     */
    public static String parseToSpecifiedFormat(Object standardDate, String format) {

        String strFormat = "";
        if (standardDate != null && standardDate instanceof java.sql.Timestamp) {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            strFormat = formatter.format((java.sql.Timestamp) standardDate);
        } else {
            strFormat = StrUtil.getNotNullStrValue(standardDate);
        }

        return strFormat;
    }

    /** 处理数字类型 **/
    public static String dealNum(Object content) {
        String str = StrUtil.getNotNullStrValue(content);
        if (StrUtil.isNull(str)) { // 库表字段如果是数字类型,为空时需要转成null，不然mysql会报错
            str = "null";
        }
        return str;
    }

    /** 处理日期类型 **/
    public static String dealDate(Object content) {
        String str = StrUtil.getNotNullStrValue(content);
        if (StrUtil.isNull(str)) { // 库表字段如果是日期类型，为空时需要转成null，不然mysql会报错
            str = "null";
        }
        return str;
    }

}
