package com.cbox.business.system.user.userlogin.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cbox.business.system.user.userlogin.bean.SysUserVO;

/**
 * @ClassName: UserMapper
 * @Function:
 * 
 * @author cbwa0
 * @date 2018年12月25日 下午4:16:27
 * @version 1.0
 */
@Mapper
public interface SysUserMapper {

    SysUserVO querySysUser(Map<String, Object> params);// 查询用户信息

}
