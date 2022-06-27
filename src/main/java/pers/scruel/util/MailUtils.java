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
 * @author Scruel Tao
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
  public MailUtils() throws GeneralSecurityException, IOException {
    boolean debug = Boolean.parseBoolean(System.getenv("CLIPLT_DEBUG"));
    System.setProperty("mail.mime.splitlongparameters", "false");
    Properties properties = PropertiesUtils.getProperties();
    this.mailHost = properties.getProperty("stk.mail.smtp.host");
    this.senderUsername = properties.getProperty("stk.mail.sender.username");
    this.senderPassword = properties.getProperty("stk.mail.sender.pass");
    this.receiveUsername = properties.getProperty("stk.mail.receiver.username");
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
      InternetAddress from = new InternetAddress(senderUsername);
      message.setFrom(from);

      InternetAddress to = new InternetAddress(receiveUsername);
      message.setRecipient(Message.RecipientType.TO, to);
      message.setSubject(subject);

      Multipart multipart = new MimeMultipart();

      BodyPart contentPart = new MimeBodyPart();
      contentPart.setContent(sendHtml, "text/html; charset=utf-8");
      multipart.addBodyPart(contentPart);

      if (attachment != null) {
        BodyPart attachmentBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(attachment);
        attachmentBodyPart.setDataHandler(new DataHandler(source));
        attachmentBodyPart.setFileName(MimeUtility.encodeText(attachment.getName(), "utf-8", "B"));
        multipart.addBodyPart(attachmentBodyPart);
      }

      message.setContent(multipart);
      message.saveChanges();
      transport = session.getTransport("smtp");
      transport.connect(mailHost, 465, senderUsername, senderPassword);
      transport.sendMessage(message, message.getAllRecipients());
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
}
