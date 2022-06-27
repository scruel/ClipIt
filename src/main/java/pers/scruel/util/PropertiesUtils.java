package pers.scruel.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Loads properties.
 *
 * @author Scruel Tao
 */
public class PropertiesUtils {
  private static final Properties PROPERTIES = new Properties();
  private static final String SCRUEL_TEST_PATH = "C:\\Users\\scruel\\Desktop\\TOOL\\auto\\clipIt\\config.properties";
  private static boolean loaded = false;

  public static Properties getProperties() throws IOException {
    if (loaded) {
      return PROPERTIES;
    }
    if (Boolean.parseBoolean(System.getenv("CLIPLT_DEBUG"))) {
      PROPERTIES.load(Files.newInputStream(Paths.get(SCRUEL_TEST_PATH)));
    }
    else {
      PROPERTIES.load(Files.newInputStream(Paths.get("config.properties")));
    }
    loaded = true;
    return PROPERTIES;
  }
}
