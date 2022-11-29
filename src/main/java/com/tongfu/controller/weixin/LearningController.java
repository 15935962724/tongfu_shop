package com.tongfu.controller.weixin;

import com.alibaba.fastjson.JSONObject;
import com.tongfu.Util.ResultUtil;
import com.tongfu.entity.Learning;
import com.tongfu.service.LearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学术活动
 */
@Controller("weixinlearning")
@RequestMapping("/weixin/learning")
public class LearningController {

    @Autowired
    private LearningService learningService;//

    /**
     * 学术活动详情页
     * @param jsonObject
     * @return
     */
    @RequestMapping("/view")
    @ResponseBody
    public String view(@RequestBody JSONObject jsonObject) {
        JSONObject returnJson = new JSONObject();
        Learning learning =  learningService.selectByPrimaryKey(jsonObject.getLong("id"));
        returnJson.put("learning",learning);
        return ResultUtil.success(returnJson);

    }


    /**
     * 微信端首页学术活动
     * @param jsonObject
     * @return
     */
    @RequestMapping("/indexList")
    @ResponseBody
    public String indexList(@RequestBody JSONObject jsonObject) {
        JSONObject returnJson = new JSONObject();
        Map<String,Object> map=new HashMap<>();
        map.put("isTop",2);
        map.put("isPayment",2);
        map.put("status",2);
        map.put("isExpire",true);//未过期
        List<Learning> learnings = learningService.getAll(map);
        returnJson.put("learnings",learnings);
        return ResultUtil.success(returnJson);

    }













}
