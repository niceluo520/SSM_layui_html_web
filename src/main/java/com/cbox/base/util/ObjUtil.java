package com.cbox.base.util;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ObjUtil
 * @Function: 对象的工具集
 * 
 * @author cbox
 * @date 2020年3月16日 上午8:28:02
 * @version 1.0
 */
public class ObjUtil {

    public static boolean isNull(List list) {
        if (list == null || list.isEmpty()) {
            // 用isEmpty，数据量大，效率高
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotNull(List list) {
        return !isNull(list);
    }

    public static boolean isNull(Map map) {
        if (map == null || map.isEmpty()) {
            // 用isEmpty，数据量大，效率高
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotNull(Map map) {
        return !isNull(map);
    }

    public static <T> Map<String, T> listToMap(List<T> list, String key) {
        Map<String, T> destMap = new HashMap<String, T>();

        try {
            if (isNotNull(list)) {
                for (T object : list) {
                    PropertyDescriptor pd = new PropertyDescriptor(key, object.getClass());
                    Object keyValue = pd.getReadMethod().invoke(object);
                    destMap.put(StrUtil.getStr(keyValue), object);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return destMap;
    }

    public static <T> List<T> mapToListByValue(Map<String, T> map) {
        List<T> list;

        if (isNotNull(map)) {
            list = new ArrayList<T>(map.values());
        } else {
            list = new ArrayList<T>();
        }

        return list;
    }

    public static <T> List<String> mapToListByKey(Map<String, T> map) {
        List<String> list;

        if (isNotNull(map)) {
            list = new ArrayList<String>(map.keySet());
        } else {
            list = new ArrayList<String>();
        }

        return list;
    }

}
