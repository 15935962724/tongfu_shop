package com.tongfu.email.aliyun;

import com.tongfu.Util.DateUtil;
import com.tongfu.Util.XmlUtil;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 发送邮件
 */
public class MailSendUtils {

    // 邮箱smtp协议 这里是使用阿里云的
    public static final String myEmailSMTPHost = "smtpdm.aliyun.com";
//    public static final String myEmailSMTPHost = "80";

    public static void main(String[] args) throws Exception {

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
        data.put("aaa", "http://lz.redcome.com/sendKeyToLiZi.do");


        data.put("orderItemId", "66");
        String content = XmlUtil.mapToXml(data,false);//map转xml
        System.out.println(content);
        MailInfo mailInfo = new MailInfo("surgi-plan@www.surgi-plan.com",  //发送方邮箱
                "SurgiPlan123",  //发送方邮箱密码
                "1263451851@qq.com", //接受方邮箱
                "解正涛", //发送人
                "雷克斯", //接收人
                "申请码",  //邮件标题
                content,//邮件正文
                null);//邮件附件
        try {
            boolean b = sendEmail(mailInfo);
            System.out.println("发送邮件结果："+b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean sendEmail(MailInfo mailInfo)  {
        try {
            // 1. 创建参数配置, 用于连接邮件服务器的参数配置
            Properties props = new Properties();          // 参数配置
            props.setProperty("mail.transport.protocol", "smtp");  // 使用的协议（JavaMail规范要求）
            props.setProperty("mail.smtp.host", myEmailSMTPHost);  // 发件人的邮箱的 SMTP 服务器地址
            props.setProperty("mail.smtp.auth", "true");      // 需要请求认证

            props.setProperty("mail.smtp.socketFactory.fallback","false");
            props.setProperty("mail.smtp.port","80");
            props.setProperty("mail.smtp.socketFactory.port","80");

            // 2. 根据配置创建会话对象, 用于和邮件服务器交互
            Session session = Session.getInstance(props);
            session.setDebug(true);                 // 设置为debug模式, 可以查看详细的发送 log

            // 3. 创建一封邮件
            MimeMessage message = new MailSendUtils().createMimeMessage(session, mailInfo);

            // 4. 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport();

            // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
            transport.connect(mailInfo.getSendEmailAccount(), mailInfo.getSendEmailPassword());

            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());

            // 7. 关闭连接
            transport.close();
            return true;
        }catch (Exception e){
            System.out.println("邮件发送失败原因："+e.getMessage());
            return false;
        }

    }


    public MimeMessage createMimeMessage(Session session, MailInfo mailInfo) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
        message.setFrom(new InternetAddress(mailInfo.getSendEmailAccount(), mailInfo.getSendPersonName(), "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO,
                new InternetAddress(mailInfo.getReceiveMailAccount(), mailInfo.getReceivePersonName(), "UTF-8"));

        // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
        message.setSubject(mailInfo.getMailTitle(), "UTF-8");

        if (mailInfo.getFileName()!=null){
            //内容
            Multipart multipart = new MimeMultipart();
            //body部分
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(mailInfo.getMailContent(), "text/plain;charset=utf-8");
            multipart.addBodyPart(contentPart);

            //附件部分
            BodyPart attachPart = new MimeBodyPart();
            FileDataSource fileDataSource = new FileDataSource(mailInfo.getFileName());
            attachPart.setDataHandler(new DataHandler(fileDataSource));
            attachPart.setFileName(MimeUtility.encodeText(fileDataSource.getName()));
            multipart.addBodyPart(attachPart);
            message.setContent(multipart);
        }else {
//             5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
        message.setContent(mailInfo.getMailContent(), "text/plain;charset=UTF-8");
        }
        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }




}