package com.github.lark.markdown.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @Author: xy-code
 * @Description: Properties文件工具类
 * @Date: 2023-10-29 16:27
 **/
public class PropertiesUtil {

    private PropertiesUtil() {
    }

    public static Properties loadProperties(String fileName) throws IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(fileName));
        return props;
    }

    public static void storeProperties(Properties props, String fileName) throws IOException {
        props.store(new FileOutputStream(fileName), null);
    }

    public static void setProperty(Properties props, String key, String value) {
        props.setProperty(key, value);
    }

    public static String getProperty(Properties props, String key) {
        return props.getProperty(key);
    }

    public static void removeProperty(Properties props, String key) {
        props.remove(key);
    }

}
