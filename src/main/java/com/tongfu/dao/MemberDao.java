package com.tongfu.dao;

import com.tongfu.entity.Member;

import java.util.List;
import java.util.Map;

public interface MemberDao {
    int deleteByPrimaryKey(Long id);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

    Member selectByUsername(String username);

    Member selectByEmail(String email);

    Member getMember(Map query_map);

    List<Member> getMemberList(Map query_map);

}