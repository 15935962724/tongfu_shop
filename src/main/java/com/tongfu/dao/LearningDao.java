package com.tongfu.dao;

import com.tongfu.entity.Learning;

import java.util.List;
import java.util.Map;

public interface LearningDao {
    int deleteByPrimaryKey(Long id);

    int insert(Learning record);

    int insertSelective(Learning record);

    Learning selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Learning record);

    int updateByPrimaryKeyWithBLOBs(Learning record);

    int updateByPrimaryKey(Learning record);

    List<Learning> getAll(Map<String,Object> query_map);

}