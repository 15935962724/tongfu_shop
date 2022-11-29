package com.tongfu.service.impl;

import com.tongfu.dao.MeetingApplyDao;
import com.tongfu.entity.MeetingApply;
import com.tongfu.service.MeetingApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeetingApplyServiceImpl implements MeetingApplyService {

    @Autowired
    private MeetingApplyDao meetingApplyDao;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(MeetingApply record) {
        return 0;
    }

    @Override
    public int insertSelective(MeetingApply record) {
        return meetingApplyDao.insertSelective(record);
    }

    @Override
    public MeetingApply selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(MeetingApply record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(MeetingApply record) {
        return 0;
    }
}
