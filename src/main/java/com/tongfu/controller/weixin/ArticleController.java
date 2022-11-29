package com.tongfu.controller.weixin;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.Util.ResultUtil;
import com.tongfu.entity.Ad;
import com.tongfu.entity.Article;
import com.tongfu.entity.ProductCategory;
import com.tongfu.service.AdService;
import com.tongfu.service.ArticleService;
import com.tongfu.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Controller("weixinarticle")
@RequestMapping("/weixin/article")
public class ArticleController {


    @Autowired
    private ArticleService articleService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Value("${path-url}")
    private String pathurl;

    @Value("${industryid}")
    private Long industryid;

    @Value("${addedvalueid}")
    private Long addedvalueid;

    @Value("${knowHowid}")
    private Long knowHowid;

    @Value("${adindustryidhead}")
    private Long adindustryidhead;

    @Value("${adaddedvaluehead}")
    private Long adaddedvaluehead;

    @Value("${adknowhowhead}")
    private Long adknowhowhead;

    @Autowired
    private AdService adService;


    @RequestMapping("/indexList")
    @ResponseBody
    public String indexList(@RequestBody JSONObject jsonObject){
        JSONObject returnJson = new JSONObject();
        List<Map> list = articleService.getIndexArticle(null);
        returnJson.put("list",list);
        return ResultUtil.success(returnJson);
    }



    /**
     * 行业资讯
     * @param jsonObject
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public String list(@RequestBody JSONObject jsonObject){
        JSONObject returnJson = new JSONObject();
        Map query_map =new HashMap<>();
        query_map.put("status",2);
        query_map.put("paymentStatus",2);
        query_map.put("adPosition",adindustryidhead);//行业资讯头部
        List<Ad> listAd= adService.listAd(query_map);
        returnJson.put("listAd",listAd);
        query_map.put("parent",1);//行业资讯顶级分类
        List<Map> list = articleService.getArticle(query_map);
        returnJson.put("list",list);
        return ResultUtil.success(returnJson);
    }


    /**
     * 详情页
     * @param jsonObject
     * @return
     */
    @RequestMapping("/view")
    @ResponseBody
    public String view(@RequestBody JSONObject jsonObject){
        JSONObject returnJson = new JSONObject();
        Article article = articleService.selectByPrimaryKey(jsonObject.getLong("id"));
        returnJson.put("article",article);
        return ResultUtil.success(returnJson);
    }


    /**
     * 行业资讯更多
     * @param jsonObject
     * @return
     */
    @RequestMapping("/categoryAiticleList")
    @ResponseBody
    public String categoryAiticleList(@RequestBody JSONObject jsonObject){
        JSONObject returnJson = new JSONObject();
        Map query_map =new HashMap<>();
        query_map.put("status",2);
        query_map.put("paymentStatus",2);
        query_map.put("adPosition",adindustryidhead);//行业资讯头部
        List<Ad> listAd= adService.listAd(query_map);
        returnJson.put("listAd",listAd);
        query_map.put("category",jsonObject.getLong("categoryId"));//行业资讯顶级分类
        Integer pageSize = jsonObject.getInteger("pageSize");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Page<Map> page  = PageHelper.startPage(pageNum,pageSize);
        List<Map> categoryList = articleService.selectArticle(query_map);
        PageInfo<Map> pageInfo = new PageInfo<Map>();
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        pageInfo.setList(categoryList);
        returnJson.put("pageInfo",pageInfo);
        ProductCategory productCategory = productCategoryService.selectByPrimaryKey(jsonObject.getLong("categoryId"));
        returnJson.put("name",productCategory.getName());
        return ResultUtil.success(returnJson);
    }


    /**
     * 点击增值服务
     * @param jsonobject
     * @return
     */
    @RequestMapping("/valuelist")
    @ResponseBody
    public String valuelist(@RequestBody JSONObject jsonobject){
        JSONObject returnJson = new JSONObject();
        Map<String,Object> admap=new HashMap<>();
        admap.put("adPosition",adaddedvaluehead);//增值服务头部
        List<Ad> listAd= adService.listAd(admap);
        returnJson.put("adListHead",listAd);
        Map<String,Object> map = new HashMap<>();
        map.put("category",jsonobject.getLong("categoryId"));
        List<Map> list = articleService.selectArticle(map);
        returnJson.put("list",list);
        return ResultUtil.success(returnJson);
    }


    /**
     * 点击knowhow
     * @param jsonobject
     * @return
     */
    @RequestMapping("/knowhowlist")
    @ResponseBody
    public String knowhowlist(@RequestBody JSONObject jsonobject){
        JSONObject returnJson = new JSONObject();
        Map<String,Object> admap=new HashMap<>();
        admap.put("adPosition",adknowhowhead);//know-how头部广告
        List<Ad> listAd= adService.listAd(admap);
        returnJson.put("adListHead",listAd);
        Map<String,Object> map = new HashMap<>();
        map.put("category",jsonobject.getLong("categoryId"));
        List<Map> list = articleService.selectArticle(map);
        returnJson.put("list",list);
        return ResultUtil.success(returnJson);
    }







}
