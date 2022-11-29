package com.tongfu.service;

import com.tongfu.entity.MemberPointLog;

import java.util.List;
import java.util.Map;

public interface MemberPointLogService {

    int insertMemberPointLog(MemberPointLog memberPointLog);

    List<Map<String,Object>> selectPoint(Long member,Integer seltime,Integer selshouzhi);

}
