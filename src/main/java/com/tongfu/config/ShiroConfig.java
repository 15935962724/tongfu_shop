package com.tongfu.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 权限配置
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")
                                                                        DefaultWebSecurityManager
                                                                        securityManager) {
        //1.定义shiroFactoryBean
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        //2.设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //3.LinkedHashMap是有序的，进行顺序拦截器配置
        Map<String,String> filterChainMap = new LinkedHashMap<String,String>();
        //4.配置logout过滤器
        filterChainMap.put("/logout", "logout");
        //5.所有url必须通过认证才可以访问
//        filterChainMap.put("/product/view","authc");
        //解决shiro 登录成功后无法正确跳转successUrl的问题，重写Filter

        //filterChainMap.put("/main.html","authc");
        //6.设置默认登录的url
//        shiroFilterFactoryBean.setLoginUrl("/login.html");
        //7.设置成功之后要跳转的链接
//        shiroFilterFactoryBean.setSuccessUrl("/product/view");
//
//        filterMap.put("/login/**", "anon"); //登陆
//        filterMap.put("/kaptcha", "anon");  //验证码
//        filterMap.put("/global/*", "anon");  //全局路径（错误或者超时）
//
//        filterMap.put("/favicon.ico", "anon");
//        filterMap.put("/**", "authc");
//
        //8.设置未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        //9.设置shiroFilterFactoryBean的FilterChainDefinitionMap
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        return shiroFilterFactoryBean;
    }

//    @Bean
//    public ShiroFilterFactoryBean formAuthenticationFilter(@Qualifier("securityManager")DefaultWebSecurityManager securityManager) {
//
//        return shiroFilterFactoryBean;
//    }




    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了）
     * HashedCredentialsMatcher说明：
     * 用户传入的token先经过shiroRealm的doGetAuthenticationInfo方法
     * 此时token中的密码为明文。
     * 再经由HashedCredentialsMatcher加密password与查询用户的结果password做对比。
     * new SimpleHash("SHA-256", password, null, 1024).toHex();
     * @return
     */
//    @Bean
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        hashedCredentialsMatcher.setHashAlgorithmName("MD5");//散列算法:这里使用SHA-256算法;
//        hashedCredentialsMatcher.setHashIterations(1024);//散列的次数，比如散列两次，相当于 MD5(MD5(""));
//        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
//        return hashedCredentialsMatcher;
//    }

    @Bean(name = "myRetryLimitCredentialsMatcher")
    public MyRetryLimitCredentialsMatcher hashedCredentialsMatcher() {
        MyRetryLimitCredentialsMatcher hashedCredentialsMatcher = new MyRetryLimitCredentialsMatcher();
        // 采用MD5方式加密
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        // 设置加密次数
        hashedCredentialsMatcher.setHashIterations(1024);
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    /**
     * 配置安全管理器
     * 此处不应将自定义Filter注册为 @Bean 否则SpringBoot将加载此Filter导致ShiroFilter优先级失效等一系列问题
     * http://www.hillfly.com/2017/179.html
     * @return
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("myShiroRealm")MyShiroRealm myShiroRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //将自定义的realm交给SecurityManager管理
//        securityManager.setRealm(shiroRealm());
        securityManager.setRealm(myShiroRealm);
//        // 自定义缓存实现 使用redis
//        securityManager.setCacheManager(cacheManager());
//        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
//        // 使用记住我
        securityManager.setRememberMeManager(rememberMeManager());

        return securityManager;
    }


    @Bean
      public RememberMeManager rememberMeManager() {
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        //注入自定义cookie(主要是设置寿命, 默认的一年太长)
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        //设置RememberMe的cookie有效期为7天
        simpleCookie.setMaxAge(604800);
        rememberMeManager.setCookie(simpleCookie);
    return rememberMeManager;
    }


        @Bean(name="myShiroRealm")
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm realm = new MyShiroRealm();
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        realm.setCacheManager(new MemoryConstrainedCacheManager());
//        //没有配置权限缓存,所以关闭授权缓存域
//        realm.setAuthorizationCachingEnabled(false);
        return realm;
    }

//    @Bean(name="myShiroRealm")
//    public MyShiroRealm myShiroRealm(MyRetryLimitCredentialsMatcher matcher) {
//        MyShiroRealm realm = new MyShiroRealm();
//        realm.setCredentialsMatcher(matcher);
//        return realm;
//    }


    /**
     * session的过期时间
     * @return
     */
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 设置session过期时间1200s
        sessionManager.setGlobalSessionTimeout(1000*60*60*4);
        //sessionManager.setGlobalSessionTimeout(1000*30);
        return sessionManager;
    }



    /**
     * 自定义权限注解
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager")SecurityManager  securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

//    @Bean
//    public SecurityManager securityManager(@Qualifier("myRetryLimitCredentialsMatcher") MyRetryLimitCredentialsMatcher matcher){
//        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
//        securityManager.setRealm(myShiroRealm(matcher));
//
//        // 自定义缓存实现 使用redis
////        securityManager.setCacheManager(cacheManager());
//        // 自定义session管理 使用redis
//        securityManager.setSessionManager(sessionManager());
//        return securityManager;
//    }


    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    public static String shiroEncryption(String password,String salt) {

        // shiro 自带的工具类生成salt
        //String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        // 加密次数
        int times = 1024;
        // 算法名称
        String algorithmName = "MD5";

        String encodedPassword = new SimpleHash(algorithmName,password, ByteSource.Util.bytes(salt),times).toString();

        // 返回加密后的密码
        return encodedPassword;
    }

	
}