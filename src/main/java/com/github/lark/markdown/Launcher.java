package com.github.lark.markdown;

import cn.hutool.log.Log;
import cn.hutool.system.SystemUtil;
import com.github.lark.markdown.utils.FxUtil;
import com.github.lark.markdown.utils.PropertiesUtil;
import javafx.application.Application;

import java.io.IOException;
import java.util.Properties;

/**
 * @Author: xy-code
 * @Description: 启动类
 * @Date: 2023-10-29 16:27
 **/
public class Launcher {

    private static final Log log = Log.get();

    public static void main(String[] args) throws IOException {
        // 捕捉未处理的异常
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> log.error("程序发生未处理的异常：", e));
        configHandle();
        Application.launch(MainApplication.class);
    }

    /**
     * 处理配置文件
     * @throws IOException
     */
    private static void configHandle() throws IOException {
        Properties properties = PropertiesUtil.loadProperties(FxUtil.getResourcesPath(Constants.CONTENT_FILE_NAME));
        String markdownSavePath = properties.getProperty(Constants.MARKDOWN_SAVE_PATH_KEY);
        if (markdownSavePath == null) {
            properties.setProperty(Constants.MARKDOWN_SAVE_PATH_KEY, System.getProperty(SystemUtil.USER_HOME));
            PropertiesUtil.storeProperties(properties, FxUtil.getResourcesPath(Constants.CONTENT_FILE_NAME));
        }
    }
}
