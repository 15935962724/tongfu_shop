package com.tongfu.dao;

import com.tongfu.entity.Message;

import java.util.List;
import java.util.Map;

public interface MessageDao {
    int deleteByPrimaryKey(Long id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKeyWithBLOBs(Message record);

    int updateByPrimaryKey(Message record);


}