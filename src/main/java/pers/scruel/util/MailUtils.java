package pers.scruel.util;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * @author Scruel Tao <scruel@vip.qq.com>
 */
public class MailUtils {
  private MimeMessage message;
  private Session session;
  private Transport transport;

  private String mailHost = "";
  private String senderUsername = "";
  private String receiveUsername = "";
  private String senderPassword = "";

  /*
   * 初始化方法
   */
  public MailUtils(boolean debug) throws GeneralSecurityException, IOException {
    //解决中文乱码的终极方案
    System.setProperty("mail.mime.splitlongparameters", "false");
    Properties properties = PropertiesUtils.getProperties();
    this.mailHost = properties.getProperty("stk.mail.smtp.host");
    this.senderUsername = properties.getProperty("stk.mail.sender.username");
    this.senderPassword = properties.getProperty("stk.mail.sender.pass");
    this.receiveUsername = properties.getProperty("stk.mail.receiver.username");
    // 开启SSL加密，否则会失败
    properties.put("mail.smtp.ssl.enable", true);

    session = Session.getInstance(properties);
    session.setDebug(debug); // 开启后有调试信息
    message = new MimeMessage(session);
  }

  /**
   * 发送邮件
   *
   * @param subject    邮件主题
   * @param sendHtml   邮件内容
   * @param attachment 附件
   */
  public void doSendHtmlEmail(String subject, String sendHtml, File attachment) {
    try {
      // 发件人
      InternetAddress from = new InternetAddress(senderUsername);
      message.setFrom(from);

      // 收件人
      InternetAddress to = new InternetAddress(receiveUsername);
      message.setRecipient(Message.RecipientType.TO, to);

      // 邮件主题
      message.setSubject(subject);

      // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
      Multipart multipart = new MimeMultipart();

      // 添加邮件正文
      BodyPart contentPart = new MimeBodyPart();
      contentPart.setContent(sendHtml, "text/html; charset=utf-8");
      multipart.addBodyPart(contentPart);

      // 添加附件的内容
      if (attachment != null) {
        BodyPart attachmentBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(attachment);
        attachmentBodyPart.setDataHandler(new DataHandler(source));

        // 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
        // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
        //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
        //messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachment.getName().getBytes()) + "?=");

        //MimeUtility.encodeWord可以避免文件名乱码
        attachmentBodyPart.setFileName(MimeUtility.encodeText(attachment.getName(), "utf-8", "B"));
        // attachmentBodyPart.setFileName(new String(attachment.getName().getBytes(), "utf-8"));
        multipart.addBodyPart(attachmentBodyPart);
      }

      // 将multipart对象放到message中
      message.setContent(multipart);
      // 保存邮件
      message.saveChanges();
      transport = session.getTransport("smtp");
      // System.out.println(session.getTransport());
      // smtp验证，就是你用来发邮件的邮箱用户名密码
      transport.connect(mailHost, 465, senderUsername, senderPassword);
      // 发送
      transport.sendMessage(message, message.getAllRecipients());

      // System.out.println("send success!");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (transport != null) {
        try {
          transport.close();
        } catch (MessagingException e) {
          e.printStackTrace();
        }
      }
    }
  }

  // public static void main(String[] args) throws GeneralSecurityException {
  //     pers.scruel.util.MailUtils se = new pers.scruel.util.MailUtils(true);
  //     File affix = new File("F:\\Resilio Sync\\得到2\\每天听本书\\2016\\2016年09月\\0927关键时刻\\关键时刻.doc");
  //     se.doSendHtmlEmail("邮件主题", "邮件内容", affix);//
  // }
}
