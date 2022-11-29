package com.tongfu.controller.weixin;

import com.alibaba.fastjson.JSONObject;
import com.tongfu.Util.ResultUtil;
import com.tongfu.entity.Aboutus;
import com.tongfu.service.AboutusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller("weixinAboutus")
@RequestMapping("/weixin/aboutus")
public class AboutusController {


    @Autowired
    private AboutusService aboutusService;


    /**
     * 微信端首页关于我们
     * @param jsonObject
     * @return
     */
    @RequestMapping("/indexList")
    @ResponseBody
    public String indexList(@RequestBody JSONObject jsonObject){
        JSONObject returnJson = new JSONObject();
        List<Aboutus> aboutusList = aboutusService.getAll(null);
        returnJson.put("aboutusList",aboutusList);
        return ResultUtil.success(returnJson);
    }


    /**
     * 关于我们详情
     * @param jsonObject
     * @return
     */
    @RequestMapping("/view")
    @ResponseBody
    public String view(@RequestBody JSONObject jsonObject){
        JSONObject returnJson = new JSONObject();
        Aboutus aboutus = aboutusService.selectByPrimaryKey(jsonObject.getLong("id"));
        returnJson.put("aboutus",aboutus);
        return ResultUtil.success(returnJson);
    }



}
