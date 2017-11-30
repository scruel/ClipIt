package pers.scruel.util;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;

/**
 * Created by Scruel on 2017/8/18.
 * Personal blog : http://blog.csdn.net/scruelt
 * Github : https://github.com/scruel
 */
public class BaiduOCRUtil {
  private static AipOcr client;
  private static String apiId;
  private static String secretKey;
  private static String apiKey;
  private static Properties properties;

  static {
    try {
      properties = PropertiesUtil.getProperties();
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(0);
    }

    apiId = properties.getProperty("baiduyun.api.id");
    apiKey = properties.getProperty("baiduyun.api.key");
    secretKey = properties.getProperty("baiduyun.secret.key");
    client = new AipOcr(apiId, apiKey, secretKey);
  }

  public static String bytesImgOCR(byte[] bytes) {
    JSONObject res = client.basicGeneral(bytes, new HashMap<String, String>());
    return parserResponseResult(res);
  }

  public static String fileImgOCR(File file) {
    JSONObject res = client.basicGeneral(file.getPath(), new HashMap<String, String>());
    return parserResponseResult(res);
  }

  public static String urlImgOCR(URL url) {
    JSONObject res = client.basicGeneralUrl(url.getPath(), new HashMap<String, String>());
    return parserResponseResult(res);
  }

  private static String getDateKey() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    Date date = new Date();
    String dateStr = simpleDateFormat.format(date);
    Random rm = new Random();
    return dateStr + "_" + String.valueOf(rm.nextInt(160800)) + "_";
  }

  private static String getImgType(URL url) {
    String urlS = url.toString();
    if (urlS.contains("jpeg")) {
      return "jpeg";
    }
    if (urlS.contains("png")) {
      return "png";
    }
    if (urlS.contains("gif")) {
      return "gif";
    }
    if (urlS.contains("jpg")) {
      return "jpg";
    }
    if (urlS.contains("bmp")) {
      return "bmp";
    }
    return "unknown";
  }


  private static String parserResponseResult(JSONObject res) {
    StringBuilder lastResult = new StringBuilder();
    // System.out.println(res.toString(2));
    JSONArray jsonarray = res.getJSONArray("words_result");
    // if (jsonarray == null)
    for (Object o : jsonarray) {
      JSONObject wordJson = (JSONObject) o;
      lastResult.append(wordJson.get("words")).append("\n");
    }
    return lastResult.toString();
  }
}
