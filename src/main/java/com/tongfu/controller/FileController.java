package com.tongfu.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.Util.ResultUtil;
import com.tongfu.entity.Company;
import com.tongfu.entity.Media;
import com.tongfu.entity.ProductCategory;
import com.tongfu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/file")
public class FileController {

    @Value("${path-url}")
    String pathurl;

    @Autowired
    CompanyService companyService;//公司

    @Autowired
    ProductService productService;//产品

    @Autowired
    ProductCategoryService pcService;//产品类别

    @Autowired
    JournalismService journalismService;//新闻

    @Autowired
    MediaService mediaService;//媒体


    @PostMapping("/read")
    @ResponseBody
    public String read(String jsonObject,@RequestParam("codeFile") MultipartFile codeFile ) throws IOException {
        System.out.println("----------------------------------");

        System.out.println(jsonObject+">>>"+codeFile.getSize()+">>>>"+codeFile.getOriginalFilename());
//        File toFile = null;
//        InputStream ins = null;
//        ins = codeFile.getInputStream();
//        toFile = new File(codeFile.getOriginalFilename());
//        inputStreamToFile(ins, toFile);
//        ins.close();
//
//        String content =  readFileContent(toFile);
//        System.out.println(content);

        return ResultUtil.success();
            }


    /**
     * 测试
     * @param jsonObject
     * @return
     */
    @PostMapping("/test")
    @ResponseBody
    public String test(String jsonObject) {
        System.out.println("file/test:"+jsonObject);
        return ResultUtil.success(jsonObject);
    }




    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            FileOutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public static String readFileContent(File file) {
        BufferedReader reader=  null;
        StringBuffer sbf
                =
                new
                        StringBuffer();

        try {
            reader
                    =
                    new
                            BufferedReader(
                            new
                                    FileReader(file));
            String tempStr;

            while
            ((tempStr = reader.readLine()) !=
                    null
            ) {
                sbf.append(tempStr);
            }
            reader.close();

            return
                    sbf.toString();
        } catch
        (IOException e) {
            e.printStackTrace();
        } finally {

            if
            (reader !=
                    null
            ) {

                try {
                    reader.close();
                } catch
                (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return
                sbf.toString();
    }



}
