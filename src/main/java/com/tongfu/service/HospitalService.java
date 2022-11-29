package com.tongfu.service;


import com.tongfu.entity.Hospital;

import java.util.List;
import java.util.Map;

/**
 * 医院
 */
public interface HospitalService {

    int deleteByPrimaryKey(Long id);

    int insert(Hospital record);

    int insertSelective(Hospital record);

    Hospital selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Hospital record);

    int updateByPrimaryKey(Hospital record);

    List<Hospital> selectHospital();

    List<Hospital> getHospitals(Map query_map);



}
