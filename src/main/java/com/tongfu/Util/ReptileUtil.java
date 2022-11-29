package com.tongfu.Util;

import com.tongfu.entity.Area;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReptileUtil {


    public static void main(String[] args) throws IOException {
//        String url= "https://www.yixue.com/全国医院列表";
        String [] areas = {"苏州市","无锡市","淮安市","南京市","盐城市","徐州市","宿迁市","泰州市","南通市","常州市","连云港市","扬州市","镇江市","广州市","深圳市","惠州市","佛山市","梅州市","肇庆市","韶关市","揭阳市","汕头市","东莞市","湛江市","中山市","清远市","潮州市","茂名市","珠海市","江门市","阳江市","河源市","汕尾市","云浮市","青岛市","济南市","济宁市","烟台市","潍坊市","淄博市","临沂市","菏泽市","枣庄市","泰安市","聊城市","东营市","威海市","德州市","滨州市","日照市","莱芜市","沈阳市","大连市","锦州市","鞍山市","抚顺市","丹东市","朝阳市","营口市","葫芦岛市","阜新市","辽阳市","铁岭市","本溪市","盘锦市","石家庄市","唐山市","保定市","邯郸市","邢台市","沧州市","张家口市","承德市","廊坊市","衡水市","秦皇岛市","郑州市","洛阳市","南阳市","新乡市","焦作市","周口市","平顶山市","开封市","信阳市","商丘市","安阳市","许昌市","三门峡市","驻马店市","漯河市","濮阳市","鹤壁市","济源市","成都市","绵阳市","乐山市","南充市","宜宾市","甘孜藏族自治州","广元市","凉山彝族自治州","达州市","自贡市","德阳市","攀枝花市","泸州市","雅安市","内江市","阿坝藏族羌族自治州","遂宁市","巴中市","资阳市","广安市","眉山市哈尔滨市","齐齐哈尔市","牡丹江市","绥化市","大庆市","佳木斯市","鸡西市","伊春市","黑河市","双鸭山市","鹤岗市","大兴安岭地区","七台河市","太原市","运城市","临汾市","大同市","忻州市","晋中市","长治市","吕梁地区","晋城市","阳泉市","朔州市","武汉市","襄樊市","宜昌市","荆州市","黄石市","黄冈市","孝感市","十堰市","咸宁市","恩施土家族苗族自治州","荆门市","随州市","鄂州市","天门市","仙桃市","潜江市","神农架林区","长沙市","衡阳市","湘潭市","株洲市","永州市","邵阳市","岳阳市","郴州市","怀化市","益阳市","常德市","娄底市","湘西土家族苗族自治州","张家界市","西安市","渭南市","咸阳市","宝鸡市","汉中市","延安市","榆林市","安康市","商洛市","铜川市","杭州市","宁波市","温州市","金华市","台州市","绍兴市","嘉兴市","衢州市","丽水市","湖州市","舟山市昆明市","红河哈尼族彝族自治州","大理白族自治州","曲靖市","昭通市","玉溪市","楚雄彝族自治州","思茅地区","文山壮族苗族自治州","临沧地区","保山市","德宏傣族景颇族自治州","丽江地区","西双版纳傣族自治州","怒江傈僳族自治州","迪庆藏族自治州","长春市","延边朝鲜族自治州","吉林市","通化市","四平市","白城市","辽源市","白山市","松原市","合肥市","淮南市","安庆市","阜阳市","宣城市","芜湖市","蚌埠市","六安市","滁州市","淮北市","宿州市","黄山市","巢湖市","铜陵市","马鞍山市","亳州市","池州市","南宁市","柳州市","桂林市","百色市","河池市","玉林市","梧州市","崇左市","贵港市","来宾市","北海市","钦州市","贺州市","防城港市","南昌市","赣州市","上饶市","吉安市","九江市","宜春市","抚州市","萍乡市","景德镇市","鹰潭市","新余市","福州市","泉州市","南平市","厦门市","漳州市","三明市","龙岩市","莆田市","宁德市","乌鲁木齐市","喀什地区","伊犁哈萨克自治州","巴音郭楞蒙古自治州","昌吉回族自治州","石河子市","塔城地区","和田地区","阿克苏地区","阿勒泰地区","哈密地区","吐鲁番地区","博尔塔拉蒙古自治州","克拉玛依市","克孜勒苏柯尔克孜自治州","阿拉尔市","图木舒克市","五家渠市","呼和浩特市","呼伦贝尔市","赤峰市","通辽市","鄂尔多斯市","乌兰察布市","锡林郭勒盟","巴彦淖尔市","乌海市","兴安盟","阿拉善盟","包头市","兰州市","天水市","陇南地区","平凉市","定西地区","庆阳市","武威市","酒泉市","白银市","张掖市","临夏回族自治州","甘南藏族自治州","金昌市","嘉峪关市","贵阳市","遵义市","毕节地区","黔南布依族苗族自治州","黔东南苗族侗族自治州","安顺市","六盘水市","铜仁地区","黔西南布依族苗族自治州","海口市","儋州市","三亚市","澄迈县","文昌市","琼海市","万宁市","屯昌县","琼中黎族苗族自治县","昌江黎族自治县","乐东黎族自治县","保亭黎族苗族自治县","五指山市","东方市","临高县","定安县","白沙黎族自治县","陵水黎族自治县","琼山市","西宁市","海西蒙古族藏族自治州","海东地区","玉树藏族自治州","果洛藏族自治州","黄南藏族自治州","海南藏族自治州","海北藏族自治州","银川市","吴忠市","石嘴山市","固原市","日喀则地区","那曲地区","拉萨市","山南地区","阿里地区","昌都地区","林芝地区"};


                //        Area area1 = new Area();
//        area1.setName("綦江县");
//        area1.setId(2250l);
//        Area area2 = new Area();
//        area2.setName("大足县");
//        area2.setId(2251l);
//        Area area3 = new Area();
//        area3.setName("江津市");
//        area3.setId(2256l);
//        Area area4 = new Area();
//        area4.setName("合川市");
//        area4.setId(2257l);
//        Area area5 = new Area();
//        area5.setName("永川市");
//        area5.setId(2258l);
//        Area area6 = new Area();
//        area6.setName("琼山市");
//        area6.setId(2217l);

//        openUrl(area1,"重庆市");
//
//        openUrl(area2,"重庆市");

//        openUrl(area3,"");
//
//        openUrl(area4,"");
//
//        openUrl(area5,"");

//        openUrl(area6,"");

        openUrlAtitle();
//        openUrl(areas);

    }


    private static void openUrlAtitle() throws IOException {

        String url = "https://www.yixue.com/全国医院列表";
        Integer count = 0;
        Document doc = getDocument(url);
        Elements select = doc.select("p");
        for (Element element : select) {
            String html = element.html();
            Document parse = Jsoup.parse(html);
            Elements a1 = parse.select("a");
            if (a1.size()>2){
                for (Element element1 : a1) {
                    String title = element1.html();
                    if (title.contains("一级")||title.contains("二级")||title.contains("三级")||title.contains("医院")||title.contains("科")||title.contains("中心")||title.contains("整形美容")) {

                    }else {
//                        System.out.println(element1.html());
//                        Document parse1 = Jsoup.parse(String.valueOf(element1));
                        String title1 = element1.attr("title");
                        if (!title1.contains("页面不存在")){
//                            System.out.println(title1);
//                            System.out.println(element1);
//                            System.out.println(element1.html());
                            String href = element1.attr("href");
                            String url1 = "https://www.yixue.com"+href;
                            Document doc1 = getDocument(url1);
                            Elements ul = doc1.select(".mw-parser-output").select("ul").eq(0).select("li");
                            for (Element element2 : ul) {
                                String ahtml = element2.html();
                                Document parse1 = Jsoup.parse(ahtml);
                                Elements a = parse1.select("a");


                                String aContent = a.html();
//                                System.out.println(a.html());
                                if (aContent.contains("甲")||aContent.contains("乙")||aContent.contains("丙")){

                                }else {
                                    String hospital = a.html();
                                    String diqu = hospital.substring(0, hospital.indexOf("医院"));
                                    System.out.println("医院地区:"+diqu);
//                                    System.out.println("医院名称:"+hospital);
                                    String href1 = a.attr("href");
                                    System.out.println("医院链接:"+href1);
                                    String url2 = "https://www.yixue.com"+href1;
                                    Document document = getDocument(url2);
                                    Elements ul1 = document.select(".mw-parser-output").select("ul").eq(1);
                                    for (Element element3 : ul1) {
                                        String html1 = element3.html();
                                        Document parse2 = Jsoup.parse(html1);
                                        Elements li = parse2.select("li");
                                        for (Element element4 : li) {
                                            String html2 = element4.html();
                                            if (html2.contains("ul")){
                                                Document parse3 = Jsoup.parse(html2);
                                                String hospitalName = parse3.select("a").eq(0).html();
                                                Elements ul2 = parse3.select("ul");
                                                String address = ul2.select("li").eq(0).html();
                                                address = address.substring(address.indexOf("：")+1);
                                                String phone = ul2.select("li").eq(1).html();
                                                if (phone.contains("联系电话")){
                                                    phone = phone.substring(phone.indexOf("：")+1);
                                                }else {
                                                    phone = "";
                                                }


                                            }
                                        }
                                    }
                                }

                            }
                            count++;
                        }
                    }
                }
            }
        }
        System.out.println("count+"+count);
    }





    private static void openUrl(String url) throws IOException {

        Document doc = getDocument(url);
        Elements select = doc.select(".mw-parser-output").select("ul").eq(1);
        for (Element element : select) {
            String html = element.html();
            Document parse = Jsoup.parse(html);
            Elements a1 = parse.select("li");
            for (Element element1 : a1) {
                String html1 = element1.html();
                if (html1.contains("ul")){
//                    System.out.println("li内容:"+html1);
                    Document parse1 = Jsoup.parse(html1);
                    String hospitalName = parse1.select("a").eq(0).html();
//                        System.out.println("医院名称:"+hospitalName);
                    Elements ul = parse1.select("ul");
                    String address = ul.select("li").eq(0).html();
                    address = address.substring(address.indexOf("：")+1);
//                        System.out.println("医院地址:"+address);
                    String phone = ul.select("li").eq(1).html();
                    if (phone.contains("联系电话")){
                        phone = phone.substring(phone.indexOf("：")+1);
                    }else {
                        phone = "";
                    }
//                        System.out.println("医院电话:"+phone);
                    String content = "INSERT INTO `hospitals` (`name`, `address`, `phone`, `area_id`) VALUES('"+hospitalName+"','"+address+"','"+phone+"','"+(0)+"');";
                    System.out.println(content);
//                    outTxt(content);

                    System.out.println("------------------------------------------------------------------");
                }

            }
        }
    }






    private static void openUrl(Area area, String city) throws IOException {

            String url = "https://www.yixue.com/"+city+area.getName()+"医院列表";

            Document doc = getDocument(url);
            Elements select = doc.select(".mw-parser-output").select("ul").eq(3);
            for (Element element : select) {
                String html = element.html();
                Document parse = Jsoup.parse(html);
                Elements a1 = parse.select("li");
                for (Element element1 : a1) {
                    String html1 = element1.html();
                    if (html1.contains("ul")){
//                    System.out.println("li内容:"+html1);
                        Document parse1 = Jsoup.parse(html1);
                        String hospitalName = parse1.select("a").eq(0).html();
//                        System.out.println("医院名称:"+hospitalName);
                        Elements ul = parse1.select("ul");
                        String address = ul.select("li").eq(0).html();
                        address = address.substring(address.indexOf("：")+1);
//                        System.out.println("医院地址:"+address);
                        String phone = ul.select("li").eq(1).html();
                        if (phone.contains("联系电话")){
                            phone = phone.substring(phone.indexOf("：")+1);
                        }else {
                            phone = "";
                        }
//                        System.out.println("医院电话:"+phone);
                        String content = "INSERT INTO `hospitals` (`name`, `address`, `phone`, `area_id`) VALUES('"+hospitalName+"','"+address+"','"+phone+"','"+(area.getId())+"');";
                        System.out.println(content);
                        outTxt(content);

                        System.out.println("------------------------------------------------------------------");
                    }

                }
            }
        }



    /**
     * 写入txt
     */
    public static void outTxt(String content) throws IOException {
        File f = new File("D:/hospital.txt");
        if (!f.exists()) {
            try {
                f.createNewFile();// 不存在则创建
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedWriter output = null;//true,则追加写入text文本
        try {
            output = new BufferedWriter(new FileWriter(f,true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        output.write(content);
        output.write("\r\n");//换行
        output.flush();
        output.close();

    }



    public static void reptileHospital() {

            String url= "https://www.yixue.com/全国医院列表";

            String content = getContent(url);

            System.out.println("call " + url + " , content=" + content);

        }


    public static String getContent(String url) {
        // okHttpClient 实例
        OkHttpClient okHttpClient = new OkHttpClient();
        // 定义一个request
        Request request = new Request.Builder().url(url).build();
        // 使用client去请求
        Call call = okHttpClient.newCall(request);
        // 返回结果字符串
        String result = null;
        try {
            // 获得返回结果
            result = call.execute().body().string();
        } catch (IOException e) {
            // 抓取异常
            System.out.println("request " + url + " error . ");
            e.printStackTrace();
        }
        return result;
    }



    public static Document getDocument (String url){
        try {
            //通过Jsoup获取资源，5000是设置连接超时时间，单位ms
            return Jsoup.connect(url).timeout(50000).get();
        } catch (IOException e) {
            System.out.println(url);
            e.printStackTrace();
        }
        return null;
    }







}


