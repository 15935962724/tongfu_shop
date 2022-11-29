package com.tongfu.service;

import com.tongfu.entity.Refunds;

public interface RefundsService {
    int deleteByPrimaryKey(Long id);

    int insert(Refunds record);

    int insertSelective(Refunds record);

    Refunds selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Refunds record);

    int updateByPrimaryKey(Refunds record);
}