package com.tongfu.service.impl;


import com.tongfu.dao.ConsultationDao;
import com.tongfu.entity.Consultation;
import com.tongfu.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ConsultationServiceImpl implements ConsultationService {

    @Autowired
    ConsultationDao consultationDao;

    @Override
    public int addConsultation(Consultation consultation) {
        return consultationDao.insertSelective(consultation);
    }
}
