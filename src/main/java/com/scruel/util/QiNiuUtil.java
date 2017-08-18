package com.scruel.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

/**
 * Created by Scruel on 2017/8/18.
 * Personal blog : http://blog.csdn.net/scruelt
 * Github : https://github.com/scruel
 */
public class QiNiuUtil {
    private static UploadManager uploadManager;
    private static String upToken;
    private static String accessKey;
    private static String secretKey;
    private static String bucket;
    private static String bucket_domain;
    private static Properties properties;

    static {
        properties = PropertiesUtil.getProperties();
        accessKey = properties.getProperty("accessKey");
        secretKey = properties.getProperty("secretKey");
        bucket = properties.getProperty("bucket");
        bucket_domain = properties.getProperty("bucket_domain");
        Auth auth = Auth.create(accessKey, secretKey);
        upToken = auth.uploadToken(bucket);
        Configuration cfg = new Configuration(Zone.autoZone());
        uploadManager = new UploadManager(cfg);
    }

    public static void uploadByBytes(byte[] bytes) {
        String key = getDateKey() + "image" + ".png";
        try {
            Response response = uploadManager.put(bytes, key, upToken);
            parserQiniuResponseResult(response);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    public static void streamUpload(InputStream in) {
        String key = getDateKey() + "image" + ".png";
        try {
            Response response = uploadManager.put(in, key, upToken, null, null);
            parserQiniuResponseResult(response);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    public static void fileUpload(File file) {
        String localFilePath = file.getPath();
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = getDateKey() + file.getName();
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            parserQiniuResponseResult(response);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    private static String getDateKey() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String dateStr = simpleDateFormat.format(date);
        Random rm = new Random();
        return dateStr + "_" + String.valueOf(rm.nextInt(160800)) + "_";
    }

    private static void parserQiniuResponseResult(Response response) throws QiniuException {
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        String prefix = properties.getProperty("markdownPrefix");
        String suffix = properties.getProperty("markdownSuffix");
        String result = prefix + bucket_domain + "/" + putRet.key + suffix;
        System.out.println(result);
        ClipboardUtil.setClipBoard(result);
        System.out.println(putRet.hash);
    }
}
