package com.tongfu.dao;

import com.tongfu.entity.MemberPointLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MemberPointLogDao {
    int deleteByPrimaryKey(Long id);

    int insert(MemberPointLog record);

    int insertSelective(MemberPointLog record);

    MemberPointLog selectByPrimaryKey(Long id);

    List<Map<String,Object>> selectPoint(@Param("member") Long member, @Param("seltime")Integer seltime, @Param("selshouzhi")Integer selshouzhi);

    int updateByPrimaryKeySelective(MemberPointLog record);

    int updateByPrimaryKey(MemberPointLog record);
}