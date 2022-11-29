package com.tongfu.controller.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongfu.Util.ResultUtil;
import com.tongfu.Util.SendCode;
import com.tongfu.Util.SettingUtils;
import com.tongfu.Util.WeiXinUtils;
import com.tongfu.config.CustomToken;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller("wexinLogin")
@RequestMapping("/weixin/login")
public class LoginController {

    @Autowired
    private MemberService memberService;


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

    @Value("${wxAppid}")
    private String wxAppid;

    @Value("${wxSecret}")
    private String wxSecret;


    /**
     * 微信登录
     * @param jsonObject
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/submig")
    @ResponseBody
    public String submig(@RequestBody JSONObject jsonObject) throws UnsupportedEncodingException {

        Subject subject = SecurityUtils.getSubject();
            jsonObject.put("appId",wxAppid);
            jsonObject.put("secret",wxSecret);
            System.out.println("微信参数"+jsonObject);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            WeiXinUtils wxu = new WeiXinUtils();
            JSONObject userinfojson = null;
            try {
                userinfojson = JSON.parseObject(wxu.getUserInfo(jsonObject));
            } catch (IOException e) {
                System.out.println("获取微信信息出错!");
                e.printStackTrace();
            }
            System.out.println(userinfojson);
            String openid = userinfojson.getString("openid");
            System.out.println("获取登录人--nickName>>>"+userinfojson.get("nickname").toString()+"---访问时间>>>" + sdf.format(new Date()));
            Map<String,Object> map=new HashMap<>();
            map.put("openId",openid);
            Member member = memberService.getMember(map);
            if (member!=null){
                CustomToken token = new CustomToken(member.getUsername());
                subject.login(token);
            }


        return "";
    }

    /**
     * 手机号验证码登录
     * @param jsonObject
     * @return
     * @throws UnsupportedEncodingException
     */
    @PostMapping("/phoneSubmig")
    @ResponseBody
    public String phoneSubmig(@RequestBody JSONObject jsonObject){
        Subject subject = SecurityUtils.getSubject();
        Map query_map = (Map) JSON.parse(jsonObject.toJSONString());
        String phone = jsonObject.getString("phone");
        String sessionCode = (String) subject.getSession().getAttribute(phone);
        System.out.println("session验证码为:"+sessionCode);
        query_map.put("username",phone);
        query_map.put("isLocked",false);
        try {
            Member member = memberService.getMember(query_map);
            if (member==null){
                return ResultUtil.error("该用户未注册!");
            }
            if (member.getIsLocked()){
                return ResultUtil.error("该用户已被锁定请联系管理员!");

            }
            if (!jsonObject.getString("code").equals(sessionCode)){
                return ResultUtil.error("验证码输入有误!");
            }
            CustomToken token = new CustomToken(member.getUsername());
            subject.login(token);
            return ResultUtil.success();
        } catch (IncorrectCredentialsException e) {
            System.out.println(e.getMessage());
            return ResultUtil.error("密码错误!");
        }  catch (AuthenticationException e) {
            System.out.println(e.getMessage());
            return ResultUtil.error("该用户未注册!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResultUtil.error("登录错误");
        }
    }

    /**
     * 微信端发送验证码
     * @param jsonObject
     * @return
     */
    @PostMapping("/sendCode")
    @ResponseBody
    public String sendCode(@RequestBody JSONObject jsonObject){
        Subject subject = SecurityUtils.getSubject();
        String phone = jsonObject.getString("phone");
        Map<String,String> map = new HashMap<>();
        Map<String,Object> set_map = SettingUtils.getReadXml(pathUrl) ;
        map.put("url",String.valueOf(set_map.get("shortMessageUrl")));
        map.put("accountSid",String.valueOf(set_map.get("accountSid")));
        map.put("authToken",String.valueOf(set_map.get("authToken")));
        map.put("phone",phone);
        Random random = new Random();
        String code = String.valueOf(random.nextInt(1000000));
        code = String.format("%0"+6+"d",Integer.parseInt(code));
        map.put("param",code);
        map.put("templateid",jsonObject.getString("templateid"));
        System.out.println("验证码为:"+code);
        String data = SendCode.getSendCodeMessage(map);
        System.out.println(data);
        JSONObject json = JSON.parseObject(data);
        if (json.getString("respCode").equals("0000")){
            System.out.println("发送成功");
            subject.getSession().setAttribute(phone,code);
            subject.getSession().setAttribute("codeTime", new Date().getTime());
            return  ResultUtil.success("验证码发送成功",null);
        }else {
            System.out.println("验证码发送失败，失败原因为:" +json.getString("respDesc"));
            return ResultUtil.error("验证码发送失败");
        }

    }


}
