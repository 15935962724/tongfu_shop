package com.tongfu.dao;

import com.tongfu.entity.WbMember;

public interface WbMemberDao {
    int deleteByPrimaryKey(Long id);

    int insert(WbMember record);

    int insertSelective(WbMember record);

    WbMember selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WbMember record);

    int updateByPrimaryKeyWithBLOBs(WbMember record);

    int updateByPrimaryKey(WbMember record);
}