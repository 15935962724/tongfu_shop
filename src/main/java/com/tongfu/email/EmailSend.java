package com.tongfu.email;


import com.tongfu.Util.DateUtil;
import com.tongfu.Util.XmlUtil;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.*;


/**
 * 发送邮件
 */
public class EmailSend {

    public static boolean EmailSendTest(EmailEntity emailEntity){
        try {
            //配置文件
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.host", emailEntity.getHost());
            properties.put("mail.smtp.port",emailEntity.getPort());
            properties.put("mail.smtp.starrttls.enable", "true");
            //创建会话
            VerifyEmail verifyEmail = new VerifyEmail(emailEntity.getUserName(), emailEntity.getPassword());
            Session mailSession = Session.getInstance(properties, verifyEmail);
            mailSession.setDebug(true);
            //创建信息对象
            Message message = new MimeMessage(mailSession);
            InternetAddress from = new InternetAddress(emailEntity.getFromAddress());
            InternetAddress to = new InternetAddress(emailEntity.getToAddress());
            //设置邮件信息的来源
            message.setFrom(from);
            //设置邮件的接收者
            message.setRecipient(MimeMessage.RecipientType.TO, to);
            message.setSubject(emailEntity.getSubject());
            //设置邮件发送日期
            message.setSentDate(new Date());
//            //设置邮件内容
//            message.setContent(emailEntity.getContext() , emailEntity.getContextType());

            Multipart mp = new MimeMultipart();
                         MimeBodyPart mbpContent = new MimeBodyPart();
                         mbpContent.setText(emailEntity.getContext());
                         mp.addBodyPart(mbpContent);

            /* 往邮件中添加附件 */
            String fileName = emailEntity.getFilename();
            Vector<String> file = emailEntity.getFile();
                        if (file!= null) {
                                 Enumeration<String> efile = file.elements();
                                 while (efile.hasMoreElements()) {
                                         MimeBodyPart mbpFile = new MimeBodyPart();
                                         fileName = efile.nextElement().toString();
                                         FileDataSource fds = new FileDataSource(fileName);
                                         mbpFile.setDataHandler(new DataHandler(fds));
//                                         System.out.println(fds.getName());
//                                         String text = MimeUtility.encodeText(new String(fds.getName().getBytes(), "GB2312"), "GB2312", "B");
                                     System.out.println(MimeUtility.encodeText(fds.getName()));
                                         mbpFile.setFileName(fds.getName());
                                     mp.addBodyPart(mbpFile);
                                     }
                                 System.out.println("添加成功");
                             }
            message.setContent(mp);
            message.saveChanges();
            //发送邮件
            Transport transport = mailSession.getTransport("smtp");

            transport.connect(emailEntity.getHost(), emailEntity.getUserName(), emailEntity.getPassword());
            System.out.println("发送:" + transport);
            transport.sendMessage(message, message.getAllRecipients());
            System.out.println("success");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("fial..." + e.getMessage());
            return false;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static void main(String[] args) throws Exception {







        EmailEntity email = new EmailEntity();
//        email.setUserName("15935962724@163.com");
//        email.setPassword("ZhuGeSiMa123");
//        email.setFromAddress("15935962724@163.com");
//        email.setHost("smtp.163.com");
        email.setUserName("lvzhichao@circ.com.cn");
        email.setPassword("Lzc!2021");
        email.setFromAddress("lvzhichao@circ.com.cn");
        email.setHost("smtphz.qiye.163.com");
        email.setPort(465);

        email.setToAddress("1263451851@qq.com");
        email.setSubject("这是一封测试邮件!!!!");
        Map<String, String> data = new HashMap<String, String>();
        data.put("System", "Apsaras Brachy 3");
        data.put("Time", DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss",new Date()));
        data.put("HospitalName","协和医院");
        data.put("Deadline", "1");
        data.put("CaseNumber", "0");
        data.put("SerialNumber", "dfasdlfjasldkfjasldfkjaskldf");
        String url = "hppt://47.111.95.217:8004";
        System.out.println(url);
//        data.put("url", url+"/feiTainZhaoYe/authorizationCode");
        data.put("url", "http://lz.redcome.com/sendKeyToLiZi.do");
        data.put("orderItemId", "66");
        String content = XmlUtil.mapToXmlRequest(data).toString(); //map 转 xml
        System.out.println(content);
        email.setContext(content);
        email.setContextType("text/html;charset=utf-8");
//        email.attachFile("D:\\aaa.txt"); // 往邮件中添加附件
//        email.attachFile("E:\\upload\\avatar2.jpg");
//        email.attachFile("往邮件中添加附件");
        boolean flag = EmailSend.EmailSendTest(email);
        System.err.println("邮件发送结果=="+flag);

    }

}