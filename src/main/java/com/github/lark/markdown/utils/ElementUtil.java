package com.github.lark.markdown.utils;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import com.github.lark.markdown.Constants;
import com.github.lark.markdown.parser.page.label.LabelAttributeName;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * @Author: xy-code
 * @Description: ElementUtil工具类
 * @Date: 2023-10-29 16:27
 **/
public class ElementUtil {


    private static volatile ChromeDriver currentDriver = null;
    private static final ThreadLocal<ChromeDriver> DRIVER_THREAD_LOCAL = new ThreadLocal<>();
    private static final ThreadLocal<String> FILE_DIR_THREAD_LOCAL = new ThreadLocal<>();

    public static void setDriver(ChromeDriver chromeDriver) {
        DRIVER_THREAD_LOCAL.set(chromeDriver);
        currentDriver = chromeDriver;
    }

    public static ChromeDriver getDriver() {
        return DRIVER_THREAD_LOCAL.get();
    }

    public static void removeDriver() {
        DRIVER_THREAD_LOCAL.remove();
    }

    public static void setFileDir(String dirPath) {
        FILE_DIR_THREAD_LOCAL.set(dirPath);
    }

    public static String getFileDir() {
        return FILE_DIR_THREAD_LOCAL.get();
    }

    public static void removeFileDir() {
        FILE_DIR_THREAD_LOCAL.remove();
    }

    public static void closeCurrentDriver() {
        if(currentDriver != null) {
            currentDriver.quit();
        }
    }

    private ElementUtil() {
    }

    public static boolean scrollToElementDisplayed(ChromeDriver driver, WebElement parentElement, By by) {
        long roll = 100;
        Number beforeHeight = 0L;
        boolean displayed = false;
        for (; ; ) {
            if (parentElement.findElement(by).isDisplayed()) {
                displayed = true;
                break;
            }
            driver.executeScript(String.format("window.scrollBy(0,%d)", roll));
            Number afterHeight = (Number) driver.executeScript("return document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;");
            if (Objects.equals(beforeHeight, afterHeight)) {
                break;
            }
            ThreadUtil.sleep(50);
            beforeHeight = afterHeight;
        }
//        if(displayed) {
//            driver.executeScript("arguments[0].scrollIntoView()",parentElement.findElement(by));
//        }
        return displayed;
    }

    public static boolean scrollToElementDisplayed(ChromeDriver driver, By by) {
        long roll = 100;
        Number beforeHeight = 0L;
        boolean displayed = false;
        for (; ; ) {
            if (driver.findElement(by).isDisplayed()) {
                displayed = true;
                break;
            }
            driver.executeScript(String.format("window.scrollBy(0,%d)", roll));
            Number afterHeight = (Number) driver.executeScript("return document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;");
            if (Objects.equals(beforeHeight, afterHeight)) {
                break;
            }
            ThreadUtil.sleep(50);
            beforeHeight = afterHeight;
        }
        return displayed;
    }

    public static void scrollToTop(ChromeDriver driver) {
        driver.executeScript("document.documentElement.scrollTop=0;");
    }

    public static final boolean elementIsExists(WebElement webElement, By selector) {
        if (webElement == null) {
            throw new IllegalArgumentException("webElement is null");
        }
        if (selector == null) {
            throw new IllegalArgumentException("selector is null");
        }
        boolean result = true;
        try {
            webElement.findElement(selector);
        } catch (NoSuchElementException e) {
            result = false;
        }
        return result;
    }

    public static boolean containClass(WebElement webElement, String classValue) {
        if (webElement == null) {
            throw new IllegalArgumentException("webElement is null");
        }
        return StrUtil.equals(webElement.getAttribute(LabelAttributeName.CLASS), classValue);
    }

    public static final List<WebElement> getChildElements(WebElement webElement) {
        if (webElement == null) {
            throw new IllegalArgumentException("webElement is null");
        }
        List<WebElement> elementList = null;
        try {
            String xpath = "./*";
            elementList = webElement.findElements(By.xpath(xpath));
        } catch (NoSuchElementException e) {
            //...
        }
        return elementList;
    }

    public static String getChromeDriverPath() {
        String os = SystemUtil.get(SystemUtil.OS_NAME);
        String chromeDriverPath = ElementUtil.class.getResource("/").getPath() + "driver" + File.separator;
        if (os.toLowerCase().startsWith("windows")) {
            chromeDriverPath = chromeDriverPath + Constants.CHROME_DRIVER_FILE_NAME;
        } else {
            throw new IllegalArgumentException("不支持的操作系统，暂时只支持windows.");
        }
        return chromeDriverPath;
    }


    public static String getChromePath() {
        String os = SystemUtil.get(SystemUtil.OS_NAME);
        String chromePath = ElementUtil.class.getResource("/").getPath() + "google" + File.separator;
        if (os.toLowerCase().startsWith("windows")) {
            chromePath = chromePath + Constants.CHROME_FILE_NAME;
        } else {
            throw new IllegalArgumentException("不支持的操作系统，暂时只支持windows.");
        }
        return chromePath;
    }
}
