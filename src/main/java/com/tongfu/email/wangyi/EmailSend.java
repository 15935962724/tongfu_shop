package com.tongfu.email.wangyi;


import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.*;
import java.util.Properties;

/**
 * 发送邮件
 */
public class EmailSend {


    public static void main(String[] args) throws Exception {
        sendHtmelMail();//发送邮件
//        readHtmelMail();//读取邮件内容
    }


    /**
     * 发送邮件
     *
     * @throws Exception
     */
    public static void sendHtmelMail() throws Exception {

        String from = "吕志超";//发件人
        String[] to = {"1263451851@qq.com"};//收件人邮箱
        String subject = "Hainan (EU comm label) order of Therasphere Dose 2022-07-26 (ID: 代红卫_6900116158)";//邮件主题
        /*邮件内容*/
        String text = "<p>Hi,</p><p>Please take the following order:</p><p>&nbsp;</p><p style=\"margin-left: 48px\">Site Name/ 供应商名称: China Isotope &amp; Radiation Corporation (CIRC)</p><p style=\"margin-left: 48px\">Contact Name / 联系人姓名: Yuan XinXin; Brian Tseng</p><p style=\"margin-left: 48px\">Location Identifier/ 位置识别码: NR-TS-EU-481088</p><p style=\"margin-left: 48px\">Email Address/ 邮箱地址:&nbsp;<a href=\"mailto:yuanxinxin@circ.com.cn\" style=\"color: rgb(0, 51, 153)\">yuanxinxin@circ.com.cn</a>,&nbsp;<a href=\"mailto:Brian.tseng@bsci.com\" style=\"color: rgb(0, 51, 153)\">Brian.tseng@bsci.com</a></p><p style=\"margin-left: 48px\">City / 城市: Beijing</p><p style=\"margin-left: 48px\">Telephone Number/ 电话号码: 18601102107 (Yuan XinXin), 15618345085 (Brian Tseng)</p><p style=\"margin-left: 48px\">Planned Treatment Date (China Standard Time) / 计划治疗日期（北京时间）: Aug 05</p><p style=\"margin-left: 48px\">Planned Treatment Time (China Standard Time) / 计划治疗时间（北京时间）:&nbsp; 11:00</p><p style=\"margin-left: 20px;text-indent: 28px\">Treatment Reference / 医院名称及受试者编号: &nbsp;Hainan Super Hospital, Patient ID 6000030766</p><p style=\"margin-left: 48px\">Order:</p><p class=\"MsoListParagraph\" style=\"margin-right: 0;margin-bottom: 0;margin-left: 96px;text-indent: 0\">·&nbsp;&nbsp;&nbsp; &nbsp; &nbsp; Dose # \u00AD\u00AD1:&nbsp;<span style=\"text-decoration:underline;\">___</span><span style=\"text-decoration:underline;\">3_</span><span style=\"text-decoration:underline;\">_</span>_ GBq, Calibration Date ___<span style=\"text-decoration:underline;\">July - 31_</span>__</p><p style=\"margin-left: 48px\">FOC (free of charge) / 免费 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; No</p><p>&nbsp;</p><p>Best regards,</p><p>Yuan XinXin</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p><br/></p>";

        String host = "smtphz.qiye.163.com";
        String username = "lvzhichao@circ.com.cn";//发件人邮箱
        String password = "bq6LEKKmk5AbF2nb";//发件人邮箱秘钥

        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.smtp.timeout", "465");
        props.setProperty("mail.smtp.auth", "true");

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);


        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "utf-8");
        String nick = MimeUtility.encodeText(from);
        mimeMessageHelper.setFrom(new InternetAddress(nick + "<" + username + ">"));
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, true);
        mimeMessageHelper.addAttachment("TheraSphere.pdf", new File("D:\\TheraSphere Treatment Window Illustrator - Dai Weihong.pdf"));
        javaMailSender.setHost(host);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setJavaMailProperties(props);
        javaMailSender.send(message);

        System.out.println("true");


    }


    /**
     * 读取邮件内容
     *
     * @throws Exception
     */
    public static void readHtmelMail() throws Exception {

        try {//1. 设置连接信息, 生成一个 Session

            String protocol = "pop3";

            boolean isSSL = true;

            String host = "imaphz.qiye.163.com";

            int port = 995;

            String username = "lvzhichao@circ.com.cn";

//            String password= "Lzc!2021";
            String password = "bq6LEKKmk5AbF2nb";

            Properties props = new Properties();

            props.put("mail.pop3.ssl.enable", isSSL);

            props.put("mail.pop3.host", host);

            props.put("mail.pop3.port", port);

            Session session = Session.getDefaultInstance(props);//2. 获取 Store 并连接到服务器

            Store store = session.getStore(protocol);

            store.connect(username, password);

            Folder folder = store.getDefaultFolder();//默认父目录

            if (folder == null) {
                System.out.println("服务器不可用");
                return;

            }/*System.out.println("默认信箱名:" + folder.getName());

Folder[] folders = folder.list();// 默认目录列表

for(int i = 0; i < folders.length; i++) {
System.out.println(folders[0].getName());

}

System.out.println("默认目录下的子目录数: " + folders.length);*/
            Folder popFolder = folder.getFolder("INBOX");//获取收件箱

            popFolder.open(Folder.READ_WRITE);//可读邮件,可以删邮件的模式打开目录//4. 列出来收件箱 下所有邮件

            Message[] messages = popFolder.getMessages();//取出来邮件数

            int msgCount = popFolder.getMessageCount();

            System.out.println("共有邮件: " + msgCount + "封");//FetchProfile fProfile = new FetchProfile();//选择邮件的下载模式,//根据网速选择不同的模式//fProfile.add(FetchProfile.Item.ENVELOPE);//folder.fetch(messages, fProfile);//选择性的下载邮件//5. 循环处理每个邮件并实现邮件转为新闻的功能

            for (int i = msgCount - 1; i > 0; i--) {//单个邮件

                System.out.println("第" + i + "邮件开始");

                mailReceiver(messages[i]);

                System.out.println("第" + i + "邮件结束");//邮件读取用来校验

                messages[i].writeTo(new FileOutputStream("D:/pop3MailReceiver" + i + ".eml"));

            }//7. 关闭 Folder 会真正删除邮件, false 不删除

            popFolder.close(true);//8. 关闭 store, 断开网络连接

            store.close();

        } catch (NoSuchProviderException e) {//TODO Auto-generated catch block

            e.printStackTrace();

        } catch (Exception e) {//TODO Auto-generated catch block

            e.printStackTrace();

        }


    }


    private static void mailReceiver(Message msg) throws Exception {//发件人信息

        Address[] froms = msg.getFrom();

        if (froms != null) {//System.out.println("发件人信息:" + froms[0]);

            InternetAddress addr = (InternetAddress) froms[0];

            System.out.println("发件人地址:" + addr.getAddress());
            String address = addr.getAddress();
            if (!address.equals("yuanxinxin@circ.com.cn")) {
                return;
            }
            System.out.println("发件人显示名:" + addr.getPersonal());

        }


        System.out.println("邮件主题:" + msg.getSubject());//getContent() 是获取包裹内容, Part相当于外包装

        Object o = msg.getContent();
        if (o instanceof Multipart) {
            Multipart multipart = (Multipart) o;

            reMultipart(multipart);

        } else if (o instanceof Part) {
            Part part = (Part) o;

            rePart(part);

        } else {
            System.out.println("类型" + msg.getContentType());

            System.out.println("内容" + msg.getContent());

        }

    }


    /***@parampart 解析内容
     *@throwsException*/

    private static void rePart(Part part) throws MessagingException,IOException {
        if (part.getDisposition() != null) {
            String strFileNmae = MimeUtility.decodeText(part.getFileName()); //MimeUtility.decodeText解决附件名乱码问题

            System.out.println("发现附件: " + MimeUtility.decodeText(part.getFileName()));

            System.out.println("内容类型: " + MimeUtility.decodeText(part.getContentType()));

            System.out.println("附件内容:" + part.getContent());

            InputStream in = part.getInputStream();//打开附件的输入流//读取附件字节并存储到文件中

            java.io.FileOutputStream out = new FileOutputStream(strFileNmae);
            int data;
            while ((data = in.read()) != -1) {
                out.write(data);

            }
            in.close();
            out.close();
        } else {
            if (part.getContentType().startsWith("text/plain")) {
                System.out.println("文本内容：" + part.getContent());
            } else {
                System.out.println("HTML内容：" + part.getContent());
            }
        }
    }


    /***@parammultipart // 接卸包裹(含所有邮件内容(包裹+正文+附件))
     *@throwsException*/

    private static void reMultipart(Multipart multipart) throws Exception {//
        System.out.println("邮件共有" + multipart.getCount() + "部分组成");//依次处理各个部分
        for (int j = 0, n = multipart.getCount(); j < n; j++) {
            System.out.println("处理第" + j + "部分");
            Part part = multipart.getBodyPart(j);//解包, 取出 MultiPart的各个部分, 每部分可能是邮件内容,//也可能是另一个小包裹(MultipPart)//判断此包裹内容是不是一个小包裹, 一般这一部分是 正文 Content-Type: multipart/alternative
            if (part.getContent() instanceof Multipart) {
                Multipart p = (Multipart) part.getContent();//转成小包裹//递归迭代
                reMultipart(p);
            } else {
                rePart(part);
            }
        }
    }
}