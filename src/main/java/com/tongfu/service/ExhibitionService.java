package com.tongfu.service;

import com.tongfu.entity.Exhibition;

import java.util.List;
import java.util.Map;

public interface ExhibitionService {
    int deleteByPrimaryKey(Long id);

    int insert(Exhibition record);

    int insertSelective(Exhibition record);

    Exhibition selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Exhibition record);

    int updateByPrimaryKeyWithBLOBs(Exhibition record);

    int updateByPrimaryKey(Exhibition record);

    List<Exhibition> getAll(Map<String, Object> query_map);
}