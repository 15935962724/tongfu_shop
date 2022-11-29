package com.tongfu.Util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class TxtUtil {


    public static void main(String[] args) {

     System.out.println();

//        List<String> stringList = new ArrayList<>();
//        stringList.add("1哈哈 黑");
//        stringList.add("2嘿嘿 黑");
        String filePath = "D:\\temp\\txt";
        boolean aaa = writeDataHubData("发生地方了时代峻峰", filePath, "ddd");
        System.out.println(aaa);

    }


    /**
     * 写入txt文件
     * @param result  内容
     * @param filePath  路径
     * @param fileName 文件名
     * @return
     */
        public static boolean writeDataHubData(String result,String filePath, String fileName) {
            long start = System.currentTimeMillis();
//            String filePath = "D:\\temp\\txt";
            StringBuilder content = new StringBuilder();
            boolean flag = false;
            BufferedWriter out = null;
            try {
                if (result != null && !result.isEmpty() && StringUtils.isNotEmpty(fileName)) {
                    fileName += ".txt";
                    File pathFile = new File(filePath);
                    if (!pathFile.exists()) {
                        pathFile.mkdirs();
                    }
                    String relFilePath = filePath + File.separator + fileName;
                    File file = new File(relFilePath);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GB2312"));
//                //标题头
//                out.write("curr_time,link_id,travel_time,speed,reliabilitycode,link_len,adcode,time_stamp,state,public_rec_time,ds");
//                out.newLine();


                        out.write(result);
                        out.newLine();

                    flag = true;
//                logger.info("写入文件耗时：*********************************" + (System.currentTimeMillis() - start) + "毫秒");
                    System.out.println("写入文件耗时：*********************************" + (System.currentTimeMillis() - start) + "毫秒");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return flag;
            }
        }
    }


