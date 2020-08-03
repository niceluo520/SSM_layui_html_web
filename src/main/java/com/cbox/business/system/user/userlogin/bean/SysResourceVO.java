package com.cbox.business.system.user.userlogin.bean;

import java.io.Serializable;
import java.util.List;

public class SysResourceVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String rec_id;
    private String parent_id;
    private String parent_ids;
    private String type;
    private String url;
    private String previewurl;
    private String icon;
    private String sort;
    private String is_local;
    private String open_type;
    private String is_control;
    private String name;
    private String authid;// 资源唯一编码，用于权限控制

    private List<SysResourceVO> childMenus;// 子菜单。注意：存到缓存中时不填充值，只有在页面获取菜单时才填充值。

    private List<SysResourceVO> listHasBtnResources;// 页面的（当前用户）拥有权限的所有子资源（button资源）信息



    public List<SysResourceVO> getListHasBtnResources() {
        return listHasBtnResources;
    }

    public void setListHasBtnResources(List<SysResourceVO> listHasBtnResources) {
        this.listHasBtnResources = listHasBtnResources;
    }

    /**
     * @return the parent_ids
     */
    public String getParent_ids() {
        return parent_ids;
    }

    /**
     * @param parent_ids the parent_ids to set
     */
    public void setParent_ids(String parent_ids) {
        this.parent_ids = parent_ids;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public String getPreviewurl() {
        return previewurl;
    }

    public void setPreviewurl(String previewurl) {
        this.previewurl = previewurl;
    }

    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return the sort
     */
    public String getSort() {
        return sort;
    }

    /**
     * @param sort the sort to set
     */
    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * @return the is_local
     */
    public String getIs_local() {
        return is_local;
    }

    /**
     * @param is_local the is_local to set
     */
    public void setIs_local(String is_local) {
        this.is_local = is_local;
    }

    /**
     * @return the open_type
     */
    public String getOpen_type() {
        return open_type;
    }

    /**
     * @param open_type the open_type to set
     */
    public void setOpen_type(String open_type) {
        this.open_type = open_type;
    }

    /**
     * @return the is_control
     */
    public String getIs_control() {
        return is_control;
    }

    /**
     * @param is_control the is_control to set
     */
    public void setIs_control(String is_control) {
        this.is_control = is_control;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getAuthid() {
        return authid;
    }

    public void setAuthid(String authid) {
        this.authid = authid;
    }

    /**
     * @return the rec_id
     */
    public String getRec_id() {
        return rec_id;
    }

    /**
     * @param rec_id the rec_id to set
     */
    public void setRec_id(String rec_id) {
        this.rec_id = rec_id;
    }

    /**
     * @return the parent_id
     */
    public String getParent_id() {
        return parent_id;
    }

    /**
     * @param parent_id the parent_id to set
     */
    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public List<SysResourceVO> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<SysResourceVO> childMenus) {
        this.childMenus = childMenus;
    }

}