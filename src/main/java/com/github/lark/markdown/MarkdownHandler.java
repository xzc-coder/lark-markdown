package com.github.lark.markdown;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.log.Log;
import com.github.lark.markdown.controller.MainController;
import com.github.lark.markdown.enums.MarkdownItemStatus;
import com.github.lark.markdown.model.MarkdownUrlItem;
import com.github.lark.markdown.parser.ContentParser;
import com.github.lark.markdown.parser.TitleParser;
import com.github.lark.markdown.utils.ElementUtil;
import com.github.lark.markdown.utils.FxUtil;
import com.github.lark.markdown.utils.PropertiesUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: xy-code
 * @Description: Markdown的处理器
 * @Date: 2023-10-29 16:27
 **/
public class MarkdownHandler {

    private static final Log log = Log.get();
    private TitleParser titleParser = new TitleParser();
    private ContentParser contentParser = new ContentParser();
    private MainController mainController;
    private AtomicInteger serialNumberValue = new AtomicInteger(1);
    private BlockingQueue<MarkdownUrlItem> markdownUrlItemBlockingQueue = new LinkedBlockingQueue<>(Integer.MAX_VALUE);
    private ExecutorService pool = Executors.newFixedThreadPool(1);

    public MarkdownHandler(MainController mainController) {
        this.mainController = mainController;
        pool.execute(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                MarkdownUrlItem markdownUrlItem;
                try {
                    markdownUrlItem = markdownUrlItemBlockingQueue.take();
                } catch (InterruptedException interruptedException) {
                    throw new RuntimeException(interruptedException);
                }
                try {
                    handle(markdownUrlItem);
                } catch (Exception e) {
                    log.error("{} 无法解析，处理发生异常。", markdownUrlItem.getUrl());
                    log.error("错误详情：", e);
                    markdownUrlItem.setStatus(MarkdownItemStatus.ERROR.getStatus());
                    markdownUrlItem.setErrorMessage(e.getMessage());
                    mainController.tableRefresh();
                }
            }
        });
    }

    /**
     * 处理一个语雀文章条目
     *
     * @param markdownUrlItem 语雀文章条目
     * @throws IOException
     */
    private void handle(MarkdownUrlItem markdownUrlItem) throws IOException {
        String url = markdownUrlItem.getUrl();
        log.info("开始处理解析语雀文章：{}", url);
        //静默模式
//        // 谷歌驱动路径
        String chromeDriverPath = FxUtil.getResourcesPath(Constants.CHROME_DRIVER_FILE_NAME);
        String chromePath = FxUtil.getResourcesPath(Constants.CHROME_FILE_NAME);
        System.setProperty(Constants.CHROME_DRIVER_KEY, chromeDriverPath);
        Properties properties = PropertiesUtil.loadProperties(FxUtil.getResourcesPath(Constants.CONTENT_FILE_NAME));
        Integer browserWaiteTimeSecond = Integer.parseInt(properties.getProperty(Constants.BROWSER_WAITE_TIME_SECOND_KEY));
        Integer browserRollIntervalMill = Integer.parseInt(properties.getProperty(Constants.BROWSER_ROLL_INTERVAL_MILL_KEY));
        String markdownSavePath = properties.getProperty(Constants.MARKDOWN_SAVE_PATH_KEY);
        markdownUrlItem.setSavePath(markdownSavePath);
        // ChromeOptions
        ChromeOptions chromeOptions = new ChromeOptions();
        // 设置后台静默模式启动浏览器
        chromeOptions.setHeadless(true);
        chromeOptions.setBinary(new File(chromePath));
        ChromeDriver driver = new ChromeDriver(chromeOptions);
        //最大化窗口
        driver.manage().window().maximize();
        markdownUrlItem.setStatus(MarkdownItemStatus.LOADING.getStatus());
        mainController.tableRefresh();
        //显式等待
        WebDriverWait driverWait = new WebDriverWait(driver, browserWaiteTimeSecond);
        try {
            ElementUtil.setDriver(driver);
            driver.get(url);
            String pageCompleteXpath = "//article[@id='content'] |  //div[@class='clearfix layout-container']";
            try {
                driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pageCompleteXpath)));
            } catch (Exception e) {
                log.info("{} 路径信息异常：{}", url, e.getMessage());
                throw new RuntimeException("页面无法获取到有效元素，请检查url");
            }
            //查看获取的是正常内容页面
            String articleXpathXpath = "//article[@id='content']";
            WebElement articleElement;
            try {
                articleElement = driver.findElement(By.xpath(articleXpathXpath));
            } catch (NoSuchElementException e) {
                log.info("{} 无权访问.", url);
                throw new RuntimeException("无权访问该url");
            }
            //滚动到底部 加载数据
            rollToBottom(driver, browserRollIntervalMill);
            //再滑动到顶部
            ElementUtil.scrollToTop(driver);
            ThreadUtil.sleep(500);
            //开始解析
            //1.解析标题
            log.info("---开始处理语雀文章标题解析---");
            String titleXpath = "//h1[@id='article-title']";
            WebElement titleElement = articleElement.findElement(By.xpath(titleXpath));
            String title = this.titleParser.parse(titleElement);
            markdownUrlItem.setTitle(title);
            log.info("---处理语雀文章标题完成：{}---", title);
            //2.解析内容并写入
            log.info("---开始处理语雀文章内容解析---");
            String contentXpath = "//div[@class='ne-viewer-body']";
            WebElement contentElement = articleElement.findElement(By.xpath(contentXpath));
            markdownUrlItem.setStatus(MarkdownItemStatus.START.getStatus());
            mainController.tableRefresh();
            String fileName = FileUtil.cleanInvalid(title);
            markdownSavePath = markdownSavePath + "/" + fileName;
            FileUtil.mkdir(markdownSavePath);
            ElementUtil.setFileDir(markdownSavePath);
            String markdownSaveFile = markdownSavePath + "/" + fileName + ".md";
            this.contentParser.parseAndWrite(markdownUrlItem, contentElement, title, markdownSaveFile, mainController);
            markdownUrlItem.setStatus(MarkdownItemStatus.COMPLETE.getStatus());
            log.info("处理解析语雀文章完成：{}", url);
            mainController.tableRefresh();
        } finally {
            driver.quit();
            ElementUtil.removeDriver();
            ElementUtil.removeFileDir();
        }
    }

    private void rollToBottom(ChromeDriver driver, Integer browserRollIntervalMill) {
        long roll = 1500;
        Number beforeHeight = 0L;
        for (; ; ) {
            driver.executeScript(String.format("window.scrollBy(0,%d)", roll));
            //滚动间隔可以设置
            ThreadUtil.sleep(browserRollIntervalMill);
            Number afterHeight = (Number) driver.executeScript("return document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;");
            if (Objects.equals(beforeHeight, afterHeight)) {
                return;
            }
            beforeHeight = afterHeight;
        }
    }

    /**
     * 生成一个自增序号
     *
     * @return 序号
     */
    public int produceSerialNumber() {
        return serialNumberValue.getAndIncrement();
    }

    /**
     * 添加一个语雀文章条目到处理器
     *
     * @param markdownUrlItem 语雀文章条目
     */
    public void addTask(MarkdownUrlItem markdownUrlItem) {
        markdownUrlItemBlockingQueue.offer(markdownUrlItem);
    }


}
