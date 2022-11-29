package com.tongfu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongfu.Util.ResultUtil;
import com.tongfu.dao.CompanyApplyMapper;
import com.tongfu.entity.Admin;
import com.tongfu.entity.CompanyApply;
import com.tongfu.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/companyapply")
public class CompanyApplyController {

    @Autowired
   private CompanyApplyMapper companyApplyMapper;

    @RequestMapping("/save")
    @ResponseBody
    public String save(@RequestBody JSONObject jsonObject){
        CompanyApply companyApply = JSON.parseObject(jsonObject.toString(), CompanyApply.class);
        companyApply.setCreateDate(new Date());
        companyApply.setModifyDate(new Date());
        int insertSelective = companyApplyMapper.insertSelective(companyApply);
        if (insertSelective<1){
            return ResultUtil.error("操作失败!");
        }
        return ResultUtil.success();
    }





}
