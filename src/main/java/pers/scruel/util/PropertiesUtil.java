package pers.scruel.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Scruel on 2017/8/19.
 * Personal blog : http://blog.csdn.net/scruelt
 * Github : https://github.com/scruel
 */
public class PropertiesUtil {
  private static final Properties properties = new Properties();
  private static boolean debug = false;

  public static Properties getProperties() {
    try {
      // properties.load(QiNiuUtil.class.getClassLoader().getResourceAsStream("config.properties"));
      if (debug) {
        properties.load(new FileInputStream("C:\\Users\\scrue\\Desktop\\TOOL\\auto\\clipIt\\config.properties"));
      }
      else {
        properties.load(new FileInputStream("config.properties"));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return properties;
  }

  public static boolean isDebug() {
    return debug;
  }
}
