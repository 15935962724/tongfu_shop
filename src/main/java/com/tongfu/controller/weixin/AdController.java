package com.tongfu.controller.weixin;

import com.alibaba.fastjson.JSONObject;
import com.tongfu.Util.ResultUtil;
import com.tongfu.entity.Ad;
import com.tongfu.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("weixinad")
@RequestMapping("/weixin/ad")
public class AdController {

    @Autowired
    private AdService adService;

    @Value("${adpositiontop}")
    private Long adpositiontop;


    @Value("${adpositionfoot}")
    private Long adpositionfoot;

    /**
     * 微信首页头部广告
     * @param jsonObject
     * @return
     */
    @RequestMapping("/indexList")
    @ResponseBody
    public String indexList(@RequestBody JSONObject jsonObject  ){
        JSONObject returnJson = new JSONObject();
        Map<String,Object> map=new HashMap<>();
            //首页头部广告
            map.put("adPosition",adpositiontop);//首页头部
            List<Ad> headList= adService.listAd(map);
            returnJson.put("headList",headList);
            return ResultUtil.success(returnJson);
        }


    /**
     * 微信首页脚部广告
     * @param jsonObject
     * @return
     */
    @RequestMapping("/footList")
    @ResponseBody
    public String footList(@RequestBody JSONObject jsonObject  ){
        JSONObject returnJson = new JSONObject();
        Map<String,Object> map=new HashMap<>();
        //首页脚部广告
        map.put("adPosition",adpositionfoot);
        List<Ad> footList= adService.listAd(map);
        returnJson.put("footList",footList);
        return ResultUtil.success(returnJson);
    }

}





