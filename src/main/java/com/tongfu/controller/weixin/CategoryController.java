package com.tongfu.controller.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongfu.Util.ResultUtil;
import com.tongfu.entity.Ad;
import com.tongfu.entity.ProductCategory;
import com.tongfu.service.AdService;
import com.tongfu.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller("wexincategory")
@RequestMapping("/weixin/category")
public class CategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private AdService adService;

    @Value("${adpositionjhhead}")
    private String adpositionjhhead;

    @Value("${adpositionjhfoot}")
    private String adpositionjhfoot;

    /**
     * 子分类页接口
     * @param jsonObject
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public String list(@RequestBody JSONObject jsonObject){
        JSONObject returnJson = new JSONObject();
        Map query_map = (Map) JSON.parse(jsonObject.toJSONString());
        //类别
        List<ProductCategory> listcate= productCategoryService.getParentProductCategory(query_map);
        returnJson.put("categoryList",listcate);//子类
        //列表头部广告
        query_map.put("productCategoryId",query_map.get("parent"));
        query_map.put("status",2);
        query_map.put("paymentStatus",2);
        List<Ad> list= adService.listAd(query_map);
        ProductCategory productCategory = productCategoryService.selectByPrimaryKey(jsonObject.getLong("parent"));
        returnJson.put("adListHead",list);
        returnJson.put("name",productCategory.getName());
        return  ResultUtil.success(returnJson);
    }


    /**
     * 微信端底部导航产品
     * @param jsonObject
     * @return
     */
    @RequestMapping("/categoryList")
    @ResponseBody
    public String categoryList(@RequestBody JSONObject jsonObject){
        List<Map> mapList = new ArrayList<>();
        List<ProductCategory> productCategorys = productCategoryService.selectByParents(0L,1l,null);
        for (ProductCategory productCategory : productCategorys) {
            Map query_map = new HashMap();
            query_map.put("parent",productCategory.getId());
            List<ProductCategory> parentProductCategory = productCategoryService.getParentProductCategory(query_map);
            query_map.put("name",productCategory.getName());
            query_map.put("treeCatrgorys",parentProductCategory);
            mapList.add(query_map);
        }
        return  ResultUtil.success(mapList);
    }

    /**
     * 首页右侧分类
     * @param jsonObject
     * @return
     */
    @RequestMapping("/rightCategoryList")
    @ResponseBody
    public String rightCategoryList(@RequestBody JSONObject jsonObject){
        List<Map> mapList = new ArrayList<>();
        Map query_map = new HashMap();
        query_map.put("parent",1);
        List<ProductCategory> parentProductCategorys = productCategoryService.getParentProductCategory(query_map);
        Map map1 = new HashMap();
        map1.put("name","新闻资讯");
        map1.put("categorys",parentProductCategorys);
        query_map.put("parent",0);
        query_map.put("type",1);
        parentProductCategorys = productCategoryService.getParentProductCategory(query_map);
        Map map2 = new HashMap();
        map2.put("name","产品");
        map2.put("categorys",parentProductCategorys);
        query_map.put("parent",2);
        query_map.remove("type");
        parentProductCategorys = productCategoryService.getParentProductCategory(query_map);
        Map map3 = new HashMap();
        map3.put("name","增值服务");
        map3.put("categorys",parentProductCategorys);
        query_map.put("parent",3);
        parentProductCategorys = productCategoryService.getParentProductCategory(query_map);
        Map map4 = new HashMap();
        map4.put("name","know-how");
        map4.put("categorys",parentProductCategorys);
        mapList.add(map1);
        mapList.add(map2);
        mapList.add(map3);
        mapList.add(map4);
        return  ResultUtil.success(mapList);
    }





    /**
     * 微信首页分类
     * @param jsonObject
     * @return
     */
    @RequestMapping("/indexList")
    @ResponseBody
    public String indexList(@RequestBody JSONObject jsonObject){

        JSONObject returnJson = new JSONObject();
        Map<String,Object> map=new HashMap<>();
        List<ProductCategory> listcate3= productCategoryService.selectByParents(0L,1l,null);
        returnJson.put("categoryList",listcate3);
        return  ResultUtil.success(returnJson);
    }










}
