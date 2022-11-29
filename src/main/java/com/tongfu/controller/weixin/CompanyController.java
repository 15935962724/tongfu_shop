package com.tongfu.controller.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.Util.ResultUtil;
import com.tongfu.entity.Company;
import com.tongfu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller("weixincompany")
@RequestMapping("/weixin/company")
public class CompanyController {

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

    /**
     * 供应商详情页
     * @param jsonObject
     * @return
     */
    @RequestMapping("/view")
    @ResponseBody
    public String view(@RequestBody JSONObject jsonObject) {
        JSONObject returnJson = new JSONObject();
        jsonObject.put("status",2);
        Map query_map = (Map<String, Object>) JSON.parse(jsonObject.toJSONString());
        Company company = companyService.selectCompanyById(jsonObject.getLong("company"));
        returnJson.put("company",company);
        List companyProduct = productService.selectByCompanyCategory(query_map);
        returnJson.put("products",companyProduct);
        List<Map> journalisms = journalismService.getJournalisms(query_map);
        returnJson.put("journalisms",journalisms);
        return ResultUtil.success(returnJson);
    }


    /**
     * 搜索公司
     * @param jsonObject
     * @return
     */
    @RequestMapping("/serchList")
    @ResponseBody
    public String serchList(@RequestBody JSONObject jsonObject){
        Map<String,Object> map = new HashMap<>();
        map.put("name",jsonObject.getString("keywords"));
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Page<Company> page  = PageHelper.startPage(pageNum,pageSize);
        List<Company> listCompany = companyService.companys(map);
        PageInfo<Company> pageInfo = new PageInfo<Company>();
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        pageInfo.setList(listCompany);
        JSONObject returnJson = new JSONObject();
        returnJson.put("page",pageInfo);
        return ResultUtil.success(returnJson);
    }



    /**
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping("/articleList")
    @ResponseBody
    public String articleList(@RequestBody JSONObject jsonObject) {
        JSONObject returnJson = new JSONObject();
        jsonObject.put("status","2");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Map<String,Object> query_map = (Map<String, Object>) JSON.parse(jsonObject.toJSONString());
        Integer tableNameCount = journalismService.getTableNameCount(query_map);
        query_map.put("firstIndex",(pageNum-1)*pageSize);
        query_map.put("lastsIndex",pageNum*pageSize);
        List<Map> journalisms = journalismService.getJournalismsLearningMediaExhibition(query_map);
        returnJson.put("total",(tableNameCount+pageSize-1)/pageSize);
        returnJson.put("journalisms",journalisms);
        return ResultUtil.success(returnJson);
    }

    /**
     * 详情
     * @param jsonObject
     * @return
     */
    @RequestMapping("/articleView")
    @ResponseBody
    public String articleView(@RequestBody JSONObject jsonObject) {
        JSONObject returnJson = new JSONObject();
        Map<String,Object> query_map = (Map<String, Object>) JSON.parse(jsonObject.toJSONString());
        Map article = journalismService.getArticleView(query_map);
        returnJson.put("articel",article);
        return ResultUtil.success(article);
    }



}
