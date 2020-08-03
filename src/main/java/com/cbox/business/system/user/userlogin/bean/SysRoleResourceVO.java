package com.cbox.business.system.user.userlogin.bean;

import java.io.Serializable;

/**
 *
 * @author cbox 2017年1月11日
 * @version: 1.0
 */
public class SysRoleResourceVO  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String rec_id;
    private String role_id; 
    private String resource_id;
    
    private String haspermission_ids;// 页面有权限的btn资源合，逗号分隔。（选择资源表中当前resource_id的类型为button的子资源，都有权限设为*）
    private String nopermission_ids;// 页面没有权限的btn资源，逗号分隔。（选择资源表中当前resource_id的类型为button的子资源，都无权限设为all，不设默认*）

	public String getRec_id() {
		return rec_id;
	}
	public void setRec_id(String rec_id) {
		this.rec_id = rec_id;
	}
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getResource_id() {
		return resource_id;
	}
	public void setResource_id(String resource_id) {
		this.resource_id = resource_id;
	}

    public String getHaspermission_ids() {
        return haspermission_ids;
    }

    public void setHaspermission_ids(String haspermission_ids) {
        this.haspermission_ids = haspermission_ids;
    }

    public String getNopermission_ids() {
        return nopermission_ids;
    }

    public void setNopermission_ids(String nopermission_ids) {
        this.nopermission_ids = nopermission_ids;
    }

}

