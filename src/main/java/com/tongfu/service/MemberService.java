package com.tongfu.service;

import com.tongfu.entity.Member;

import java.util.List;
import java.util.Map;

public interface MemberService {
    Member selectByUserName(String username);

    Member selectByPrimaryKey(Long id);

    Member getCurrent();

    int insertMember(Member member);

    int updateMember(Member member);

    Member selectByEmail(String email);

    Member getMember(Map query_map);

    List<Member> getMemberList(Map query_map);


}
