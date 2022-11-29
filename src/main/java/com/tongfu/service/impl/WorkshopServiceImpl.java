package com.tongfu.service.impl;


import com.tongfu.dao.WorkshopDao;
import com.tongfu.entity.Workshop;
import com.tongfu.service.WorkshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class WorkshopServiceImpl implements WorkshopService {

    @Autowired
    WorkshopDao workshopDao;


    @Override
    public List<Map<String,Object>> selectByType(Map<String, Object> map) {
        return workshopDao.selectByType(map);
    }

    @Override
    public Workshop selectById(Long id) {
        return workshopDao.selectByPrimaryKey(id);
    }
}
