package com.tongfu.controller.weixin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.tongfu.Util.*;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller("weixinpayment")
@RequestMapping("/weixinPayment")
public class PaymentController {

    @Autowired
    private OrderService orderService;//订单

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private MemberService memberService;//用户

    @Value("${path-url}")
    private String pathUrl;

    //	微信appid
    @Value("${wxAppid}")
    private String appid;

    //	商户号
    @Value("${mch_id}")
    private String mch_id;

    //	商户key
    @Value("${key}")
    private String key;


    //微信回调方法（用该订单号去微信查询该订单是否支付成功{查询支付成功后：改变订单状态、创建订单日志（支付失败也要创建）、增加积分}）
    @RequestMapping("/weChatNotifyUrl")
    public void weChatNotifyUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 支付成功，商户处理后同步返回给微信参数
        PrintWriter writer = response.getWriter();
        try {
            InputStream inStream = request.getInputStream();
            String xml = WxPayUtil.nputStream2String(inStream);
            System.out.println("微信回调参数:"+xml);
            //将微信发的xml转map
            Map<String, Object> notifyMap = XmlUtil.getXmlToMap(xml);
            Long orderId = (Long) notifyMap.get("orderId");
            Order order = orderService.selectById(orderId);
            String out_trade_no = (String.valueOf(order.getCreateDate().getTime())+String.valueOf(order.getModifyDate().getTime())+String.format("%06d%n", orderId)).trim();
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
            System.out.println("签名钱xml："+sign_xml);
            String sign = WxPayUtil.createSign(data);
            data.put("sign",sign);	//
            data.remove("key");
            String data_xml = XmlUtil.mapToXml(data);
            String return_data_xml = HttpUtil.post("https://api.mch.weixin.qq.com/pay/orderquery",data_xml);
            Map<String,Object> return_Data_Map = XmlUtil.getXmlToMap(return_data_xml);
            if (return_Data_Map.get("return_code").toString().equals("SUCCESS")){
                if (return_Data_Map.get("result_code").toString().equals("SUCCESS")){
                    String total_amount = new String(return_Data_Map.get("total_fee").toString().getBytes("ISO-8859-1"), "UTF-8");
                    BigDecimal amount = new BigDecimal(total_amount);//订单金额
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("order",order);//订单
                    jsonObject.put("amount",amount);//支付金额
                    jsonObject.put("paymentMethodName","微信");//支付方式
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
                        map.put("param",order.getSn());
                        map.put("templateid","259650");
                        SendCode.getSendCodeMessage(map);
                        writer.write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
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


    //微信支付
    @RequestMapping("/wxPay")
    @ResponseBody
    public String wxPay(@RequestBody JSONObject jsonObject) throws Exception {
        Order order = orderService.selectById(jsonObject.getLong("id"));

        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", appid);	//	是	公众账号ID	String(32)	wxd678efh567hg6787	微信支付分配的公众账号ID（企业号corpid即为此appId）
        data.put("mch_id", mch_id);	//	是	商户号	String(32)	1230000109	微信支付分配的商户号
//        data.put("device_info", "");	//	否	设备号	String(32)	1.3467E+13	自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
        data.put("nonce_str", UUIDUtil.getUUID());	//	是	随机字符串	String(32)	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	随机字符串，长度要求在32位以内。推荐随机数生成算法
        data.put("sign_type", "MD5");	//	否	签名类型	String(32)	MD5	签名类型，默认为MD5，支持HMAC-SHA256和MD5。
        data.put("body", "surgi-plan---购买软件");	//	是	商品描述	String(128)	腾讯充值中心-QQ会员充值	商品简单描述，该字段请按照规范传递，具体请见参数规定
        List<OrderItem> orderItems = orderItemService.selectByOrderid(order.getId());
        JSONObject jsonObjectDetail = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (OrderItem orderItem : orderItems) {
            JSONObject object = new JSONObject();
            object.put("goods_id",orderItem.getProduct());
            object.put("goods_name",orderItem.getName());
            object.put("quantity",orderItem.getQuantity());
            object.put("price",orderItem.getPrice().multiply(new BigDecimal(100)).intValue());
            jsonArray.add(object);
        }
        jsonObjectDetail.put("goods_detail",jsonArray);
        data.put("detail", jsonObjectDetail.toJSONString());	//	否	商品详情	String(6000)		商品详细描述，对于使用单品优惠的商户，该字段必须按照规范上传，详见“单品优惠参数说明”
//		data.put("attach", "");	//	否	附加数据	String(127)	深圳分店	附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。

//		商户订单号为 订单编号与订单id 拼接

        String out_trade_no = order.getSn()+String.format("%015d%n",order.getId());
        System.out.println("商户订单号长度:"+out_trade_no.length());
        data.put("out_trade_no", out_trade_no);	//	是	商户订单号	String(32)	2.01508E+13	商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一。详见商户订单号
        data.put("fee_type", "CNY");	//	否	标价币种	String(16)	CNY	符合ISO 4217标准的三位字母代码，默认人民币：CNY，详细列表请参见货币类型
//		价格
        Integer total_fee = orderService.getTotalAmount (order.getId()).multiply(new BigDecimal(100)).intValue();//转换成分 并转int
        data.put("total_fee", String.valueOf(total_fee));	//	是	标价金额	Int	88	订单总金额，单位为分，详见支付金额
        InetAddress ip4 = Inet4Address.getLocalHost();
        System.out.println(ip4.getHostAddress());
        data.put("spbill_create_ip", ip4.getHostAddress());	//	是	终端IP	String(64)	123.12.12.123	支持IPV4和IPV6两种格式的IP地址。用户的客户端IP
//		data.put("time_start", "");	//	否	交易起始时间	String(14)	2.00912E+13	订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
//		data.put("time_expire", "");	//	否	交易结束时间	String(14)	2.00912E+13	订time_expire只能第一次下单传值，不允许二次修改，二次修改系统将报错。如用户支付失败后，需再次支付，需更换原订单号重新下单。建议：最短失效时间间隔大于1分钟
//		data.put("goods_tag", "");	//	否	订单优惠标记	String(32)	WXG	订单优惠标记，使用代金券或立减优惠功能时需要的参数，说明详见代金券或立减优惠
        data.put("notify_url", "/weixinPayment/notifyUrl");	//	是	通知地址	String(256)	http://www.weixin.qq.com/wxpay/pay.php	异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
        data.put("trade_type", "MWEB");	//	是	交易类型	String(16)	JSAPI	JSAPI -JSAPI支付NATIVE -Native支付APP -APP支付			说明详见参数规定
//		data.put("product_id", "");	//	否	商品ID	String(32)	1.22354E+22	trade_type=NATIVE时，此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
//		data.put("limit_pay", "");	//	否	指定支付方式	String(32)	no_credit	上传此参数no_credit--可限制用户不能使用信用卡支付
        data.put("key",key);
        String sign = WxPayUtil.createSign(data);
        data.put("sign",sign);	//
        data.remove("key");
        String data_xml = XmlUtil.mapToXml(data);
        String return_data_xml = HttpUtil.post("https://api.mch.weixin.qq.com/pay/unifiedorder",data_xml);
        System.out.println("统一下单结果:"+return_data_xml);

        Map<String,Object> return_Data_Map = XmlUtil.getXmlToMap(return_data_xml);
        JSONObject returnJson = new JSONObject();
        returnJson.put("data",data);
        returnJson.put("dataXml",return_data_xml);

        if (return_Data_Map.get("return_code").toString().equals("SUCCESS")){
            if (return_Data_Map.get("result_code").toString().equals("SUCCESS")){
                return ResultUtil.success(returnJson.toString());
            }else{
              return ResultUtil.error(String.valueOf(return_Data_Map.get("err_code"))+String.valueOf(return_Data_Map.get("err_code_des")));
            }

        }else {
            return ResultUtil.error(String.valueOf(return_Data_Map.get("return_msg")));
        }
    }


}
