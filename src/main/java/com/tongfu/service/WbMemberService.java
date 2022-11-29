package com.tongfu.service;

import com.tongfu.entity.WbMember;

public interface WbMemberService {
    int deleteByPrimaryKey(Long id);

    int insert(WbMember record);

    int insertSelective(WbMember record);

    WbMember selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WbMember record);

    int updateByPrimaryKeyWithBLOBs(WbMember record);

    int updateByPrimaryKey(WbMember record);
}