package com.tongfu.dao;

import com.tongfu.entity.Consultation;

public interface ConsultationDao {
    int deleteByPrimaryKey(Long id);

    int insert(Consultation record);

    int insertSelective(Consultation record);

    Consultation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Consultation record);

    int updateByPrimaryKeyWithBLOBs(Consultation record);

    int updateByPrimaryKey(Consultation record);
}