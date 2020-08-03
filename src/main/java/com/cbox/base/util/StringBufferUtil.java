package com.cbox.base.util;

/**
 * StringBuffer工具类
 * @author cbox
 *
 */

/**
 * ClassName: StringBufferUtil Function: StringBuffer工具类，处理了灵活缩进的问题
 * 
 * @author cbox
 * @date 2017年5月23日 下午2:54:42
 * @version 1.0
 */
public class StringBufferUtil {

    public static final String LEFT_MARGIN = "	";
    StringBuffer sbBuffer;

    public StringBufferUtil() {
        sbBuffer = new StringBuffer();
    }

    public void appendOnly(String str) {
        sbBuffer.append(str); // 不加左偏移
    }

    public void append(String str) {
        append(1, str); // 自动增加左偏移
    }

    public void append(int count, String str) {
        for (int i = 0; i < count; i++) {
            sbBuffer.append(LEFT_MARGIN);
        }
        sbBuffer.append(str); // 自动增加左偏移
    }

    public void appendEnter(String str) {
        appendEnter(1, str); // 自动增加左偏移
    }

    public void appendEnter(int count, String str) {
        for (int i = 0; i < count; i++) {
            sbBuffer.append(LEFT_MARGIN);
        }
        sbBuffer.append(str + "\n"); // 自动增加左偏移
    }

    public void clear() {
        sbBuffer = new StringBuffer();
    }

    public void appendEnter() {
        sbBuffer.append("\n");
    }

    public StringBuffer getBuffer() {
        return sbBuffer;
    }

    public String toString() {
        return sbBuffer.toString();
    }
}
