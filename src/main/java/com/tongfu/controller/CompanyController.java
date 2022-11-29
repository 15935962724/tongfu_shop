package com.tongfu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/company")
public class CompanyController {

    @Value("${path-url}")
    private String pathurl;

    @Autowired
    private CompanyService companyService;//公司

    @Autowired
    private ProductService productService;//产品

    @Autowired
    private ProductCategoryService pcService;//产品类别

    @Autowired
    private JournalismService journalismService;//新闻

    @Autowired
    private MediaService mediaService;//媒体

    @Autowired
    private ExhibitionService exhibitionService;//展会展览

    @Autowired
    private CompanyStatisticsService companyStatisticsService;

    @Autowired
    private MemberService memberService;

    /**
     * 供应商详情页
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/view")
    public String view(Model model,Long id) {
        Member member = memberService.getCurrent();
        CompanyStatistics companyStatistics = new CompanyStatistics();//品牌关注度
        companyStatistics.setCompanyId(id);
        companyStatistics.setMember(member!=null?member.getId():null);
        companyStatistics.setCreateDate(new Date());
        companyStatisticsService.insertSelective(companyStatistics);

        Company company = companyService.selectCompanyById(id);
        model.addAttribute("path",pathurl);
        model.addAttribute("company",company);
        model.addAttribute("active",1);
        //商品类别的第一个id
        List<ProductCategory> listcate3= pcService.selectByParents(0L,1l,null);
        if(listcate3.size()>0){
            ProductCategory productCategory = listcate3.get(0);
            model.addAttribute("categoryId",productCategory.getId());
        }

        return "/company/view";

    }

    @RequestMapping("/gotoproduct")
    //公司产品页(公司id,父类id）
    public String goToProduct(Model model, Long id,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "8")Integer pageSize) {
        Map<String,Object> map = new HashMap<>();
        Company company = companyService.selectCompanyById(id);
        model.addAttribute("company",company);
        model.addAttribute("path",pathurl);//路径

        map.put("type",1);
        List<ProductCategory> productCategories = pcService.getParentProductCategory(map);

        model.addAttribute("active",2);
        //产品
        map.put("company",id);//公司id
        map.put("status",2);//审核状态
        Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> listProduct = productService.selectByCompanyCategory(map);//根据父类和公司查询出来的产品列表
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>();
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        pageInfo.setList(listProduct);
        model.addAttribute("page",pageInfo);
        return "/company/listProduct";

    }



    @RequestMapping("/gotonews")
    //公司新闻页(公司id）
    public String goToNews(Model model, Long id,String keyword,
                           @RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "3")Integer pageSize) {

        Company company = companyService.selectCompanyById(id);
        model.addAttribute("path",pathurl);
        model.addAttribute("company",company);
        model.addAttribute("active",3);
//        //商品类别的第一个id
//        List<ProductCategory> listcate3= pcService.selectByParents(0L,1l,null);
//        if(listcate3.size()>0){
//            ProductCategory productCategory = listcate3.get(0);
//            model.addAttribute("categoryId",productCategory.getId());
//        }
        //封装map
        Map<String,Object> map = new HashMap<>();
        map.put("company",id);
        map.put("keyword",keyword);
        map.put("status",2);//审核状态
        Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> list = journalismService.selectByCompanyKeyword(map);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>();
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        pageInfo.setList(list);
        model.addAttribute("page",pageInfo);
        model.addAttribute("keyword",keyword);
        return "/company/listNews";

    }

    @RequestMapping("/gotomedia")
    //媒体报道(公司id）
    public String goToMedia(Model model, Long id,@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "4")Integer pageSize) {

        Company company = companyService.selectCompanyById(id);
        model.addAttribute("path",pathurl);
        model.addAttribute("company",company);
        model.addAttribute("active",5);
        //商品类别的第一个id
        List<ProductCategory> listcate3= pcService.selectByParents(0L,1l,null);
        if(listcate3.size()>0){
            ProductCategory productCategory = listcate3.get(0);
            model.addAttribute("categoryId",productCategory.getId());
        }
        Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> list = mediaService.selectByCompany(id);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>();
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        pageInfo.setList(list);
        model.addAttribute("page",pageInfo);
        // model.addAttribute("newslist",list);//新闻
        return "/company/listMedia";

    }

    @RequestMapping("/gotoexhibition")
    //展会展览页(公司id,父类id）
    public String goToExhibition(Model model, Long id) {

        Company company = companyService.selectCompanyById(id);
        model.addAttribute("path",pathurl);
        model.addAttribute("company",company);
        model.addAttribute("active",6);

        Map<String,Object> query_map = new HashMap<>();
        query_map.put("companyId",id);
        query_map.put("status",2);//审核通过的
        List<Exhibition> exhibitions = exhibitionService.getAll(query_map);
        model.addAttribute("exhibitions",exhibitions);

        return "/company/listExhibition";

    }

    /**
     * 搜索结果跳转页面
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
        map.put("name",keywords);
        Page<Company> page  = PageHelper.startPage(pageNum,pageSize);
        List<Company> listCompany = companyService.companys(map);
        PageInfo<Company> pageInfo = new PageInfo<Company>();
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        pageInfo.setList(listCompany);
        model.addAttribute("path",pathurl);//路径
        model.addAttribute("page",pageInfo);
        model.addAttribute("keywords",keywords);
        return "/company/serchList";
    }


    /**
     * 搜索供应商
     * @param jsonObject
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public String list(@RequestBody JSONObject jsonObject){
        Map<String,Object> query_map = (Map<String, Object>) JSON.parse(jsonObject.toJSONString());
        List<Company> companys = companyService.companys(query_map);
        return JSONObject.toJSONString(companys);
    }




}
