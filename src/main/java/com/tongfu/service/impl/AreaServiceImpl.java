package com.tongfu.service.impl;

import com.tongfu.dao.AreaDao;
import com.tongfu.entity.Area;
import com.tongfu.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Override
    public List<Area> selectArea(Long parent) {
        List<Area> list = areaDao.selectByParent(parent);
        return list;
    }

    @Override
    public Area selectById(Long id) {
        return areaDao.selectByPrimaryKey(id);
    }

    @Override
    public Area selectByName(String name) {
        return areaDao.selectByName(name);
    }

    @Override
    public List<Map<Long, String>> getAreas(Long parent) {
        return areaDao.getAreas(parent);
    }

    @Override
    public Area getParentArea(Long areaId) {
        Area area = areaDao.selectByPrimaryKey(areaId);
        if (area.getParent()!=null){
            return getParentArea(area.getParent());
        }
        return area;
    }

    @Override
    public List<Area> getLikeName(String name) {
        return areaDao.getLikeName(name);
    }

}
