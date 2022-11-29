package com.tongfu.service;

import com.tongfu.entity.Media;

import java.util.List;
import java.util.Map;

public interface MediaService {

    int deleteByPrimaryKey(Long id);

    int insert(Media record);

    int insertSelective(Media record);

    Media selectByPrimaryKey(Long id);

    List<Map<String,Object>> selectByCompany(Long company);

    int updateByPrimaryKeySelective(Media record);

    int updateByPrimaryKeyWithBLOBs(Media record);

    int updateByPrimaryKey(Media record);


}