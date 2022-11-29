package com.tongfu.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.ArticleStatistics;
import com.tongfu.entity.Company;
import com.tongfu.entity.Media;
import com.tongfu.service.ArticleStatisticsService;
import com.tongfu.service.CompanyService;
import com.tongfu.service.MediaService;
import com.tongfu.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/media")
public class MediaController {

    @Value("${path-url}")
    private String pathurl;

    @Autowired
    private CompanyService companyService;//公司

    @Autowired
    private ProductCategoryService pcService;//产品类别

    @Autowired
    private MediaService mediaService;//媒体

    @Autowired
    private ArticleStatisticsService articleStatisticsService;//文章统计

    /**
     * 媒体报道
     * @param model
     * @param id
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/list")
    //媒体报道(公司id）
    public String list(Model model, Long id,@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "4")Integer pageSize) {

        Company company = companyService.selectCompanyById(id);
        model.addAttribute("path",pathurl);
        model.addAttribute("company",company);
        model.addAttribute("active",5);
        Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> list = mediaService.selectByCompany(id);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>();
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        pageInfo.setList(list);
        model.addAttribute("page",pageInfo);
        return "/media/list";

    }

    /**
     * 媒体报道详情页
     * @param model
     * @param companyId
     * @param id
     * @return
     */
    @RequestMapping("/view")
    public String view(Model model,Long companyId, Long id) {

        Company company = companyService.selectCompanyById(companyId);
        Media media = mediaService.selectByPrimaryKey(id);
        media.setHits(media.getHits()+1);
        mediaService.updateByPrimaryKeySelective(media);
        ArticleStatistics articleStatistics = new ArticleStatistics();
        articleStatistics.setActicleId(id);
        articleStatistics.setCompanyId(media.getCompanyId());
        articleStatistics.setCreateDate(new Date());
        articleStatistics.setTableType("3");
        articleStatisticsService.insertSelective(articleStatistics);
        model.addAttribute("path",pathurl);
        model.addAttribute("company",company);
        model.addAttribute("active",5);
        model.addAttribute("media",media);

        return "/media/view";

    }



}
