package com.tongfu.dao;

import com.tongfu.entity.Cases;
import com.tongfu.entity.CasesWithBLOBs;

public interface CasesDao {
    int deleteByPrimaryKey(Long id);

    int insert(CasesWithBLOBs record);

    int insertSelective(CasesWithBLOBs record);

    CasesWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CasesWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(CasesWithBLOBs record);

    int updateByPrimaryKey(Cases record);
}