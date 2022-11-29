package com.tongfu.controller;

import ch.qos.logback.core.joran.spi.XMLUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.tongfu.Util.*;
import com.tongfu.entity.Member;
import com.tongfu.entity.Order;
import com.tongfu.entity.OrderItem;
import com.tongfu.entity.OrderPayment;
import com.tongfu.service.MemberService;
import com.tongfu.service.OrderItemService;
import com.tongfu.service.OrderPaymentService;
import com.tongfu.service.OrderService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller("payment")
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private OrderService orderService;//订单

    @Autowired
    private MemberService memberService;

    @Autowired
    private OrderPaymentService orderPaymentService;

    @Value("${path-url}")
    private String pathUrl;

    //	微信appid
    @Value("${wxAppid}")
    private String appid;

    //	微信商户号
    @Value("${mch_id}")
    private String mch_id;

    //	微信商户key
    @Value("${key}")
    private String key;

//    支付宝私钥 开发者私钥，由开发者自己生成
    @Value("${alipay-privatekey}")
    private String privatekey;
//支付宝公钥 支付宝公钥，由支付宝生成
    @Value("${alipay-publickey}")
    private String publickey;



    //支付宝回调方法（用该订单号去支付宝查询该订单是否支付成功{查询支付成功后：改变订单状态、创建订单日志（支付失败也要创建）、增加积分}）
    @RequestMapping("/alipayNotifyUrl")
    @ResponseBody
    public String notifyUrl(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, AlipayApiException {
        StringBuffer yan = request.getRequestURL();
        System.out.println("URL============" + yan.toString());
        String query = request.getQueryString();
        System.out.println("query============" + query);
        String result = "";
        Map<String, String> params = new HashMap<String, String>();

        Map requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"),"utf-8"); URLEncoder.encode(json.toString(),"UTF-8");
            //valueStr = URLDecoder.decode(valueStr,"UTF-8");
            params.put(name, valueStr);
        }
        System.out.println("params==============" + params);
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
        OrderPayment orderPayment = orderPaymentService.getByOutTradeNo(out_trade_no);//付款单
        //        boolean signVerified = AlipaySignature.rsaCheckV2(params, publickey, alicharset, signtype); //调用SDK验证签名
//         boolean signVerified =  AlipaySignature.rsaCheckV1(params,publickey,alicharset,signtype);
        String APP_ID="2019111969290311";//APPID 即创建应用后生成
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                APP_ID, privatekey, "json", "UTF-8", publickey, "RSA2"); //获得初始化的AlipayClient
        AlipayTradeQueryRequest zfbrequest = new AlipayTradeQueryRequest();
        zfbrequest.setBizContent("{" +
                "\"out_trade_no\":\""+out_trade_no+"\"}");
        AlipayTradeQueryResponse zubresponse = alipayClient.execute(zfbrequest);
        System.out.println("异步通知账单查询结果:"+zubresponse.getMsg());
        Member member = memberService.getCurrent();
        if (zubresponse.isSuccess()) {//验证成功
            //商户订单号
            if (orderPayment != null) {
                String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
                BigDecimal amount = new BigDecimal(total_amount);//订单金额
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("orderPayment",orderPayment);//订单
                jsonObject.put("amount",amount);//支付金额
                jsonObject.put("paymentMethodName","支付宝");//支付方式
                jsonObject.put("member",member);
                String retrunData = orderService.notifyUrlService(jsonObject);
                if (retrunData=="success"){
                    result = "success" ;
                }else {
                    System.out.println("程序代码有问题，无需支付宝再次推送!");
                    Map<String,String> map = new HashMap<>();
                    Map<String,Object> set_map = SettingUtils.getReadXml(pathUrl) ;
                    map.put("url",String.valueOf(set_map.get("shortMessageUrl")));
                    map.put("accountSid",String.valueOf(set_map.get("accountSid")));
                    map.put("authToken",String.valueOf(set_map.get("authToken")));
                    map.put("phone","13522151165");
                    map.put("param",orderPayment.getOutTradeNo());
                    map.put("templateid","259645");
                    SendCode.getSendCodeMessage(map);
                    result = "success";
                }
            }else {
                System.out.println("程序代码有问题，无需支付宝再次推送!");
                Map<String,String> map = new HashMap<>();
                Map<String,Object> set_map = SettingUtils.getReadXml(pathUrl) ;
                map.put("url",String.valueOf(set_map.get("shortMessageUrl")));
                map.put("accountSid",String.valueOf(set_map.get("accountSid")));
                map.put("authToken",String.valueOf(set_map.get("authToken")));
                map.put("phone","13522151165");
                map.put("param",orderPayment.getOutTradeNo());
                map.put("templateid","259651");
                SendCode.getSendCodeMessage(map);
                result = "success";
            }

            System.out.println("success");

        } else {//验证失败
            System.out.println("支付宝异步推送查询账单失败!");
            result = "fail";

        }
        return result;
    }


    //微信回调方法（用该订单号去微信查询该订单是否支付成功{查询支付成功后：改变订单状态、创建订单日志（支付失败也要创建）、增加积分}）
    @RequestMapping("/weChatNotifyUrl")
    public void weChatNotifyUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 支付成功，商户处理后同步返回给微信参数
        PrintWriter writer = response.getWriter();
        try {
            InputStream inStream = request.getInputStream();
            String xmlString = WxPayUtil.nputStream2String(inStream);
            System.out.println("微信回调参数:"+xmlString);
            xmlString = xmlString.replaceAll("</?xml>", "");
            System.out.println(xmlString);
            //匹配一段一段这样的数据   <attach><![CDATA[支付测试]]></attach>
            Pattern pattern = Pattern.compile("<.*?/.*?>");
            Matcher matcher = pattern.matcher(xmlString);
            //配置是否包含<![CDATA[CNY]]> CDATA 包裹的数据
            Pattern pattern2 = Pattern.compile("!.*]");
            Map<String, String> wxReturnMap = new HashMap<>();
            while(matcher.find()) {
                //获取键
                String key = matcher.group().replaceAll(".*/", "");
                key = key.substring(0, key.length() - 1);
                Matcher matcher2 = pattern2.matcher(matcher.group());
                String value = matcher.group().replaceAll("</?.*?>", "");
                //获取值
                if(matcher2.find() && !value.equals("DATA")) {
                    value = matcher2.group().replaceAll("!.*\\[", "");
                    value = value.substring(0, value.length() - 2);
                }
                wxReturnMap.put(key, value);
            }
            String out_trade_no = wxReturnMap.get("out_trade_no");//商户订单号
            String transaction_id = wxReturnMap.get("transaction_id");//微信支付成功返回的微信订单号
//            Member member = memberService.getCurrent();
            OrderPayment orderPayment = orderPaymentService.selectByPrimaryKey(Long.valueOf(out_trade_no.split("_")[0]));//付款单
            orderPayment.setTransactionId(transaction_id);
            orderPaymentService.updateByPrimaryKeySelective(orderPayment);
            Map<String, String> data = new HashMap<String, String>();
            data.put("appid", appid);	//	是	公众账号ID	String(32)	wxd678efh567hg6787	微信支付分配的公众账号ID（企业号corpid即为此appId）
            data.put("mch_id", mch_id);	//	是	商户号	String(32)	1230000109	微信支付分配的商户号
//        data.put("device_info", "");	//	否	设备号	String(32)	1.3467E+13	自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
            data.put("nonce_str", UUIDUtil.getUUID());	//	是	随机字符串	String(32)	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	随机字符串，长度要求在32位以内。推荐随机数生成算法
            data.put("sign_type", "MD5");	//	否	签名类型	String(32)	MD5	签名类型，默认为MD5，支持HMAC-SHA256和MD5。
            data.put("body", "购买软件");	//	是	商品描述	String(128)	腾讯充值中心-QQ会员充值	商品简单描述，该字段请按照规范传递，具体请见参数规定
            System.out.println("商户订单号长度:"+out_trade_no.length());
            data.put("out_trade_no", out_trade_no);	//	是	商户订单号	String(32)	2.01508E+13	商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一。详见商户订单号
            data.put("trade_type", "NATIVE");	//	是	交易类型	String(16)	JSAPI	JSAPI -JSAPI支付NATIVE -Native支付APP -APP支付			说明详见参数规定
            data.put("key",key);
            String sign_xml = XmlUtil.mapToXml(data);
            System.out.println("签名前xml："+sign_xml);
            String sign = WxPayUtil.createSign(data);
            data.put("sign",sign);	//
            data.remove("key");
            String data_xml = XmlUtil.mapToXml(data);
            String return_data_xml = HttpUtil.post("https://api.mch.weixin.qq.com/pay/orderquery",data_xml);
            Map<String,Object> return_Data_Map = XmlUtil.getXmlToMap(return_data_xml);
            if (return_Data_Map.get("return_code").toString().equals("SUCCESS")){
                if (return_Data_Map.get("result_code").toString().equals("SUCCESS")){
                    if (return_Data_Map.get("trade_state").toString().equals("SUCCESS")){
                        String total_amount = new String(return_Data_Map.get("total_fee").toString().getBytes("ISO-8859-1"), "UTF-8");
                        BigDecimal amount = new BigDecimal(total_amount);//订单金额
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("orderPayment",orderPayment);//订单
                        jsonObject.put("amount",amount);//支付金额
                        jsonObject.put("paymentMethodName","微信");//支付方式
//                        jsonObject.put("member",member);//支付方式
                        String retrunData = orderService.notifyUrlService(jsonObject);
                        if (retrunData=="success"){
                            writer.write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
                        }else {
                            System.out.println("程序代码有问题，无需腾讯再次推送!");
                            //推送短信验通知
                            Map<String,String> map = new HashMap<>();
                            Map<String,Object> set_map = SettingUtils.getReadXml(pathUrl) ;
                            map.put("url",String.valueOf(set_map.get("shortMessageUrl")));
                            map.put("accountSid",String.valueOf(set_map.get("accountSid")));
                            map.put("authToken",String.valueOf(set_map.get("authToken")));
                            map.put("phone","13522151165");
                            map.put("param",orderPayment.getOutTradeNo());
                            map.put("templateid","259650");
                            SendCode.getSendCodeMessage(map);
                            writer.write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
                        }
                    }else {
                        writer.write("<xml><return_code><![CDATA[FAIL]]></return_code></xml>");
                    }
                }else{
                    writer.write("<xml><return_code><![CDATA[FAIL]]></return_code></xml>");
                }

            }else {
                writer.write("<xml><return_code><![CDATA[FAIL]]></return_code></xml>");
            }

        } catch (Exception e) {
            System.out.println("程序代码IO流有问题，无需腾讯再次推送!");
            writer.write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
            e.printStackTrace();
        }

    }





}
