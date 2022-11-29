package com.tongfu.controller.weixin;

import com.alibaba.fastjson.JSONObject;
import com.tongfu.Util.ResultUtil;
import com.tongfu.entity.Area;
import com.tongfu.entity.Department;
import com.tongfu.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("weixindepartment")
@RequestMapping("/weixin/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;


    @RequestMapping("/queryList")
    @ResponseBody
    public String queryList(@RequestBody JSONObject jsonObject  ){
        //类别
        Map query_map = new HashMap();
        Long parentId = jsonObject.getLong("parent");
        if(parentId!=0){
            query_map.put("parentId",parentId);
        }
        List<Department> list= departmentService.selectDepartment(query_map);
        return ResultUtil.success(list);
    }



    /**
     * 三级联动插件
     * @return
     */
    @RequestMapping("/getDepartments")
    @ResponseBody
    public Map<Long, String>  getDepartments(Long parentId){
        //类别
        Map query_map = new HashMap();
        query_map.put("parentId",parentId);
        List<Department> departments = departmentService.selectDepartment(query_map);
        Map<Long, String> options = new HashMap<Long, String>();
        for (Department department : departments) {
            options.put(department.getId(), department.getName());
        }
        return options;
    }





}
