package com.tongfu.service;

import com.tongfu.entity.ReturnsItem;

public interface ReturnsItemService {
    int insert(ReturnsItem record);

    int insertSelective(ReturnsItem record);
}