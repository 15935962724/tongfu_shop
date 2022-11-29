package com.tongfu.config;


import com.tongfu.entity.Member;
import com.tongfu.service.MemberService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.*;

public class MyShiroRealm extends AuthorizingRealm {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MemberService memberService;

    /*@Autowired
    AdminService adminService;

    @Autowired
    RoleService roleService;

    @Autowired
    RoleAuthorityService roleAuthorityService;*/

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        //获取登录用户名
        String userName= (String) principalCollection.getPrimaryPrincipal();

        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
//        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }

        CustomToken token = (CustomToken) authenticationToken;

        String username =token.getPrincipal().toString();
//        String passWord = new String((char[])authenticationToken.getCredentials());
        logger.info("username = {}", username);
        Member member = null;
        //Admin admin = null;
        try {
//            member = memberService.selectByUserName(username);
            Map map = new HashMap();
            map.put("username",username);
            map.put("isLocked",false);
            member = memberService.getMember(map);
             //admin  = adminService.selectByUserName(username);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

        logger.info("{}", null!=member?member.toString():"null");
        if(null!=member){
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    member, //用户名
                    member.getPassword(), //密码
                    ByteSource.Util.bytes(member.getUsername()),//salt=username+salt
                    getName()  //realm name
            );

            return authenticationInfo;
        }
        return null;
    }
}
