package com.tongfu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.Ad;
import com.tongfu.entity.Article;
import com.tongfu.entity.ProductCategory;
import com.tongfu.service.AdService;
import com.tongfu.service.ArticleService;
import com.tongfu.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/article")
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

    @RequestMapping("/list")
    public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "4")Integer pageSize, Long category,String keywords,@RequestParam(defaultValue = "0")Long index){
        Map<String,Object> admap=new HashMap<>();
        admap.put("status",2);
        admap.put("paymentStatus",2);
        if (index==0){
            admap.put("adPosition",8);//??????????????????????????????????????????
        }
        if (index==1){
            admap.put("adPosition",13);//??????????????????????????????????????????
        }
        if (index==2){
            admap.put("adPosition",14);//??????????????????????????????????????????
        }
        if (index==3){
            admap.put("adPosition",15);//??????????????????????????????????????????
        }
        if (index==4){
            admap.put("adPosition",16);//??????????????????????????????????????????
        }
        if (index==5){
            admap.put("adPosition",17);//??????????????????????????????????????????
        }

        List<Ad> listAd= adService.listAd(admap);
        model.addAttribute("adListHead",listAd);
        model.addAttribute("path",pathurl);
        Map<String,Object> map = new HashMap<>();
        map.put("category",category);
        map.put("keywords",keywords);
        map.put("title",keywords);
        Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
        List<Map> list = articleService.selectArticle(map);
        PageInfo<Map> pageInfo = new PageInfo<Map>();
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        pageInfo.setList(list);
        model.addAttribute("page",pageInfo);
        model.addAttribute("pathurl",pathurl);
        model.addAttribute("category",category);
        //????????????????????????
        List<ProductCategory> listcategory = productCategoryService.selectByParents(industryid,2l,1l);
        model.addAttribute("categorylist",listcategory);
        ProductCategory productCategory = productCategoryService.selectByPrimaryKey(category);
        Long parent = productCategory.getParent();
        ProductCategory productCategory1 = productCategoryService.selectByPrimaryKey(parent);
        model.addAttribute("parentName",productCategory1.getName());//????????????
        model.addAttribute("index",index);
        return "/article/list";
    }

    @RequestMapping("/view")
    public String view(Model model, Long category,Long articleid){
        Article article = articleService.selectByPrimaryKey(articleid);
        List<Map<String,Object>> listCaregory = productCategoryService.selectParent(category);
        String parent = "";
        if(listCaregory.size()>0){
            Map<String,Object> pc=listCaregory.get(0);
            parent = (String)pc.get("name");
        }
        ProductCategory productCategory =  productCategoryService.selectByPrimaryKey(category);
        model.addAttribute("parent",parent);//??????????????????
        model.addAttribute("productCategory",productCategory);//????????????
        model.addAttribute("article",article);//??????
        return "/article/view";
    }


    @RequestMapping("/valuelist")
    public String valuelist(Model model, Long category,Long id){
        Map<String,Object> admap=new HashMap<>();
        admap.put("adPosition",adaddedvaluehead);//??????????????????
        List<Ad> listAd= adService.listAd(admap);
        model.addAttribute("adListHead",listAd);
        model.addAttribute("path",pathurl);
        Map<String,Object> map = new HashMap<>();
        map.put("category",category);
        ProductCategory productC = productCategoryService.selectByPrimaryKey(category);
        model.addAttribute("categoryName",productC.getName());
        List<Map> list = articleService.getArticle(map);
        model.addAttribute("list",list);
        model.addAttribute("pathurl",pathurl);
        model.addAttribute("category",category);
        ProductCategory parentC = productCategoryService.selectByPrimaryKey(productC.getParent());
        model.addAttribute("parentName",parentC.getName());//????????????
        //????????????????????????????????????
        List<ProductCategory> listcategory = productCategoryService.selectByParents(addedvalueid,2l,1l);
        model.addAttribute("categorylist",listcategory);
        //?????????????????????????????????????????????????????????
        //?????????????????????
        Map<String,Object> paraMap=new HashMap<>();
        //????????????map
        Map<String,Object> articleMap = new HashMap<>();
        for (int i = 0; i < listcategory.size(); i++) {
            ProductCategory productCategory = listcategory.get(i);
            Long cid = productCategory.getId();

            paraMap.put("category",cid);

            ProductCategory pc = productCategoryService.selectByPrimaryKey(cid);
            List<Map> listarticle = articleService.getSearchArticle(paraMap);
            if(cid.longValue() == category.longValue()){
                //??????????????????????????????
                if(listarticle.size()>0){
                    if(id!=null&&id!=0l){

                        for (Map<String, Object> stringObjectMap : listarticle) {
                            if(id.equals(stringObjectMap.get("id"))){
                                model.addAttribute("title",stringObjectMap.get("title"));
                                model.addAttribute("content",stringObjectMap.get("content"));
                            }
                        }

                    }else{
                        model.addAttribute("title",listarticle.get(0).get("title"));
                        model.addAttribute("content",listarticle.get(0).get("content"));
                    }

                }

            }
            articleMap.put(pc.getName(),listarticle);
        }

        model.addAttribute("articleMap",articleMap);


        return "/article/valuelist";
    }

    @RequestMapping("/knowhowlist")
    public String knowhowlist(Model model, Long category,Long id){
        Map<String,Object> admap=new HashMap<>();
        admap.put("adPosition",adknowhowhead);//knowhow??????
        List<Ad> listAd= adService.listAd(admap);
        model.addAttribute("adListHead",listAd);
        model.addAttribute("path",pathurl);
        Map<String,Object> map = new HashMap<>();
        map.put("category",category);
        ProductCategory productC = productCategoryService.selectByPrimaryKey(category);
        model.addAttribute("categoryName",productC.getName());
        List<Map> list = articleService.getSearchArticle(map);
        model.addAttribute("list",list);
        model.addAttribute("pathurl",pathurl);
        model.addAttribute("category",category);
        ProductCategory parentC = productCategoryService.selectByPrimaryKey(productC.getParent());
        model.addAttribute("parentName",parentC.getName());
        //??????knowhow??????????????????
        List<ProductCategory> listcategory = productCategoryService.selectByParents(knowHowid,2l,1l);
        model.addAttribute("categorylist",listcategory);
        //??????knowhow?????????????????????knowhow??????
        //?????????????????????
        Map<String,Object> paraMap=new HashMap<>();
        //????????????map
        Map<String,Object> articleMap = new HashMap<>();
        for (int i = 0; i < listcategory.size(); i++) {
            ProductCategory productCategory = listcategory.get(i);
            Long cid = productCategory.getId();

            paraMap.put("category",cid);

            ProductCategory pc = productCategoryService.selectByPrimaryKey(cid);
            List<Map> listarticle = articleService.getSearchArticle(paraMap);
            if(cid.longValue() == category.longValue()){
                //??????????????????????????????
                if(listarticle.size()>0){
                    if(id!=null&&id!=0l){

                        for (Map<String, Object> stringObjectMap : listarticle) {
                            if(id.equals(stringObjectMap.get("id"))){
                                model.addAttribute("title",stringObjectMap.get("title"));
                                model.addAttribute("content",stringObjectMap.get("content"));
                            }
                        }

                    }else{
                        model.addAttribute("title",listarticle.get(0).get("title"));
                        model.addAttribute("content",listarticle.get(0).get("content"));
                    }

                }

            }
            articleMap.put(pc.getName(),listarticle);
        }

        model.addAttribute("articleMap",articleMap);


        return "/article/knowhowlist";
    }


    /**
     * ????????????????????????
     * @param jsonObject
     * @return
     */
    @RequestMapping("/searchArticleTitle")
    @ResponseBody
    public String searchArticleTitle(@RequestBody JSONObject jsonObject){
        Map<String,Object> query_map = (Map<String, Object>) JSON.parse(jsonObject.toJSONString());
//        if (jsonObject.getLong("category")==6){
//
//        }
//        if (jsonObject.getLong("category")==7){
//
//        }
        List<Map> list = articleService.getSearchArticle(query_map);
        return JSONObject.toJSONString(list);
    }


    /**
     * ???????????????
     * @param model
     * @param category
     * @return
     */
    @RequestMapping("/searchArticle")
    public String searchArticle(Model model,@RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "8")Integer pageSize, Long category, String keywords ){
        Map<String,Object> admap=new HashMap<>();
        admap.put("adPosition",adindustryidhead);//????????????????????????
        List<Ad> listAd= adService.listAd(admap);
        model.addAttribute("adListHead",listAd);
        model.addAttribute("path",pathurl);
        Map<String,Object> map = new HashMap<>();
        map.put("keywords",keywords);
        map.put("category",category);
        Page<Map> page  = PageHelper.startPage(pageNum,pageSize);
        List<Map> list = articleService.getSearchArticle(map);
        PageInfo<Map> pageInfo = new PageInfo<Map>();
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        pageInfo.setList(list);
        model.addAttribute("page",pageInfo);
        model.addAttribute("list",list);
        map.put("parent",1);
        map.put("type",2);
        List<ProductCategory> parentProductCategorys = productCategoryService.getParentProductCategory(map);
        model.addAttribute("categorylist",parentProductCategorys);
        return "/article/searchArticle";
    }







}
