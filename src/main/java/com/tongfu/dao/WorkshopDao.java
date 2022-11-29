package com.tongfu.dao;

import com.tongfu.entity.Workshop;
import com.tongfu.entity.WorkshopWithBLOBs;

import java.util.List;
import java.util.Map;

public interface WorkshopDao {
    int deleteByPrimaryKey(Long id);

    int insert(WorkshopWithBLOBs record);

    int insertSelective(WorkshopWithBLOBs record);

    WorkshopWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WorkshopWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(WorkshopWithBLOBs record);

    int updateByPrimaryKey(Workshop record);

    List<Map<String,Object>> selectByType(Map<String,Object> map);
}