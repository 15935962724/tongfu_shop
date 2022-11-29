package com.tongfu.controller;

import com.tongfu.entity.ArticleStatistics;
import com.tongfu.entity.Company;
import com.tongfu.entity.Exhibition;
import com.tongfu.service.ArticleStatisticsService;
import com.tongfu.service.CompanyService;
import com.tongfu.service.ExhibitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;


@Controller
@RequestMapping("/exhibition")
public class ExhibitionController {

    @Value("${path-url}")
    private String pathurl;

    @Autowired
    private CompanyService companyService;//公司

    @Autowired
    private ExhibitionService exhibitionService;

    @Autowired
    private ArticleStatisticsService articleStatisticsService;//文章统计


    /**
     * 展会展览详情页
     * @param model
     * @param companyId
     * @param id
     * @return
     */
    @RequestMapping("/view")
    public String view(Model model,Long companyId, Long id) {

        Company company = companyService.selectCompanyById(companyId);
        Exhibition exhibition = exhibitionService.selectByPrimaryKey(id);
        exhibition.setHits(exhibition.getHits()+1);
        exhibitionService.updateByPrimaryKeySelective(exhibition);

        ArticleStatistics articleStatistics = new ArticleStatistics();
        articleStatistics.setActicleId(id);
        articleStatistics.setCompanyId(exhibition.getCompanyId());
        articleStatistics.setCreateDate(new Date());
        articleStatistics.setTableType("4");
        articleStatisticsService.insertSelective(articleStatistics);

        model.addAttribute("path",pathurl);
        model.addAttribute("company",company);
        model.addAttribute("active",6);
        model.addAttribute("exhibition",exhibition);
        return "/exhibition/view";

    }



}
