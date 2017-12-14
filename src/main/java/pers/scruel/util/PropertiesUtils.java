package pers.scruel.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Loads properties.
 *
 * @author Scruel Tao <scruel@vip.qq.com>
 */
public class PropertiesUtils {
  private static boolean debug = false;
  private static final Properties properties = new Properties();
  private static final String SCRUEL_TEST_PATH = "C:\\Users\\scruel\\Desktop\\TOOL\\auto\\clipIt\\config.properties";
  private static boolean loaded = false;

  public static Properties getProperties() throws IOException {
    if (loaded) return properties;
    if (debug) {
      properties.load(new FileInputStream(SCRUEL_TEST_PATH));
    }
    else {
      properties.load(new FileInputStream("config.properties"));
    }
    loaded = true;
    return properties;
  }

  public static boolean isDebug() {
    return debug;
  }
}
