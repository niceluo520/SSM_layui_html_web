package com.cbox.business.system.user.userlogin.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SysUserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String loginToken;

    private String rec_id;
    private String user_id;
    private String user_name;
    private String mobile_phone;
    private String h_img;
    private String is_admin;// 是否是管理员,（0-否，1-是）
    private Integer sex;
    private String area_code;
    private String organization_id;
    private String salt;// 加密密码时使用的种子
    private String locked;// 是否锁定,（0-锁定，1-正常）
    private String user_level;
    private String login_status;
    private String email;
    private String password;

    private List<String> listRoleIds;// 用户拥有的所有角色，这里存角色ID

    private List<SysResourceVO> listUserResources;// 用户拥有的资源信息。这里只有菜单资源(module,menu)，每条资源中包含着对应的子资源（button)

    private Map<String, String> mapUserMenuUrl;// 用户有权限的菜单Url，key=url。用来做菜单权限判断
    private Map<String, String> mapUserBtnAuthid;// 用户有权限的按钮authid，key=authid。用来做按钮权限判断

    private String rec_status;// 记录的状态。（1-正常，2-删除）
    private String rec_person;// 创建人
    private Date rec_time;// 创建时间
    private String rec_updateperson;// 更新人
    private Date rec_updatetime;// 更新时间

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getRec_id() {
        return rec_id;
    }

    public void setRec_id(String rec_id) {
        this.rec_id = rec_id;
    }

    public Map<String, String> getMapUserMenuUrl() {
        return mapUserMenuUrl;
    }

    public void setMapUserMenuUrl(Map<String, String> mapUserMenuUrl) {
        this.mapUserMenuUrl = mapUserMenuUrl;
    }

    public Map<String, String> getMapUserBtnAuthid() {
        return mapUserBtnAuthid;
    }

    public void setMapUserBtnAuthid(Map<String, String> mapUserBtnAuthid) {
        this.mapUserBtnAuthid = mapUserBtnAuthid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public String getH_img() {
        return h_img;
    }

    public void setH_img(String h_img) {
        this.h_img = h_img;
    }

    public String getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(String is_admin) {
        this.is_admin = is_admin;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(String organization_id) {
        this.organization_id = organization_id;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getUser_level() {
        return user_level;
    }

    public void setUser_level(String user_level) {
        this.user_level = user_level;
    }

    public String getLogin_status() {
        return login_status;
    }

    public void setLogin_status(String login_status) {
        this.login_status = login_status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getListRoleIds() {
        return listRoleIds;
    }

    public void setListRoleIds(List<String> listRoleIds) {
        this.listRoleIds = listRoleIds;
    }

    public List<SysResourceVO> getListUserResources() {
        return listUserResources;
    }

    public void setListUserResources(List<SysResourceVO> listUserResources) {
        this.listUserResources = listUserResources;
    }

    public String getRec_status() {
        return rec_status;
    }

    public void setRec_status(String rec_status) {
        this.rec_status = rec_status;
    }

    public String getRec_person() {
        return rec_person;
    }

    public void setRec_person(String rec_person) {
        this.rec_person = rec_person;
    }

    public Date getRec_time() {
        return rec_time;
    }

    public void setRec_time(Date rec_time) {
        this.rec_time = rec_time;
    }

    public String getRec_updateperson() {
        return rec_updateperson;
    }

    public void setRec_updateperson(String rec_updateperson) {
        this.rec_updateperson = rec_updateperson;
    }

    public Date getRec_updatetime() {
        return rec_updatetime;
    }

    public void setRec_updatetime(Date rec_updatetime) {
        this.rec_updatetime = rec_updatetime;
    }

}