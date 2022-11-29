package com.tongfu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author wuyongao
 * @Description TODO
 * @Date 2022/08/12/18:41
 * @Version 1.0
 */
@Configuration
public class MyFileConfig extends WebMvcConfigurerAdapter {
    /**
     * 附件上传路径
     */
    @Value("${file-web-path}")
    private String fileWebPath;
    /**
     * 上传的图片在F盘下的file目录下，访问路径如：http://localhost:8080/file/d3cf0281-bb7f-40e0-ab77-406db95ccf2c.jpg
     *  其中file表示访问的前缀。"file:F:/file/"是文件真实的存储路径
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //企业注册附件
        System.out.println(fileWebPath);
        registry.addResourceHandler("/licenseImage/**").addResourceLocations("file:"+fileWebPath+"/licenseImage/");
        //#医生会议申请邀请函
        registry.addResourceHandler("/invitationImg/**").addResourceLocations("file:"+fileWebPath+"/invitationImg/");
        //组委会申请合同上传路径
        registry.addResourceHandler("/companyLicense/**").addResourceLocations("file:"+fileWebPath+"/companyLicense/");
        //上传病例的DICOM数据和诊断报告
        registry.addResourceHandler("/casesFile/**").addResourceLocations("file:"+fileWebPath+"/casesFile/");
        /**
         * 激活码文件
         */
        registry.addResourceHandler("/orderItemCode/**").addResourceLocations("file:"+fileWebPath+"/orderItemCode/");
    }

}
