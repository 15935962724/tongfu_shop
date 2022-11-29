package com.tongfu.dao;

import com.tongfu.entity.MeetingApply;

public interface MeetingApplyDao {
    int deleteByPrimaryKey(Long id);

    int insert(MeetingApply record);

    int insertSelective(MeetingApply record);

    MeetingApply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MeetingApply record);

    int updateByPrimaryKey(MeetingApply record);



}