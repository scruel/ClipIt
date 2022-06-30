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
  private static boolean loaded = false;

  public static Properties getProperties() throws IOException {
    if (loaded) {
      return PROPERTIES;
    }
    String configPath = System.getenv("CLIPLT_CONFIG_PATH");
    if (null == configPath) {
      configPath = "config.properties";
    }
    PROPERTIES.load(Files.newInputStream(Paths.get(configPath)));
    loaded = true;
    return PROPERTIES;
  }
}
