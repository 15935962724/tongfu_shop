package com.tongfu.Util;

import com.alibaba.fastjson.JSONObject;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.google.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;

import java.io.*;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PhoneUtil {
    private static PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    private static PhoneNumberOfflineGeocoder geocoder = PhoneNumberOfflineGeocoder.getInstance();


    /**
     * 根据国家代码和手机号 判断手机号是否有效
     * @param phoneNumber 手机号码
     * @param countryCode 国号(区号)
     * @return true / false
     */
    public static boolean checkPhoneNumber(long phoneNumber, int countryCode) {
        PhoneNumber pn = new PhoneNumber();
        pn.setCountryCode(countryCode);
        pn.setNationalNumber(phoneNumber);
        return phoneNumberUtil.isValidNumber(pn);
    }

    private static Pattern phoneReg = Pattern.compile("\\+(9[976]\\d|8[987530]\\d|6[987]\\d|5[90]\\d|42\\d|3[875]\\d|2[98654321]\\d|9[8543210]|8[6421]|6[6543210]|5[87654321]|4[987654310]|3[9643210]|2[70]|7|1)\\d{1,14}$");

    public static JSONObject getPhoneNumberInfo(String phoneNumber) throws Exception {
        // 正则校验
        Matcher matcher = phoneReg.matcher(phoneNumber);
        if (!matcher.find()) {
            throw new Exception("The phone number maybe like:" + "+[National Number][Phone number], but got " + phoneNumber);
        }

        Phonenumber.PhoneNumber referencePhonenumber;
        try {
            String language = "CN";
            referencePhonenumber = phoneNumberUtil.parse(phoneNumber, language);
        } catch (NumberParseException e) {
            throw new Exception(e.getMessage());
        }
        String regionCodeForNumber = phoneNumberUtil.getRegionCodeForNumber(referencePhonenumber);

        if (regionCodeForNumber == null) {
            throw new Exception("Missing region code by phone number " + phoneNumber);
        }

        boolean checkSuccess = PhoneUtil.checkPhoneNumber(referencePhonenumber.getNationalNumber(), referencePhonenumber.getCountryCode());
        if (!checkSuccess) {
            throw new Exception("Not an active number:" + phoneNumber);
        }

        String description = geocoder.getDescriptionForNumber(referencePhonenumber, Locale.CHINA);

        int countryCode = referencePhonenumber.getCountryCode();
        long nationalNumber = referencePhonenumber.getNationalNumber();
        JSONObject resultObject = new JSONObject();
        // 区域编码 Locale : HK, US, CN ...
        resultObject.put("regionCode", regionCodeForNumber);
        // 国号: 86, 1, 852 ... @link: https://blog.csdn.net/wzygis/article/details/45073327
        resultObject.put("countryCode", countryCode);
        // 去掉+号 和 国号/区号 后的实际号码
        resultObject.put("nationalNumber", nationalNumber);
        // 所在地区描述信息
        resultObject.put("description", description);
        // 去掉+号后的号码 (用于阿里云发送短信)
        resultObject.put("number", String.valueOf(countryCode) + nationalNumber);
        resultObject.put("fullNumber", phoneNumber);

        return resultObject;

    }

    public static String getChineseCharacter() throws Exception{

        String str = null;          //保存结果

        int highPos,lowPOs;      //高位、低位

        Random random = new Random();      //随机数生成器

        highPos = 176 + Math.abs(random.nextInt(39));        //计算高位数

        int lowPos = 161 + Math.abs(random.nextInt(93));      //计算低位数

        byte[] b = new byte[2];      //转化为B类型

        b[0] = (new Integer(highPos)).byteValue();            //高字节

        b[1] = (new Integer(lowPos)).byteValue();         //低字节

        str = new String(b, "GBK");

        return str;

    }

    public static String getPhones() throws Exception {
        Integer count = 0;
        FileWriter fw = null;
        try {
//如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f=new File("E:\\1.txt");
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        for (int i = 0; i <= 999999; i++) {
            String productCount=String.format("%06d%n", i);
            String phone = "+86155"+productCount+"24";

           phone = phone.replaceAll("\r|\n", "");
//            System.out.println("手机号为："+phone);

            String phoneData = getPhoneNumberInfo(phone).getString("description");
            if (phoneData.contains("浙江")){
                System.out.println("开始写入");
                count++;
                pw.println("BEGIN:VCARD");
                pw.println("VERSION:3.0");
                String xiang = getChineseCharacter();
                pw.println("N:"+xiang+";克斯;;;");
                pw.println("FN:"+xiang+"克斯");
                String ount= DateUtil.getDatetoString("yyyyMMddHHmmssSSS",new Date());
                pw.println("UID:"+ount);
                pw.println("TEL;TYPE=mobile:"+phone.substring(3,phone.length()));
                pw.println("END:VCARD");
                pw.println("\n");
                pw.flush();
                System.out.println("xier");
            }

        }
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(count);
        return null;
    }

    public static void write(String path)  throws IOException {
        FileWriter fw = null;
        try {
//如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f=new File("E:\\1.txt");
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println("追加内容3");
        pw.println("\n");
        pw.println("追加内容1");
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws Exception {
        // The phone number maybe like:+[National Number][Phone number], but got +86155xxxx331
//        String phoneData = PhoneUtil.getPhoneNumberInfo("+8615935962724").getString("description");
////        System.out.println(PhoneUtil.getPhoneNumberInfo("+8615935962724").toJSONString());
//        System.out.println(phoneData);

        getPhones();
//        write("E:\\1.txt"); //运行主方法
    }
}
