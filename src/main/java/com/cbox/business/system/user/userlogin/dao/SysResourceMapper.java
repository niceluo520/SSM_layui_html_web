package com.cbox.business.system.user.userlogin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cbox.business.system.user.userlogin.bean.SysResourceVO;
import com.cbox.business.system.user.userlogin.bean.SysRoleResourceVO;

/**
 * @ClassName: UserMapper
 * @Function:
 * 
 * @author cbwa0
 * @date 2018年12月25日 下午4:16:27
 * @version 1.0
 */
@Mapper
public interface SysResourceMapper {

    List<SysResourceVO> getMenuResources();// 菜单

    List<SysResourceVO> getBtnResources();// 按钮

    List<SysRoleResourceVO> getResourcesByRoles(@Param("roleIds") List<String> listRoleIds);// 按角色id查询所关联资源

}
