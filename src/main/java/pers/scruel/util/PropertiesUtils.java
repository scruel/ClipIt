package pers.scruel.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Loads properties.
 *
 * @author Scruel Tao
 */
public class PropertiesUtils {
  private static final Properties properties = new Properties();
  private static final String SCRUEL_TEST_PATH = "C:\\Users\\scruel\\Desktop\\TOOL\\auto\\clipIt\\config.properties";
  private static boolean loaded = false;

  public static Properties getProperties() throws IOException {
    if (loaded) {
      return properties;
    }
    if (Boolean.parseBoolean(System.getenv("CLIPLT_DEBUG"))) {
      properties.load(new FileInputStream(SCRUEL_TEST_PATH));
    }
    else {
      properties.load(new FileInputStream("config.properties"));
    }
    loaded = true;
    return properties;
  }
}
