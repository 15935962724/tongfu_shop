package com.tongfu.dao;

import com.tongfu.entity.Refunds;

public interface RefundsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Refunds record);

    int insertSelective(Refunds record);

    Refunds selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Refunds record);

    int updateByPrimaryKey(Refunds record);
}