package com.scruel.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Scruel on 2017/8/19.
 * Personal blog : http://blog.csdn.net/scruelt
 * Github : https://github.com/scruel
 */
public class PropertiesUtil {

    public static Properties getProperties() {
        Properties properties = new Properties();
        try {
            // properties.load(QiNiuUtil.class.getClassLoader().getResourceAsStream("config.properties"));
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
