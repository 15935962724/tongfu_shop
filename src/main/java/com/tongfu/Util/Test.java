package com.tongfu.Util;

import com.alibaba.fastjson.JSONObject;

public class Test {

    public static  void test(){
        Integer [] years = {2019,2020,2021};
        for (int i = 0; i < years.length; i++) {
        Integer year = years[i];
            for (int j = 1; j <= 12; j++) {

                        System.out.println("1/"+j+"/"+year +" "+" @  登录,未知,Login succeeded for user 'sa'. Connection: non-trusted. [客户端: 127.0.0.1] @ 刘卓起@王双瑞 @ 1/"+j+"/"+year );

            }

        }

    }

    public static  Integer days(Integer year,Integer month){
        if (month==1||month==3||month==5||month==7||month==8||month==10||month==12){
        return 31;
        }
        if (month==4||month==6||month==9||month==11){
            return 30;
        }

        if (month==2){
            if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0){ //平闰年判断算法
                return 29;
            }
            else{
                return 28;
            }
        }

        return 0;
    }

    //微信appid
    private static String appId = "wxc4f84cdd9ad8e0c8";
    private static String secret = "a304a798f950cc5206019aee1f21dc1b";

//    微信自定义菜单查询
    public static String getWeixinMenu(){
        JSONObject jsonObjects = new JSONObject();
        jsonObjects.put("appId",appId);
        jsonObjects.put("secret",secret);
        String token = WeiXinUtils.getAccess_token(jsonObjects);
        String getWeixinMenuUrl = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token="+token;
        String invoke = HttpUtil.getInvoke(getWeixinMenuUrl);
        System.out.println(invoke);
        return invoke;
    }

    /**
     * 创建微信菜单
     * @return
     */
    public static String getNewMenu(){
        JSONObject jsonObjects = new JSONObject();
        jsonObjects.put("appId",appId);
        jsonObjects.put("secret",secret);
        String token = WeiXinUtils.getAccess_token(jsonObjects);
        String menuUrl = "http://www.hailiekeji.cn/";
//        jsonObjects.put("rul","");
//        String data = "{id:24,name:\"分析软件\",url:"/pages/shop/classification/"}";
//        {id:24,name:\"分析软件\",url:\"/pages/shop/classification\"}
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc4f84cdd9ad8e0c8&redirect_uri=https://www.hailiekeji.cn/weixin/login?id=24&name=分析软件&url=/pages/shop/classification&response_type=code&scope=snsapi_userinfo";

        String getWeixinMenuUrl = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token="+token;
        String invoke = HttpUtil.getInvoke(getWeixinMenuUrl);
        System.out.println(invoke);
        return invoke;
    }

    public static String getExcle() {


        return null;
    }

    public static void main(String[] args) {


//        test();
//        getWeixinMenu();

//        String realName = "封雷";
//        String card = "230122199409161019";
//        String url="https://v.apistore.cn/api/a1";
//        String param="key=8163022b92c28d82cb3d973f3042eaf4&cardNo="+card+"&realName="+realName+"&information=";
//        String returnStr = HttpUtil.post(url, param);
//        System.out.println(returnStr);

        String realName = "封雷";
        String card = "230122199409161019";
        String url="http://api.shuzike.com/lifeservice/check/6?UserAuthKey=79421e02-7cd2-455f-b391-15f6a539f602475";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",realName);
        jsonObject.put("idcard",card);
        String returnStr = HttpUtil.post(url, jsonObject.toString());
        System.out.println(returnStr);




    }

}
