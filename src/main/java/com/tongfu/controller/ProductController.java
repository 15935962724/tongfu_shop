package com.tongfu.controller;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/product")
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

    @Autowired
    private AgentProductService agentProductService;

    @Autowired
    private ProductSoftwarePackageService productSoftwarePackageService;

    @Value("${adpositionjhhead}")
    private String adpositionjhhead;

    @Value("${adpositionRight}")
    private String adpositionRight;

    @Value("${adpositionjhfoot}")
    private String adpositionjhfoot;

    @Value("${path-url}")
    private  String pathurl;

    /**
     * 产品列表页面
     * @param model
     * @param pageNum
     * @param pageSize
     * @param category
     * @param parent
     * @param keywords
     * @return
     */
    @RequestMapping("/list")
    public String selectByCategory(Model model, @RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "6")Integer pageSize,
                                   Long category,String keywords){
        int flag = 0;//0代表从产品列表进。1代表从分类列表页进agentProductService
        Map<String,Object> map = new HashMap<>();
        map.put("category",category);
        map.put("status",2);
        map.put("keywords",keywords);
        List<Map<String,Object>> listProduct = null;
        Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
        if(flag == 0){
            listProduct=productService.selectByCategory(map);
        }else{
            listProduct=productService.selectByKeywords(map);
        }

        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>();
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        pageInfo.setList(listProduct);
        model.addAttribute("path",pathurl);//路径
        model.addAttribute("page",pageInfo);

        if(flag==0){
            model.addAttribute("category",category);
        }

        List<ProductCategory> listcate2= productCategoryService.selectByParents(0L,1L,null);
        model.addAttribute("categoryParentList",listcate2);//父类
//        ProductCategory productCategory = productCategoryService.selectByPrimaryKey(parent);
//        model.addAttribute("productCategory",productCategory);
        if(keywords!=null&&keywords.length()>0){
            model.addAttribute("keywords",keywords);
        }
        //商品列表页广告
        //列表头部广告
        Map<String,Object> mapad=new HashMap<>();
        mapad.put("adPosition",adpositionjhhead);//列表头部
        mapad.put("productCategoryId",category);
        mapad.put("status",2);
        mapad.put("paymentStatus",2);

        List<Ad> list= adService.listAd(mapad);
        if (list.size()>5){
            list = list.subList(0,4);
        }
        model.addAttribute("adListHead",list);
        //列表右侧（待后台加功能）单独推荐软件表
        map.put("category",category);
        List<Map<String,Object>> rightAds = recommendProductService.getAll(map);
        model.addAttribute("rightAds",rightAds);

        Map productCategory = productCategoryService.getParentProductCategoryName(category);
        model.addAttribute("productCategory",productCategory);
        model.addAttribute("procount",pageInfo.getTotal());//产品数量
        Integer countCompanys = productService.getCountCompanys(map);
        model.addAttribute("countCompanys",countCompanys);//品牌数量
        return "/product/list";
    }


    /**
     * @param model
     * @param pageNum
     * @param pageSize
     * @param keywords
     * @return
     */
    @RequestMapping("/serchList")
    public String serchList(Model model, @RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "6")Integer pageSize,
                                   String keywords){
        Map<String,Object> map = new HashMap<>();
        map.put("keywords",keywords);
        Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> listProduct=productService.selectByKeywords(map);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>();
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        pageInfo.setList(listProduct);
        model.addAttribute("path",pathurl);//路径
        model.addAttribute("page",pageInfo);
        model.addAttribute("keywords",keywords);
        //列表头部广告
        Map<String,Object> mapad=new HashMap<>();
        mapad.put("adPosition",adpositionjhhead);//列表头部
        mapad.put("status",2);
        mapad.put("paymentStatus",2);
        List<Ad> list= adService.listAd(mapad);
        if (list.size()>5){
            list = list.subList(0,4);
        }
        model.addAttribute("adListHead",list);
//        列表右侧推荐软件
        List<Map<String,Object>> rightAds = recommendProductService.getAll(map);
        model.addAttribute("rightAds",rightAds);
        Integer countCompanys = productService.getCountCompanys(map);
        model.addAttribute("countCompanys",countCompanys);//品牌数量
        return "/product/serchList";
    }



    /**
     * 推荐软件详情页
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/rightView")
    public String rightView(Model model, Long id){

        //model.addAttribute("productList",listProduct);
        Member member = memberService.getCurrent();
        RecommendProduct recommendProduct = recommendProductService.selectByPrimaryKey(id);
        Company company = companyService.selectCompanyById(recommendProduct.getCompanyId());
        model.addAttribute("recommendProduct",recommendProduct);
        model.addAttribute("company",company);
        model.addAttribute("path",pathurl);//路径
        return "/product/rightView";
    }

    /**
     * 商品 详情
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/view")
    public String view(Model model, Long id,String title){

        //model.addAttribute("productList",listProduct);
        Member member = memberService.getCurrent();
        Map<String ,Object> query_map = new HashMap<>();

        //根据id查询产品
        Product product = productService.selectProductById(id);
        model.addAttribute("product",product);
        model.addAttribute("isaddedvalue",product.getIsAddService());
        //查询购买方式
        query_map.put("type",product.getIsAddService());

        List<Purchase> purchaseList = purchaseService.selectFangshi(query_map);
        for (Purchase purchase : purchaseList) {
            query_map.put("productId",id);
            query_map.put("purchaseId",purchase.getId());
            List<Map> productPuchases = productPurchaseService.getProductPuchases(query_map);
            purchase.setProduct_puchase(productPuchases);
        }
        model.addAttribute("purchases", purchaseList);//所有购买方式
        query_map.remove("type");
        query_map.put("productId",id);

        //获取详情页商品图片（小图）
        model.addAttribute("pathUrl",pathurl);//路径
        List<ProductImg> listImg = productImgService.selectByProductId(id);
        model.addAttribute("listImg",listImg);
        //获取详情页商品图片（大图）
        if(listImg.size()>0){
            model.addAttribute("picture",listImg.get(0));
        }else{
            model.addAttribute("picture",null);
        }
        //获取软件概览等八大项
        List<ProductSpecifications> listSpe =  productSpecificationsService.selectByProductId(id);
        model.addAttribute("listSpe",listSpe);

        //获取厂家图片
        long companyId = product.getCompanyId();
        Company company = companyService.selectCompanyById(companyId);
        model.addAttribute("company",company);

        //医院列表
//        List<Hospital> listHospital = hospitalService.selectHospital();
        //科室列表
        List<Department> listDepartment = departmentService.selectDepartment(null);
//        model.addAttribute("listHospital",listHospital);
        model.addAttribute("listDepartment",listDepartment);
        //大家都在看
        query_map.put("productCategory",product.getProductCategory());
        query_map.put("status",2);
        List<Map<String,Object>> topList = productService.selectByKeywords(query_map);
        model.addAttribute("toplist",topList);
        List<Map<String,Object>> product_meal_list = new ArrayList<>();
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

        Department memberDepartment = departmentService.selectByPrimaryKey(member.getDepartment()==null?0L:Long.valueOf(member.getDepartment()));
        model.addAttribute("memberDepartment",memberDepartment);//用户科室

        model.addAttribute("productMealList",product_meal_list);
        model.addAttribute("member",member);
        Area area = areaService.selectById(member.getArea());
        model.addAttribute("area",area);

        ProductCategory parentProductCategory = productCategoryService.getParentProductCategory(product.getProductCategory());
        model.addAttribute("parentProductCategory",parentProductCategory);
        Map productPurchase = productPurchaseService.getProductPurchase(query_map);
        model.addAttribute("price",productPurchase.get("price"));
        model.addAttribute("title",title);
        query_map.put("productId",id);
        List<Map> agentProducts = agentProductService.getAgentProducts(query_map);
        model.addAttribute("agentProducts",agentProducts);
        List<ProductSoftwarePackage> productSoftwarePackages = productSoftwarePackageService.getAll(query_map);
        model.addAttribute("productSoftwarePackages",productSoftwarePackages);

        return "/product/view";
    }

//    商品点击量
    @ResponseBody
    @RequestMapping(value = "/updateProductHits",method = RequestMethod.POST)
    public int updateProductHits(String ip,Long pid){

        Product product = productService.selectProductById(pid);

        product.setHits(product.getHits()+1);
        productService.updateProductHits(product,ip);
        return 0;

    }

    // 商品列表页输入关键字查询商品
    @RequestMapping(value = "/selectByKeywords",method = RequestMethod.POST)
    public String selectByKeywords(Model model,String keywords,Long parent,@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "1")Integer pageSize){
        Map<String,Object> map = new HashMap<>();
        map.put("parent",parent);
        map.put("keywords",keywords);

        Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> list = productService.selectByKeywords(map);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>();
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        pageInfo.setList(list);
        model.addAttribute("page",pageInfo);
        model.addAttribute("path",pathurl);//路径
        model.addAttribute("page",pageInfo);
        model.addAttribute("parent",parent);
        List<ProductCategory> listcate2= productCategoryService.selectByParents(0L,1L,null);
        model.addAttribute("categoryParentList",listcate2);//父类
        ProductCategory productCategory = productCategoryService.selectByPrimaryKey(parent);
        model.addAttribute("productCategory",productCategory);
        return "/product/list";
    }


    /**
     * 增值服务产品详情页
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/addedvalue")
    //参数：增值服务产品id，产品id
    public String selectAddedvalue(Model model, Long id){
        Product product = productService.selectProductById(id);
        model.addAttribute("product",product);
        //获取厂家图片
        long companyId = product.getCompanyId();
        Company company = companyService.selectCompanyById(companyId);
        model.addAttribute("company",company);


        //查询购买方式
        Map query_map= new HashMap();
        query_map.put("productId",id);
        List<Purchase> purchaseList = purchaseService.selectFangshi(query_map);
        for (Purchase purchase : purchaseList) {
            query_map.put("purchaseId",purchase.getId());
            List<Map> productPuchases = productPurchaseService.getProductPuchases(query_map);
            purchase.setProduct_puchase(productPuchases);
        }
        model.addAttribute("purchases", purchaseList);//所有购买方式



        //获取详情页商品图片（小图）
        model.addAttribute("pathUrl",pathurl);//路径
        List<ProductImg> listImg = productImgService.selectByProductId(id);
        model.addAttribute("listImg",listImg);
        //获取详情页商品图片（大图）
        if(listImg.size()>0){
            model.addAttribute("picture",listImg.get(0));
        }else{
            model.addAttribute("picture",null);
        }
        //获取软件概览等八大项
        List<ProductSpecifications> listSpe =  productSpecificationsService.selectByProductId(id);
        model.addAttribute("listSpe",listSpe);
        //大家都在看
        List<Product> topList = productService.selectTopByHits(5);
        model.addAttribute("toplist",topList);
        return "/product/view_addedvalue";
    }


    /**
     * 培训包详情页
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/trainPackagesView")
    public String trainPackagesView(Model model, Long id){
        Product product = productService.selectProductById(id);
        model.addAttribute("product",product);
        //获取厂家图片
        long companyId = product.getCompanyId();
        Company company = companyService.selectCompanyById(companyId);
        model.addAttribute("company",company);


        //查询购买方式
        Map query_map= new HashMap();
        query_map.put("productId",id);
        List<Purchase> purchaseList = purchaseService.selectFangshi(query_map);
        for (Purchase purchase : purchaseList) {
            query_map.put("purchaseId",purchase.getId());
            List<Map> productPuchases = productPurchaseService.getProductPuchases(query_map);
            purchase.setProduct_puchase(productPuchases);
        }
        model.addAttribute("purchases", purchaseList);//所有购买方式

        //获取详情页商品图片（小图）
        model.addAttribute("path",pathurl);//路径
        List<ProductImg> listImg = productImgService.selectByProductId(id);
        model.addAttribute("listImg",listImg);

        //获取软件概览等八大项
        List<ProductSpecifications> listSpe =  productSpecificationsService.selectByProductId(id);
        model.addAttribute("listSpe",listSpe);
        //大家都在看
        List<Product> topList = productService.selectTopByHits(5);
        model.addAttribute("toplist",topList);
        model.addAttribute("pathUrl",pathurl);//路径
        return "/product/trainPackagesView";
    }




    @RequestMapping("/selectGuige")
    @ResponseBody
    public List<Map<String,Object>> selectGuige(Long id,Long parentid){
        //查询购买规格
        Map<String,Object> map = new HashMap<>();
        map.put("product",id);
        map.put("parent",parentid);
        List<Map<String,Object>> list = productPurchaseService.selectByProductParent(map);//根据产品id和购买方式的id查询产品规格

        return list;
    }

    @RequestMapping("/addConsultation")
    @ResponseBody
    public Integer addConsultation(Long pid,Long cid,String content){
        //添加咨询
        Consultation consultation = new Consultation();
        consultation.setCreateDate(new Date());
        consultation.setModifyDate(new Date());
        consultation.setContent(content);
        consultation.setProduct(pid);
        consultation.setCompanyId(cid);
        Member member = memberService.getCurrent();
        Long memberid = member.getId();
        consultation.setMember(memberid);
        consultation.setStatus(0l);//未发送
        int result = consultationService.addConsultation(consultation);

        return result;
    }


    /**
     * 实时检索产品
     * @param jsonObject
     * @return
     */
    @RequestMapping("/searchProductName")
    @ResponseBody
    public String searchProductName(@RequestBody JSONObject jsonObject){
        Map<String,Object> query_map = (Map<String, Object>) JSON.parse(jsonObject.toJSONString());
        List<Map<String,Object>> list = productService.selectByKeywords(query_map);
        return JSONObject.toJSONString(list);
    }

    /**
     * 根据地区查询地区
     * @param areaId
     * @return
     */
    @RequestMapping("/hospitals")
    @ResponseBody
    public String hospitals(Long areaId){
        Map query_map = new HashMap();
        query_map.put("areaId",areaId);
        List<Hospital> hospitals = hospitalService.getHospitals(query_map);
        return ResultUtil.success(hospitals);
    }





}
