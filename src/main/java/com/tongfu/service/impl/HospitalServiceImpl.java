package com.tongfu.service.impl;

import com.tongfu.dao.HospitalDao;
import com.tongfu.entity.DemonstrationStatistics;
import com.tongfu.entity.Hospital;
import com.tongfu.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    HospitalDao hospitalDao;


    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(Hospital record) {
        return 0;
    }

    @Override
    public int insertSelective(Hospital record) {
        return hospitalDao.insertSelective(record);
    }

    @Override
    public Hospital selectByPrimaryKey(Long id) {
        return hospitalDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Hospital record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Hospital record) {
        return 0;
    }

    @Override
    public List<Hospital> selectHospital() {
        return hospitalDao.selectHospital();
    }

    @Override
    public List<Hospital> getHospitals(Map query_map) {
        return hospitalDao.getHospitals(query_map);
    }
}
