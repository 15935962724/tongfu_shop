package com.tongfu.service.impl;

import com.tongfu.dao.OrderLogDao;
import com.tongfu.entity.OrderLog;
import com.tongfu.service.OrderLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderLogServiceImpl implements OrderLogService {

    @Autowired
    OrderLogDao orderLogDao;

    @Override
    public int insertOrderLog(OrderLog orderLog) {
        return orderLogDao.insertSelective(orderLog);
    }
}
