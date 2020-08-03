package com.cbox.business.common.attach.factory;

import java.io.File;
import java.util.Calendar;

public class AttachNameFactory {

    /**
     * getAttachPath:获取附件的存储路径。暂时分年、月存储。
     *
     * @date: 2019年10月3日 下午7:44:05
     * @author cbox
     * @return
     */
    public static String getAttachPath() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1; // 月份是从0—11，需要加1
        String path = cal.get(Calendar.YEAR) + File.separator + month + File.separator;

        return path;
    }

}
