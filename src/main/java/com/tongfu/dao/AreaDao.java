package com.tongfu.dao;

import com.tongfu.entity.Area;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AreaDao {
    int deleteByPrimaryKey(Long id);

    int insert(Area record);

    int insertSelective(Area record);

    Area selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Area record);

    int updateByPrimaryKeyWithBLOBs(Area record);

    int updateByPrimaryKey(Area record);

    List<Area> selectByParent(@Param("parent")Long parent);

    Area selectByName(@Param("name")String name);

    List<Map<Long,String>> getAreas(Long parent);

    List<Area> getLikeName(String name);



}