package com.tongfu.service.impl;

import com.tongfu.dao.JournalismDao;
import com.tongfu.dao.MediaDao;
import com.tongfu.entity.Journalism;
import com.tongfu.entity.Media;
import com.tongfu.service.JournalismService;
import com.tongfu.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    MediaDao mediaDao;


    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(Media record) {
        return 0;
    }

    @Override
    public int insertSelective(Media record) {
        return 0;
    }

    @Override
    public Media selectByPrimaryKey(Long id) {
        return mediaDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> selectByCompany(Long company) {
        return mediaDao.selectByCompany(company);
    }

    @Override
    public int updateByPrimaryKeySelective(Media record) {
        return mediaDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(Media record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Media record) {
        return 0;
    }
}
