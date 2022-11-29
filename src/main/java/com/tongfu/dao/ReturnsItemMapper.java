package com.tongfu.dao;

import com.tongfu.entity.ReturnsItem;

public interface ReturnsItemMapper {
    int insert(ReturnsItem record);

    int insertSelective(ReturnsItem record);
}