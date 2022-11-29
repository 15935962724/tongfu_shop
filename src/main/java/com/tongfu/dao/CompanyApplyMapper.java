package com.tongfu.dao;

import com.tongfu.entity.CompanyApply;

public interface CompanyApplyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CompanyApply record);

    int insertSelective(CompanyApply record);

    CompanyApply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CompanyApply record);

    int updateByPrimaryKey(CompanyApply record);
}