package com.cbox.base.protocol;

import java.util.HashMap;
import java.util.Map;

import com.cbox.constant.BusiConstant;

/**
 * @ClassName: ServerRspUtil
 * @Function: ResponseBodyVO的操作类。
 * 
 * @author cbox
 * @date 2019年10月4日 上午10:08:12
 * @version 1.0
 */
public class ServerRspUtil {
 
	/***************** 指定信息生成ResponseBodyVO  **************/
    // 成功 
    public static <T> ResponseBodyVO<T> success(String retMsg, T retData) {
    	ResponseBodyVO<T> serverRsp = new ResponseBodyVO<T>();
        serverRsp.setRetCode(BusiConstant.SUCCESS);
        serverRsp.setRetMsg(retMsg);
        serverRsp.setRetData(retData);
        return serverRsp;
    }
    public static <T> ResponseBodyVO<T> success(T retData) {
        return success("操作成功", retData);
    }    
    
    // 失败 
    public static <T> ResponseBodyVO<T> error(String retCode, String retMsg) {
    	ResponseBodyVO<T> serverRsp = new ResponseBodyVO<T>();
        serverRsp.setRetCode(retCode);
        serverRsp.setRetMsg(retMsg);
        // serverRsp.setRetData("");
        return serverRsp;
    }
    public static <T> ResponseBodyVO<T> error(String retMsg) {
        return error(BusiConstant.ERROR_BUSINESS, retMsg); //默认为业务失败
    }
    public static <T> ResponseBodyVO<T> error(Integer iRetCode, String retMsg) {
        return error(iRetCode + "", retMsg);
    }

    
    /***************** 根据传入状态iStatus自动生成ResponseBodyVO  **************/
    public static <T> ResponseBodyVO<T> formRspBodyVO(int iStatus, T retData, String errMsg) {
        if (iStatus > 0) {
            return success(retData);
        } else {
            return error(errMsg);
        }
    }
    public static <T> ResponseBodyVO<T> formRspBodyVO(int iStatus, T retData) {
        return formRspBodyVO(iStatus,retData,"操作失败");
    }    
    public static <T> ResponseBodyVO<T> formRspBodyVO(int iStatus,String errMsg) {
        return formRspBodyVO(iStatus,null,errMsg);
    }


    /***************** 生成controller的返回数据类型Map<String,Object> **************/
    
    // 成功，返回Map 
    public static <T> Map<String, Object> genResponseMap(String retCode, String retMsg, T retData) {
        Map<String, Object> mapRsp = new HashMap<String, Object>();
        mapRsp.put(BusiConstant.RET_CODE, retCode);
        mapRsp.put(BusiConstant.RET_MSG, retMsg);
        mapRsp.put(BusiConstant.RET_DATA, retData);
        return mapRsp;
    }
    public static <T> Map<String, Object> genResponseMap(ResponseBodyVO<T> rspBodyVO) {
        return genResponseMap(rspBodyVO.getRetCode(),rspBodyVO.getRetMsg(),rspBodyVO.getRetData());
    }    
    public static Map<String, Object> successToMap(String retMsg, Object retData) {
        return genResponseMap(BusiConstant.SUCCESS, retMsg, retData);
    }
    public static Map<String, Object> successToMap(String retMsg) {
        return genResponseMap(BusiConstant.SUCCESS, retMsg, "");
    }
    

    // 失败，返回Map
    public static Map<String, Object> errorToMap(String retMsg) {
        return genResponseMap(BusiConstant.ERROR_SYSTEM, retMsg, "");
    }    

}
