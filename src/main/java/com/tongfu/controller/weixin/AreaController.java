package com.tongfu.controller.weixin;

import com.alibaba.fastjson.JSONObject;
import com.tongfu.Util.ResultUtil;
import com.tongfu.entity.Area;
import com.tongfu.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("weixinarea")
@RequestMapping("/weixin/area")
public class AreaController {

    @Autowired
    AreaService areaService;

    @RequestMapping("/queryList")
    @ResponseBody
    public String queryList(@RequestBody JSONObject jsonObject  ){
        //类别
        List<Area> list= areaService.selectArea(jsonObject.getLong("parent"));
        return ResultUtil.success(list);
    }

    /**
     * 三级联动插件
     * @param parentId
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
