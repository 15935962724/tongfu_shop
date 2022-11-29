package com.tongfu.service;

import com.tongfu.Util.HTMLSpirit;
import com.tongfu.entity.Journalism;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

//新闻列表页读取新闻内容
@Service("journalismSer")
public class JournalismSer {
    @Autowired
    JournalismService journalismService;

    //根据公司新闻主键查询(id:journalism主键，num：截取字符串的个数）
    public String getContent(Long id,Integer num){
        Journalism journalism = journalismService.selectByPrimarykey(id);
        String content = journalism.getContent();
        String html = HTMLSpirit.delHTMLTag(content);
        html = html.substring(0,num);
        return  html;
    }

}
