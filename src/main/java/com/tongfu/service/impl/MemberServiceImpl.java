package com.tongfu.service.impl;

import com.tongfu.dao.MemberDao;
import com.tongfu.entity.Admin;
import com.tongfu.entity.Member;
import com.tongfu.service.MemberService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import java.util.HashMap;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    MemberDao memberDao;

    @Override
    public Member selectByUserName(String username) {
        Member member = memberDao.selectByUsername(username);
        return member;
    }

    @Override
    public Member selectByPrimaryKey(Long id) {

        return memberDao.selectByPrimaryKey(id);
    }

    //获得登录人信息
    @Override
    public Member getCurrent() {
        Member member=null;
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipal() != null) {
            member = (Member)subject.getPrincipal();
            return member;
        }else{
            Map query_map = new HashMap();
            query_map.put("username","13522751165");
            member = memberDao.getMember(query_map);
            return member;
        }

    }

    @Override
    public int insertMember(Member member) {
       int result= memberDao.insertSelective(member);
       return result;
    }

    @Override
    public int updateMember(Member member) {
        return memberDao.updateByPrimaryKeySelective(member);
    }

    @Override
    public Member selectByEmail(String email) {
        return memberDao.selectByEmail(email);
    }

    @Override
    public Member getMember(Map query_map) {
        return memberDao.getMember(query_map);
    }

    @Override
    public List<Member> getMemberList(Map query_map){
        return memberDao.getMemberList(query_map);
    }



}
