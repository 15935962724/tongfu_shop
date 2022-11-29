package com.tongfu.service.impl;

import com.tongfu.dao.AreaDao;
import com.tongfu.dao.CommitteeApplyDao;
import com.tongfu.entity.Area;
import com.tongfu.entity.CommitteeApply;
import com.tongfu.service.AreaService;
import com.tongfu.service.CommitteeApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommitteeApplyServiceImpl implements CommitteeApplyService {

    @Autowired
    private CommitteeApplyDao committeeApplyDao;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(CommitteeApply record) {
        return 0;
    }

    @Override
    public int insertSelective(CommitteeApply record) {
        return committeeApplyDao.insertSelective(record);
    }

    @Override
    public CommitteeApply selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(CommitteeApply record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(CommitteeApply record) {
        return 0;
    }
}
