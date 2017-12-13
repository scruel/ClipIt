package pers.scruel.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

/**
 * @author Scruel Tao <scruel@vip.qq.com>
 */
public class QiNiuUtils {
  private static UploadManager uploadManager;
  private static String upToken;
  private static String accessKey;
  private static String secretKey;
  private static String bucket;
  private static String bucketDomain;
  private static Properties properties;

  static {
    try {
      properties = PropertiesUtils.getProperties();
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(0);
    }
    accessKey = properties.getProperty("qiniu.access.key");
    secretKey = properties.getProperty("qiniu.secret.key");
    bucket = properties.getProperty("qiniu.bucket");
    bucketDomain = properties.getProperty("qiniu.bucket.domain");
    Auth auth = Auth.create(accessKey, secretKey);
    upToken = auth.uploadToken(bucket);
    Configuration cfg = new Configuration(Zone.autoZone());
    uploadManager = new UploadManager(cfg);
  }

  public static String uploadByBytes(byte[] bytes) {
    String key = getDateKey() + "clipboard" + ".png";
    try {
      Response response = uploadManager.put(bytes, key, upToken);
      return parserQiniuResponseResult(response);
    } catch (QiniuException ex) {
      Response r = ex.response;
      System.err.println(r.toString());
      try {
        System.err.println(r.bodyString());
      } catch (QiniuException ignore) {
        return "";
      }
      return "";
    }
  }

  public static String fileUpload(File file) {
    String localFilePath = file.getPath();
    //默认不指定key的情况下，以文件内容的hash值作为文件名
    String key = getDateKey() + file.getName();
    try {
      Response response = uploadManager.put(localFilePath, key, upToken);
      return parserQiniuResponseResult(response);
    } catch (QiniuException ex) {
      Response r = ex.response;
      System.err.println(r.toString());
      try {
        System.err.println(r.bodyString());
      } catch (QiniuException ignore) {
        return "";
      }
      return "";
    }
  }

  public static String streamUpload(InputStream in) {
    String key = getDateKey() + "clipboard" + ".png";
    try {
      Response response = uploadManager.put(in, key, upToken, null, null);
      return parserQiniuResponseResult(response);
    } catch (QiniuException ignore) {
      return "";
    }
  }

  public static String urlImgUpload(URL url) {
    //默认不指定key的情况下，以文件内容的hash值作为文件名
    String key = getDateKey() + "net." + getImgType(url);
    try {
      URLConnection conn = url.openConnection();
      Response response = uploadManager.put(conn.getInputStream(), key, upToken, null, null);
      return parserQiniuResponseResult(response);
    } catch (IOException ignore) {
      return "";
    }
  }

  private static String getDateKey() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    Date date = new Date();
    String dateStr = simpleDateFormat.format(date);
    Random rm = new Random();
    return dateStr + "_" + String.valueOf(rm.nextInt(160800)) + "_";
  }

  public static String getImgType(URL url) {
    return getImgType(url.toString());
  }

  public static String getImgType(String s) {
    if (s.contains("jpeg")) {
      return "jpeg";
    }
    if (s.contains("png")) {
      return "png";
    }
    if (s.contains("gif")) {
      return "gif";
    }
    if (s.contains("jpg")) {
      return "jpg";
    }
    if (s.contains("bmp")) {
      return "bmp";
    }
    return "unknown";
  }

  private static String parserQiniuResponseResult(Response response) throws QiniuException {
    //解析上传成功的结果
    DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
    String prefix = properties.getProperty("markdown.prefix");
    String suffix = properties.getProperty("markdown.suffix");
    String result;
    String key = putRet.key;
    String fileName = key.substring(key.indexOf("_", key.indexOf("_") + 1) + 1);
    if (!fileName.startsWith("clipboard")) {
      prefix = prefix.replace("image", fileName);
    }
    try {
      result = String.format("%s%s%s", prefix, bucketDomain + "/" + URLEncoder.encode(key, "utf-8").replace("+", "%20"), suffix);

    } catch (UnsupportedEncodingException e) {
      // e.printStackTrace();
      result = String.format("%s%s%s", prefix, bucketDomain + "/" + key, suffix);
    }
    // System.out.println(result);
    return result;
  }
}
