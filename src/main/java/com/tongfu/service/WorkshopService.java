package com.tongfu.service;


import com.tongfu.entity.Workshop;

import java.util.List;
import java.util.Map;

public interface WorkshopService {


    List<Map<String,Object>> selectByType(Map<String,Object> map);

    Workshop selectById(Long id);



}
