package pers.scruel.thread;

import cn.hutool.crypto.digest.DigestUtil;
import okhttp3.OkHttpClient;
import pers.scruel.listener.BaseAction;
import pers.scruel.util.PropertiesUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * @author Scruel Tao
 * @date 2022/7/1
 */
public class SingleFileThread extends BaseThread {
  private static final String savePath;
  private static final int hashLength = 43;
  private static final int extensionLength = 5;
  private static final OkHttpClient client = new OkHttpClient();
  private static Properties properties;

  static {
    try {
      properties = PropertiesUtils.getProperties();
    } catch (IOException var1) {
      var1.printStackTrace();
      System.exit(0);
    }

    savePath = properties.getProperty("singlefile.save-path");
  }

  public SingleFileThread(Object uploadObj, BaseAction action) {
    super(uploadObj, action);
  }

  private static String parsePageTitle(String filename) {
    return filename.substring(0, filename.length() - 43 - 5);
  }

  public void runWithString(String str) throws Exception {
    Process p = Runtime.getRuntime().exec("cmd /k start python SingleFileTemp.py", null,
                                          new File("tools/singlefile"));
    p.waitFor();
  }

  @Override
  void runWithURL(URL url) {
    String urlHrefHash = DigestUtil.sha1Hex(url.toString());
  }

  @Override
  void runWithFile(File file) {
  }

  @Override
  void runWithBytes(byte[] bytes) {
  }

  @Override
  void runWithImage(Image image) throws Exception {
  }
}
