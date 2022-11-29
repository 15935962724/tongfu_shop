package com.tongfu.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
@Component
public class FileUpload {

//    private static final String FAR_SERVICE_DIR = "http://106.13.123.1:8082/journalismLogo";
    private static SystemPropertiesUtil systemPropertiesUtil;
    @Autowired
    public void init(SystemPropertiesUtil systemPropertiesConfig) {
        FileUpload.systemPropertiesUtil = systemPropertiesConfig;
    }
    public static String upload (MultipartFile file, String path){

        if (file.getSize()<=0){
            return null;
        }

        // 生成新的文件名
        //String realPath = path + "/" + FileNameUtils.getFileName(fileName);

        //    获取静态资源路径
        String staticPath= systemPropertiesUtil.getFileWebPath()+path;//ClassUtils.getDefaultClassLoader().getResource("static").getPath()+path;
        File files = new File(staticPath);
        if (!files.isDirectory()) {
            files.mkdirs();
        }

        // 获取文件名
        String fileName = file.getOriginalFilename();
        System.out.println("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("上传的后缀名为：" + suffixName);
        fileName= Long.toString(System.currentTimeMillis())+suffixName;//重新命名图片，变成随机的名字
        //使用原文件名
        String realPath = staticPath  + fileName;

        File dest = new File(realPath);

        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        try {
            //保存文件
            file.transferTo(dest);
            return path+fileName;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }

    public static void uploadToFarService(Map files) {
        try {
            String BOUNDARY = "---------7d4a6d158c9"; // 定义数据分隔线
//            URL url = new URL(FAR_SERVICE_DIR);
            String pahtUrl = (String) files.get("pathUrl");
            URL url = new URL(pahtUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);

            OutputStream out = new DataOutputStream(conn.getOutputStream());
            byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线
//            Iterator iter = files.entrySet().iterator();
//            int i=0;
//            while (iter.hasNext()) {
//                i++;
//                Map.Entry entry = (Map.Entry) iter.next();
//                String key = (String) entry.getKey();
//                InputStream val = (InputStream) entry.getValue();
//                String fname = key;
                InputStream val = (InputStream) files.get("inputStream");
                String fname = (String) files.get("fileName");
                File file = new File(fname);
                StringBuilder sb = new StringBuilder();
                sb.append("--");
                sb.append(BOUNDARY);
                sb.append("\r\n");
                sb.append("Content-Disposition: form-data;name=\"file" + 1
                        + "\";filename=\"" + fname + "\"\r\n");
                sb.append("Content-Type:application/octet-stream\r\n\r\n");

                byte[] data = sb.toString().getBytes();
                out.write(data);
                DataInputStream in = new DataInputStream(val);
                int bytes = 0;
                byte[] bufferOut = new byte[1024];
                while ((bytes = in.read(bufferOut)) != -1) {
                    out.write(bufferOut, 0, bytes);
                }
                out.write("\r\n".getBytes()); // 多个文件时，二个文件之间加入这个
                in.close();
//            }
            out.write(end_data);
            out.flush();
            out.close();

            // 定义BufferedReader输入流来读取URL的响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println(pahtUrl+"/"+fname);

        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
    }




}
