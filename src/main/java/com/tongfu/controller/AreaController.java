package com.tongfu.controller;

import com.tongfu.entity.Area;
import com.tongfu.entity.ProductCategory;
import com.tongfu.service.AreaService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/area")
public class AreaController {

    @Autowired
    AreaService areaService;

    @RequestMapping("/queryList")
    @ResponseBody
    public Object queryList(Long parent){
        //类别
        List<Area> list= areaService.selectArea(parent);
        return list;
    }

    /**
     * 三级联动插件
     * @param parent
     * @return
     */
    @RequestMapping("/getAreas")
    @ResponseBody
    public Map<Long, String>  getAreas( @RequestParam(defaultValue = "0") Long parentId){
        //类别
        List<Area> areas= areaService.selectArea(parentId);
        Map<Long, String> options = new HashMap<Long, String>();
        for (Area area : areas) {
            options.put(area.getId(), area.getName());
        }
        return options;
    }





}
