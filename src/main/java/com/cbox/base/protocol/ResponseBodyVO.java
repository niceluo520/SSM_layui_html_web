package com.cbox.base.protocol;

import com.cbox.constant.BusiConstant;

/**
 * 
 * @ClassName: ResponseBodyVO
 * @Function: 服务端给客户端的返回数据Body格式。controller与service间也使用这个格式交互，省去数据转换给客户端。
 * 
 * @author cbox
 * @date 2020年2月27日 上午11:14:00
 * @version 1.0 @param <T>
 */
public class ResponseBodyVO<T> {

    /** 对象属性 */
    String retCode;// 返回编码
    String retMsg;// 返回消息
    T retData;// 返回数据

    /** 扩展方法 */
    public boolean success() {
        boolean isSuccess = false;
        if (BusiConstant.SUCCESS.equals(retCode)) {
            isSuccess = true;
        }
        return isSuccess;
    }

    public T getBody() {
        return retData;
    }

    /** setter,getter */
    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public T getRetData() {
        return retData;
    }

    public void setRetData(T retData) {
        this.retData = retData;
    }
}
