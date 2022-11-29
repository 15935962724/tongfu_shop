
package com.tongfu.Util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Utils - 微信操作工具类
 * add by sl on 2015-12-04 09:42:34
 * @author  XIAER Team
 * @version 1.0
 */
public final class WeiXinUtils {
	
	private String code;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取jsontoken
	 * @return
	 */
	public static String getAccess_token(JSONObject jsonObjects) {
	       String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ jsonObjects.getString("appId")+"&secret=" + jsonObjects.getString("secret") ;
	       JSONObject jsonobject = JSON.parseObject(HttpUtil.getInvoke(url));
	       String jsontoken = jsonobject.getString("access_token");
	       return jsontoken;
	 }

	/**
	 * 长链接转成短链接 提高扫码速度和成功率
	 * @param
	 * @return
	 */
    public static String shortURL( JSONObject jsonObjects ) {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN",getAccess_token(jsonObjects));
        String jsonMsg = "{\"action\":\"long2short\",\"long_url\":\"%s\"}";
        String.format(jsonMsg, jsonObjects.getString("longURL"));
        JSONObject jsonobject = JSON.parseObject(HttpUtil.post(requestUrl, String.format(jsonMsg, jsonObjects.getString("longURL"))));
        return jsonobject.getString("short_url");
    }


	/**
	 *  获取网页授权accessToken json字符串
	 * @param jsonObject
	 * @return
	 */
	public String getAccessTokenJson(JSONObject jsonObject){
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ jsonObject.getString("appId") + "&secret=" + jsonObject.getString("secret") +"&code=" + jsonObject.getString("code") + "&grant_type=authorization_code";
		String return_data = HttpUtil.getInvoke(url);
		System.out.println(return_data);
		return return_data;
	}

	/**
	 *  获取openid
	 * @param jsonObject
	 * @return
	 */
	public String getOpenid(JSONObject jsonObject){
		JSONObject jsontoken = JSON.parseObject(getAccessTokenJson(jsonObject));
		return jsontoken.getString("openid");
	}

	/**
	 * 获取用户信息
	 * @param jsonObject
	 * @return
	 * @throws IOException
	 */
	public String getUserInfo(JSONObject jsonObject ) throws IOException {
		JSONObject jsontoken = JSON.parseObject(getAccessTokenJson(jsonObject));
		try {
			String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+jsontoken.getString("access_token")+"&openid="+ jsontoken.getString("openid") +"&lang=zh_CN";
			String newstr = HttpUtil.getInvoke(url);
			String userinfo = newstr.replace("\"", "'");
			return 	userinfo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}


	/**
	 * 获取jsapi_ticket
	 * @param jsonObject
	 * @return
	 * @throws IOException
	 */
	public String getJsapiTicket(JSONObject jsonObject) throws IOException {
//		JSONObject jsontoken = JSON.parseObject(getAccessTokenJson(jsonObject));
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ jsonObject.getString("appId") +
				"&secret=" + jsonObject.getString("secret");
		System.out.println("getJsapiTicketUrl:"+url);
		jsonObject = JSON.parseObject(HttpUtil.getInvoke(url));
		System.out.println("getJsapiTicketUrlData:"+jsonObject);
		String accessToken = jsonObject.getString("access_token");

		url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
				+ accessToken + "&type=jsapi";
		jsonObject = JSON.parseObject(HttpUtil.getInvoke(url));
		System.out.println("ticket:"+jsonObject);
		String jsapi_ticket = jsonObject.getString("ticket");
		return jsapi_ticket;
	}



	/**
	 * 获取微信签名signature
	 * 	 * @param 随机数  随机字符串 url
	 * @return
	 * @throws IOException
	 */
	public Map<String, Object> getSignature(JSONObject jsonObject ) throws IOException {
		Map<String, Object> wxMap = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 调用获取随机字符串的方法
		String nonce_str = UUID.randomUUID().toString();
		// 调用获取随机数的方法
		String  timestamp = Long.toString(System.currentTimeMillis() / 1000);
		// 获取jsapi_ticket
		String jsapi_ticket = getJsapiTicket(jsonObject);
		// 定义签名参数
		String signature = "";
		// 拼接字符串
		String string1 = "jsapi_ticket=" + jsapi_ticket +
						  "&noncestr="+ nonce_str +
						  "&timestamp=" + timestamp +
				          "&url=" + jsonObject.getString("url");
		System.out.println("string1:"+string1);
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			// 用算法获取签名
			signature = HttpUtil.byteToHex(crypt.digest());
			System.out.println("访问地址:" + jsonObject.getString("url") +"  时间:" + sdf.format(new Date()));
		} catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException:"+e.getMessage());
		    e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException:"+e.getMessage());
			e.printStackTrace();
		}
		wxMap.put("nonceStr", nonce_str);
		wxMap.put("timestamp", timestamp);
		wxMap.put("signature", signature);
		wxMap.put("appId", jsonObject.getString("appId"));
		return wxMap;
	}





	
}