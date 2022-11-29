package com.tongfu.config;



import com.tongfu.entity.Ad;
import com.tongfu.entity.ProductCategory;
import com.tongfu.service.AdService;
import com.tongfu.service.ProductCategoryService;
import com.tongfu.service.WebsiteStatisticsService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.*;


public class OneInterceptor  implements HandlerInterceptor {



    /** "重定向URL"参数名称 */
    private static final String REDIRECT_URL_PARAMETER_NAME = "redirectUrl";



    /** 默认登录URL */
    private static final String DEFAULT_LOGIN_URL = "/denglu";

    /** 登录URL */
    private String loginUrl = DEFAULT_LOGIN_URL;

    @Autowired
    AdService adService;
    @Autowired
    ProductCategoryService pcService;

    @Autowired
    WebsiteStatisticsService websiteStatisticsService;

    @Value("${path-url}")
    private String pathUrl;

    @Value("${adpositiontop}")
    private Long adpositiontop;

    @Value("${adpositionmiddle}")
    private Long adpositionmiddle;

    @Value("${adpositionfoot}")
    private Long adpositionfoot;

    @Value("${map-key}")
    private String mapKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        System.out.println("=====================");
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipal() != null) {
            return true;
        } else {
            String requestType = request.getHeader("X-Requested-With");
            if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
                response.addHeader("loginStatus", "accessDenied");
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return false;
            } else {
                if (request.getMethod().equalsIgnoreCase("GET")) {
                    System.out.println(request.getRequestURI()+"==="+request.getQueryString()+"==="+request.getRequestURI());
                    String redirectUrl = request.getQueryString() != null ? request.getRequestURI() + "?" + request.getQueryString() : request.getRequestURI();
                    redirectUrl= redirectUrl.replace("/tongfu","");
                    System.out.println(request.getContextPath() + loginUrl + "?" + REDIRECT_URL_PARAMETER_NAME + "=" + URLEncoder.encode(redirectUrl,"UTF-8"));
                    response.sendRedirect(request.getContextPath() + loginUrl + "?" + REDIRECT_URL_PARAMETER_NAME + "=" + URLEncoder.encode(redirectUrl, "UTF-8"));
                } else {
                    System.out.println(request.getContextPath() + loginUrl);
                    response.sendRedirect(request.getContextPath() + loginUrl);
                }
                return false;
            }
        }




    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}