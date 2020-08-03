package com.cbox.base.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;;

@Mapper
public interface ExecSqlMapper {

    List<Map<String, Object>> selectList(@Param("execSql") String execSql);

    Map<String, Object> selectMap(@Param("execSql") String execSql);

    Map<String, Object> selectCount(@Param("execSql") String execSql);

    int save(@Param("execSql") String execSql);

    int update(@Param("execSql") String execSql);

    int delete(@Param("execSql") String execSql);

}
