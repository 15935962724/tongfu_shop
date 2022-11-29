package com.tongfu.dao;

import com.tongfu.entity.Returns;

public interface ReturnsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Returns record);

    int insertSelective(Returns record);

    Returns selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Returns record);

    int updateByPrimaryKey(Returns record);
}