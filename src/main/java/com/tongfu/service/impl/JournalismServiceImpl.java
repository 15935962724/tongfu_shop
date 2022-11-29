package com.tongfu.service.impl;

import com.tongfu.dao.JournalismDao;
import com.tongfu.entity.Journalism;
import com.tongfu.service.JournalismService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JournalismServiceImpl implements JournalismService {

    @Autowired
    private JournalismDao journalismDao;


    @Override
    public List<Map<String,Object>> selectByCompanyKeyword(Map<String,Object> map) {
        return journalismDao.selectByCompanyKeyword(map);
    }

    @Override
    public Journalism selectByPrimarykey(Long id) {
        return journalismDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Map> getJournalisms(Map query_map) {
        return journalismDao.getJournalisms(query_map);
    }

    @Override
    public List<Map> getJournalismsLearningMediaExhibition(Map query_map) {
        return journalismDao.getJournalismsLearningMediaExhibition(query_map);
    }

    @Override
    public Integer getTableNameCount(Map query_map) {
        return journalismDao.getTableNameCount(query_map);
    }

    @Override
    public Map getArticleView(Map query_map) {
        return journalismDao.getArticleView(query_map);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(Journalism record) {
        return 0;
    }

    @Override
    public int insertSelective(Journalism record) {
        return 0;
    }

    @Override
    public Journalism selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Journalism record) {
        return journalismDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(Journalism record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Journalism record) {
        return 0;
    }

}
