package com.tongfu.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.tongfu.Util.ResultUtil;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;


public class GunsUserFilter extends AccessControlFilter {

    /**
     *
     * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
     * (感觉这里应该是对白名单（不需要登录的接口）放行的)
     * 如果isAccessAllowed返回true则onAccessDenied方法不会继续执行
     * 这里可以用来判断一些不被通过的链接（个人备注）
     * * 表示是否允许访问 ，如果允许访问返回true，否则false；
     * @param request
     * @param response
     * @param mappedValue 表示写在拦截器中括号里面的字符串 mappedValue 就是 [urls] 配置中拦截器参数部分
     * @return
     * @throws Exception
     * */

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {


        if (isLoginRequest(request, response)) {
            return true;
        } else {
            Subject subject = getSubject(request, response);
            // If principal is not null, then the user is known and should be allowed access.
            return ((Subject) subject).getPrincipal() != null;
        }
    }

    /**
     * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
     * onAccessDenied是否执行取决于isAccessAllowed的值，如果返回true则onAccessDenied不会执行；如果返回false，执行onAccessDenied
     * 如果onAccessDenied也返回false，则直接返回，不会进入请求的方法（只有isAccessAllowed和onAccessDenied的情况下）
     * */
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);

        //前端http请求中code为403的时候跳转到登陆页，R.fail()为你返回给前端的json对象
        RenderUtil.renderJson(httpServletResponse, ResultUtil.success());

        return false;

    }
}