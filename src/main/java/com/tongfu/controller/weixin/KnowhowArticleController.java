package com.tongfu.controller.weixin;

import com.alibaba.fastjson.JSONObject;
import com.tongfu.Util.ResultUtil;
import com.tongfu.entity.KnowhowArticle;
import com.tongfu.service.KnowhowArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller("weixiKnowhowArticle")
@RequestMapping("/weixin/knowhowArticle")
public class KnowhowArticleController {


    @Autowired
    private KnowhowArticleService knowhowArticleService;

    /**
     * 微信端首页Know-how
     * @param jsonObject
     * @return
     */
    @RequestMapping("/indexList")
    @ResponseBody
    public String indexList(@RequestBody JSONObject jsonObject){
        JSONObject returnJson = new JSONObject();
        List<KnowhowArticle> knowhowArticleList = knowhowArticleService.getAll(null);
        returnJson.put("knowhowArticleList",knowhowArticleList);
        return ResultUtil.success(returnJson);
    }





}
