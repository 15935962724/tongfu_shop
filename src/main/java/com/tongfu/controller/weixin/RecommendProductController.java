package com.tongfu.controller.weixin;

import com.alibaba.fastjson.JSONObject;
import com.tongfu.Util.ResultUtil;
import com.tongfu.service.AdService;
import com.tongfu.service.RecommendProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("weixinRecommendProduct")
@RequestMapping("/weixin/recommendProduct")
public class RecommendProductController {

    @Autowired
    private AdService adService;

    @Autowired
    private RecommendProductService recommendProductService;



    /**
     * 微信首页脚部广告
     * @param jsonObject
     * @return
     */
    @RequestMapping("/view")
    @ResponseBody
    public String view(@RequestBody JSONObject jsonObject  ){
        JSONObject returnJson = new JSONObject();
        Map<String,Object> map=new HashMap<>();
        map.put("category",jsonObject.getLong("categoryId"));
        List<Map<String,Object>> rightAds = recommendProductService.getAll(map);
        returnJson.put("rightAds",rightAds);
        return ResultUtil.success(returnJson);
    }

}





