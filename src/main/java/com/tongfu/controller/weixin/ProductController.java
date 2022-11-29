package com.tongfu.controller.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.Util.ResultUtil;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("wexinproduct")
@RequestMapping("/weixin/product")
public class ProductController {


    @Autowired
    private ProductService productService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ProductPurchaseService productPurchaseService;

    @Autowired
    private ProductImgService productImgService;//产品图片

    @Autowired
    private ProductSpecificationsService productSpecificationsService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private CompanyService companyService;//供应商公司

    @Autowired
    private  HospitalService hospitalService;//医院

    @Autowired
    private DepartmentService departmentService;//科室

    @Autowired
    private AdService adService;//广告

    @Autowired
    private ConsultationService consultationService;//咨询

    @Autowired
    private MemberService memberService;//用户

    @Autowired
    private ProductMealService productMealService;// 套餐

    @Autowired
    private AreaService areaService;//地区

    @Autowired
    private RecommendProductService recommendProductService;

    @Value("${adpositionjhhead}")
    private String adpositionjhhead;

    @Value("${adpositionRight}")
    private String adpositionRight;

    @Value("${adpositionjhfoot}")
    private String adpositionjhfoot;

    @Value("${path-url}")
    private  String pathurl;

    /**
     * 产品列表页
     * @param jsonObject
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public String list(@RequestBody JSONObject jsonObject){
        JSONObject returnJson = new JSONObject();
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
        Map<String,Object> query_map = (Map<String, Object>) JSON.parse(jsonObject.toJSONString());
        List<Map<String,Object>> listProduct =productService.selectByCategory(query_map);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>();
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        pageInfo.setList(listProduct);
        returnJson.put("page",pageInfo);
        //商品列表页广告
        //列表头部广告
        Map<String,Object> mapad=new HashMap<>();
        mapad.put("productCategoryId",jsonObject.getLong("category"));
        mapad.put("status",2);
        mapad.put("paymentStatus",2);
        List<Ad> list= adService.listAd(mapad);
        returnJson.put("adListHead",list);
        return  ResultUtil.success(returnJson);
    }


    /**
     * 搜索
     * @param jsonObject
     * @return
     */

    @RequestMapping("/serchList")
    @ResponseBody
    public String serchList(@RequestBody JSONObject jsonObject){
        Map<String,Object> map = new HashMap<>();
        map.put("keywords",jsonObject.getString("keywords"));
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> listProduct=productService.selectByKeywords(map);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>();
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        pageInfo.setList(listProduct);
        JSONObject returnJson = new JSONObject();
        returnJson.put("page",pageInfo);
        return ResultUtil.success(returnJson);
    }






    /**
     * 供应商公司下的产品
     * @param jsonObject
     * @return
     */
    @RequestMapping("/companyProducts")
    @ResponseBody
    public String companyProducts(@RequestBody JSONObject jsonObject){
        JSONObject returnJson = new JSONObject();
        jsonObject.put("status",2);
        Map<String,Object> query_map = (Map<String, Object>) JSON.parse(jsonObject.toJSONString());
        List<Map> listProduct =productService.getCompanyProducts(query_map);
        returnJson.put("listProduct",listProduct);
        return  ResultUtil.success(returnJson);
    }


    /**
     * 供应商的产品分类下的产品
     * @param jsonObject
     * @return
     */
    @RequestMapping("/companyCategoryProducts")
    @ResponseBody
    public String companyCategoryProducts(@RequestBody JSONObject jsonObject){
        JSONObject returnJson = new JSONObject();
        jsonObject.put("status",2);
        Map<String,Object> query_map = (Map<String, Object>) JSON.parse(jsonObject.toJSONString());
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object> > listProduct =productService.selectByCompanyCategory(query_map);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>();
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        pageInfo.setList(listProduct);
        returnJson.put("page",pageInfo);
        return  ResultUtil.success(returnJson);
    }




    /**
     * 商品 详情
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/view")
    @ResponseBody
    public String selectByProductId(@RequestBody JSONObject jsonObject){
        JSONObject returnJson = new JSONObject();
        Member member = memberService.getCurrent();
        Map<String ,Object> query_map = new HashMap<>();
        Long id =  jsonObject.getLong("id");

        //根据id查询产品
        Product product = productService.selectProductById(id);
        returnJson.put("product",product);
        returnJson.put("isaddedvalue",product.getIsAddService());
        //查询购买方式
        query_map.put("type",product.getIsAddService());

        List<Purchase> purchaseList = purchaseService.selectFangshi(query_map);
        for (Purchase purchase : purchaseList) {
            query_map.put("productId",id);
            query_map.put("purchaseId",purchase.getId());
            List<Map> productPuchases = productPurchaseService.getProductPuchases(query_map);
            purchase.setProduct_puchase(productPuchases);
        }
        returnJson.put("purchases", purchaseList);//所有购买方式
        query_map.remove("type");
        query_map.put("productId",id);


        List<ProductImg> listImg = productImgService.selectByProductId(id);
        returnJson.put("listImg",listImg);
        //获取软件概览等八大项
        List<ProductSpecifications> listSpe =  productSpecificationsService.selectByProductId(id);
        returnJson.put("listSpe",listSpe);

        List<Map<String,Object>> product_meal_list = new ArrayList<>();
        query_map.put("status",2);
        List<ProductMeal> productMealList = productMealService.getAll(query_map);
        for (ProductMeal productMeal:productMealList){
            Map<String,Object> productMealMap = new HashMap<>();
            productMealMap.put("productMealId",productMeal.getId());
            productMealMap.put("prouctMealPrice",productMeal.getPrice());//套餐价

            ProductPurchase productPurchase = productPurchaseService.selectById(productMeal.getProductPurchaseId());
            Product product1 = productService.selectProductById(productMeal.getProductId());
            productMealMap.put("productName",product1.getName());
            productMealMap.put("productImg",product1.getImage());
            productMealMap.put("productPrice",productPurchase.getPrice());
            productMealMap.put("productId",productMeal.getProductPurchaseId());

            Product product2 = productService.selectProductById(productMeal.getProductPackageId());
            productMealMap.put("productPackageName",product2.getName());
            productMealMap.put("productPackageImg",product2.getImage());
            productMealMap.put("productPackagePrice",product2.getPrice());
            productMealMap.put("productPackageId",productMeal.getProductPackageId());
            BigDecimal originalPrice = productPurchase.getPrice().add(product2.getPrice());
            productMealMap.put("originalPrice",originalPrice);//原价
            productMealMap.put("discountPrice",originalPrice.subtract(productMeal.getPrice()));//优惠价

            product_meal_list.add(productMealMap);

        }

        returnJson.put("productMealList",product_meal_list);
        returnJson.put("member",member);
        return ResultUtil.success(returnJson);
    }



    /**
     * 增值服务产品详情页
     * @return
     */
    @RequestMapping("/addedvalue")
    @ResponseBody
    public String selectAddedvalue(@RequestBody JSONObject jsonObject){
        JSONObject returnJson = new JSONObject();
        Long id = jsonObject.getLong("id");
        Product product = productService.selectProductById(id);
        returnJson.put("product",product);
        //获取厂家图片
        long companyId = product.getCompanyId();
        Company company = companyService.selectCompanyById(companyId);
        returnJson.put("company",company);
        //查询购买方式
        Map query_map= new HashMap();
        query_map.put("productId",id);
        List<Purchase> purchaseList = purchaseService.selectFangshi(query_map);
        for (Purchase purchase : purchaseList) {
            query_map.put("purchaseId",purchase.getId());
            List<Map> productPuchases = productPurchaseService.getProductPuchases(query_map);
            purchase.setProduct_puchase(productPuchases);
        }
        returnJson.put("purchases", purchaseList);//所有购买方式
        //获取详情页商品图片（小图）
        returnJson.put("pathUrl",pathurl);//路径
        List<ProductImg> listImg = productImgService.selectByProductId(id);
        returnJson.put("listImg",listImg);
        //获取软件概览等八大项
        List<ProductSpecifications> listSpe =  productSpecificationsService.selectByProductId(id);
        returnJson.put("listSpe",listSpe);
        //大家都在看
        List<Product> topList = productService.selectTopByHits(5);
        returnJson.put("toplist",topList);
        return ResultUtil.success(returnJson);
    }


    /**
     * 培训包详情页
     * @return
     */
    @RequestMapping("/trainPackagesView")
    @ResponseBody
    public String trainPackagesView(@RequestBody JSONObject jsonObject){
        JSONObject returnJson = new JSONObject();
        Long id = jsonObject.getLong("id");
        Product product = productService.selectProductById(id);
        returnJson.put("product",product);
        //获取厂家图片
        long companyId = product.getCompanyId();
        Company company = companyService.selectCompanyById(companyId);
        returnJson.put("company",company);


        //查询购买方式
        Map query_map= new HashMap();
        query_map.put("productId",id);
        List<Purchase> purchaseList = purchaseService.selectFangshi(query_map);
        for (Purchase purchase : purchaseList) {
            query_map.put("purchaseId",purchase.getId());
            List<Map> productPuchases = productPurchaseService.getProductPuchases(query_map);
            purchase.setProduct_puchase(productPuchases);
        }
        returnJson.put("purchases", purchaseList);//所有购买方式

        List<ProductImg> listImg = productImgService.selectByProductId(id);
        returnJson.put("listImg",listImg);

        //获取软件概览等八大项
        List<ProductSpecifications> listSpe =  productSpecificationsService.selectByProductId(id);
        returnJson.put("listSpe",listSpe);
        //大家都在看
        List<Product> topList = productService.selectTopByHits(5);
        returnJson.put("toplist",topList);
        return ResultUtil.success(returnJson);
    }




    //商品点击量
    @RequestMapping(value = "/updateProductHits",method = RequestMethod.POST)
    @ResponseBody
    public String updateProductHits(@RequestBody JSONObject jsonObject){
        Product product = productService.selectProductById(jsonObject.getLong("id"));
        product.setHits(product.getHits()+1);
        productService.updateProductHits(product,jsonObject.getString("ip"));
        return ResultUtil.success();

    }


}
