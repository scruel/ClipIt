package pers.scruel.thread;


import pers.scruel.listener.BaseAction;
import pers.scruel.util.IOUtil;
import pers.scruel.util.MailUtil;
import pers.scruel.util.PropertiesUtil;

import java.io.File;
import java.net.URL;

/**
 * @author Scruel Tao <scruel@vip.qq.com>
 */
public class SendToKindleThread extends BaseThread {
  private String path = "tools/kindle";

  public SendToKindleThread(Object uploadObj, BaseAction action) {
    super(uploadObj, action);
    // path = SendToKindleThread.class.getClassLoader().getResource("tools/kindle").getPath();
  }

  @Override
  void runWithFile(File file) throws Exception {
    MailUtil se = new MailUtil(true);
    String filenameWithoutExtension = file.getName();
    filenameWithoutExtension = filenameWithoutExtension.substring(0, filenameWithoutExtension.lastIndexOf("."));
    File newFile = new File(path + "\\temp\\" + filenameWithoutExtension + ".mobi");

    if (!newFile.exists()) {
      IOUtil.copyFile(file, path + "\\temp\\" + file.getName());
      Process p = Runtime.getRuntime()
          .exec("cmd.exe /c start /wait mobi.bat", //path to executable
              null, // env vars, null means pass parent env
              new File(path)); // working directory
      p.waitFor();
    }
    se.doSendHtmlEmail("sendToKindle", "send", newFile);
    if ("true".equals(PropertiesUtil.getProperties().getProperty("stk.auto.delete"))) {
      IOUtil.deleteFileMatchByPrefix(path + "\\temp\\", filenameWithoutExtension);
    }
  }

  @Override
  void runWithURL(URL url) {

  }

  @Override
  void runWithBytes(byte[] bytes) {

  }
}
