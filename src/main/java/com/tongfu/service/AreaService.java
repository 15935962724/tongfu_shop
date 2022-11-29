package com.tongfu.service;

import com.tongfu.entity.Area;

import java.util.List;
import java.util.Map;

public interface AreaService {


    List<Area> selectArea(Long parent);

    //根据主键查询
    Area selectById(Long id);

    //根据名称查询
    Area selectByName(String name);

    List<Map<Long,String>> getAreas(Long parent);
    //通过id查询最顶层
    Area getParentArea(Long areaId);

    List<Area> getLikeName(String name);


}
