package com.tongfu.controller;

import com.alibaba.fastjson.JSONObject;
import com.tongfu.Util.MapUtil;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/knowHowArticle")
public class KnowHowArticleController {

    @Autowired
    private     AdService adService;

    @Autowired
    private KnowhowArticleService knowhowArticleService;

    @Value("${adknowhowhead}")
    Long adknowhowhead;

    @Value("${path-url}")
    private String pathUrl;


    //KnowHowView
    @RequestMapping(value = "/view")
    public String gotoaboutus(Model model,Long id){
        Map<String,Object> admap=new HashMap<>();
        admap.put("adPosition",adknowhowhead);//KnowHow头部广告
        List<Ad> listAd= adService.listAd(admap);
        model.addAttribute("adListHead",listAd);
        model.addAttribute("pathUrl",pathUrl);
        KnowhowArticle knowhowArticle = knowhowArticleService.selectByPrimaryKey(id);
        model.addAttribute("knowhowArticle",knowhowArticle);
        return "/knowHowArticle/view";

    }

}
