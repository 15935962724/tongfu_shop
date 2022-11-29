package com.tongfu.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.Ad;
import com.tongfu.entity.ProductCategory;
import com.tongfu.service.AdService;
import com.tongfu.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;


@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    ProductCategoryService pcService;

    @Value("${path-url}")
    String pathurl;

    @Autowired
    AdService adService;

    @Value("${adpositionjhhead}")
    String adpositionjhhead;

    @Value("${adpositionjhfoot}")
    String adpositionjhfoot;

    @RequestMapping("/list")
    public String category(Model model, Long parent, Long type, Long position){


        //类别
        //List<Map<String,Object>> listcate2= pcService.selectByParents2(parent,type,position);
        List<ProductCategory> listcate= pcService.selectByParents(parent,type,position);
        model.addAttribute("categoryList",listcate);//子类
        List<ProductCategory> listcate3= pcService.selectByParents(0L,1l,null);
        model.addAttribute("categoryParentList",listcate3);//父类
        model.addAttribute("path",pathurl);//路径
        ProductCategory productCategory = pcService.selectByPrimaryKey(parent);
        model.addAttribute("productCategory",productCategory);
        //列表头部广告
        Map<String,Object> map=new HashMap<>();
        map.put("adPosition",adpositionjhhead);//列表头部
        map.put("productCategoryId",parent);
        map.put("status",2);
        map.put("paymentStatus",2);
        List<Ad> list= adService.listAd(map);
        model.addAttribute("adListHead",list);
        //列表脚部广告
        Map<String,Object> map2=new HashMap<>();
        map2.put("adPosition",adpositionjhfoot);//列表脚
        map2.put("productCategoryId",parent);
        List<Ad> list2= adService.listAd(map2);
        model.addAttribute("adListFoot",list2);
        model.addAttribute("parent",parent);
        return "/category/list";
    }

    @RequestMapping("/queryList")
    @ResponseBody
    public Object queryList(Model model,Long parent,Long type,Long position){
        //类别
        List<ProductCategory> productCategoryList= pcService.selectByParents(parent,type,position);
        Map<String,Object> map=new HashMap<>();
        map.put("productCategoryList",productCategoryList);
        map.put("path",pathurl);
        map.put("adPosition",6);//列表头部
        map.put("productCategoryId",parent);//父类id
        //广告
        List<Ad> adListHead= adService.listAd(map);
        map.put("adListHead",adListHead);//头部广告
        map.put("adPosition",9);//列表脚
        List<Ad> adListFoot= adService.listAd(map);
        map.put("adListFoot",adListFoot);//脚广告
        return map;

    }





}
