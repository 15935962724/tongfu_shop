package com.tongfu.service.impl;

import com.tongfu.dao.MemberPointLogDao;
import com.tongfu.entity.MemberPointLog;
import com.tongfu.service.MemberPointLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MemberPointLogServiceImpl implements MemberPointLogService {

    @Autowired
    MemberPointLogDao memberPointLogDao;


    @Override
    public int insertMemberPointLog(MemberPointLog memberPointLog) {
        return memberPointLogDao.insertSelective(memberPointLog);
    }

    @Override
    public List<Map<String, Object>> selectPoint(Long member, Integer seltime, Integer selshouzhi) {
        return memberPointLogDao.selectPoint(member,seltime,selshouzhi);
    }


}
