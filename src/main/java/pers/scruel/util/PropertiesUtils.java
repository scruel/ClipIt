package pers.scruel.util;

import io.github.cdimascio.dotenv.Dotenv;

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
    try {

      Dotenv dotenv = Dotenv.configure().load();
      dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
    } catch (Exception ignore) {
    }
    String configPath = System.getProperty("CLIPLT_CONFIG_PATH");
    if (null == configPath) {
      configPath = "config.properties";
    }
    PROPERTIES.load(Files.newInputStream(Paths.get(configPath)));
    loaded = true;
    return PROPERTIES;
  }
}
