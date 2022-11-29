package com.tongfu.service;

import com.tongfu.entity.CommitteeApply;

public interface CommitteeApplyService {
    int deleteByPrimaryKey(Long id);

    int insert(CommitteeApply record);

    int insertSelective(CommitteeApply record);

    CommitteeApply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CommitteeApply record);

    int updateByPrimaryKey(CommitteeApply record);
}