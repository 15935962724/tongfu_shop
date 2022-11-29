package com.tongfu.controller;

import com.tongfu.entity.ArticleStatistics;
import com.tongfu.entity.Company;
import com.tongfu.entity.Journalism;
import com.tongfu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;


@Controller
@RequestMapping("/journalism")
public class JournalismController {

    @Value("${path-url}")
    private String pathurl;

    @Autowired
    private CompanyService companyService;//公司

    @Autowired
    private JournalismService journalismService;//新闻

    @Autowired
    private ArticleStatisticsService articleStatisticsService;//文章统计



    /**
     * 新闻动态详情页
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/view")
    public String view(Model model,Long companyId, Long id) {
        Journalism journalism =  journalismService.selectByPrimarykey(id);
        journalism.setHits(journalism.getHits()+1);
        journalismService.updateByPrimaryKeySelective(journalism);
        Company company = companyService.selectCompanyById(journalism.getCompanyId());
        ArticleStatistics articleStatistics = new ArticleStatistics();
        articleStatistics.setActicleId(id);
        articleStatistics.setCompanyId(journalism.getCompanyId());
        articleStatistics.setCreateDate(new Date());
        articleStatistics.setTableType("1");
        articleStatisticsService.insertSelective(articleStatistics);
        model.addAttribute("path",pathurl);
        model.addAttribute("company",company);
        model.addAttribute("active",3);
        model.addAttribute("journalism",journalism);
        return "/journalism/view";

    }



}
