package com.cbox.business.common.dictionary.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SelectDictMapper {

    /** 全部班级选择all_class **/
    public List<Map<String, Object>> getOptionAll_class(Map<String, Object> param);

    /** 院系班级选择t_depart_t_classid **/
    public List<Map<String, Object>> getOptionT_depart_t_classid(Map<String, Object> param);

    /** 院系选择下拉列表t_depart_name **/
    public List<Map<String, Object>> getOptionT_depart_name(Map<String, Object> param);

    /** 学校名称schoolname **/
    public List<Map<String, Object>> getOptionSchoolname(Map<String, Object> param);

    /** 学校关联院系名称department2 **/
    public List<Map<String, Object>> getOptionDepartment2(Map<String, Object> param);

    /** 科目下拉列表droiplist_course **/
    public List<Map<String, Object>> getOptionDroiplist_course(Map<String, Object> param);

    /** 用户角色选择userRole **/
    public List<Map<String, Object>> getOptionUserRole(Map<String, Object> param);

    /** 用户学历userEdu **/
    public List<Map<String, Object>> getOptionUserEdu(Map<String, Object> param);

}
