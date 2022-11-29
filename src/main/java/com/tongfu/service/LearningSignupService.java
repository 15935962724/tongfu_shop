package com.tongfu.service;

import com.tongfu.entity.LearningSignup;

public interface LearningSignupService {
    int deleteByPrimaryKey(Long id);

    int insert(LearningSignup record);

    int insertSelective(LearningSignup record);

    LearningSignup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LearningSignup record);

    int updateByPrimaryKey(LearningSignup record);
}