package com.tongfu.service;

import com.tongfu.entity.Returns;

public interface ReturnsService {
    int deleteByPrimaryKey(Long id);

    int insert(Returns record);

    int insertSelective(Returns record);

    Returns selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Returns record);

    int updateByPrimaryKey(Returns record);
}