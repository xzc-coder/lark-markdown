package com.github.lark.markdown.parser.page.label.complex;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import com.github.lark.markdown.Constants;
import com.github.lark.markdown.parser.page.label.AbstractLabelParser;
import com.github.lark.markdown.parser.page.label.LabelAttributeName;
import com.github.lark.markdown.utils.ElementUtil;
import com.github.lark.markdown.utils.ImageUtil;
import com.github.lark.markdown.utils.MarkdownUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
/**
 * @Author: xy-code
 * @Description: 图片 标签解析器
 * @Date: 2023-10-29 16:27
 **/
public class ImageLabelParser extends AbstractLabelParser {


    private static final Log log = Log.get();
    private static final ImageLabelParser instance = new ImageLabelParser();


    public static ImageLabelParser getInstance() {
        return instance;
    }


    private ImageLabelParser() {
    }

    @Override
    public boolean isParser(WebElement webElement) {
        String dataCardNameValue = "image";
        return StrUtil.equals(LabelAttributeName.NE_CARD, webElement.getTagName()) && dataCardNameValue.equals(webElement.getAttribute(LabelAttributeName.DATA_CARD_NAME));
    }


    @Override
    public String parse(WebElement webElement) {
        String imageXpath = ".//img";
        WebElement imageElement = webElement.findElement(By.xpath(imageXpath));
        String imageTitle = getImageTitle(webElement);
        String src = imageElement.getAttribute(LabelAttributeName.SRC);
        String fileName = null;
        try {
            fileName = saveImage(src);
        }catch (Exception e) {
            log.info("图片解析异常，src：{}",src);
        }
        if(StrUtil.isBlank(fileName)) {
            return Constants.EMPTY;
        }else {
            String mdImage = "./" + Constants.IMAGE_DIR_NAME + "/" + fileName;
            String content = MarkdownUtil.replaceMdPlaceholderContent(Constants.IMAGE_SYMBOL, imageTitle, mdImage);
            return content;
        }
    }

    private String getImageTitle(WebElement webElement) {
        String imageTitle = Constants.IMAGE_DEFAULT_TITLE;
        String imageTitleXpath = ".//div[contains(@class,'ne-image-title-content')]";
        if (ElementUtil.elementIsExists(webElement, By.xpath(imageTitleXpath))) {
            imageTitle = webElement.findElement(By.xpath(imageTitleXpath)).getText();
        }
        return imageTitle;
    }

    //    private void saveImage(String imageSrc) {
//        URL url = null;
//        try {
//            url = new URL(imageSrc);
//            try(InputStream inputStream = url.openStream();) {
//                Files.copy(inputStream, Paths.get(ElementUtil.getFileDir()), StandardCopyOption.REPLACE_EXISTING);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
    private String saveImage(String imageSrc) {
        ChromeDriver driver = ElementUtil.getDriver();
        String url = imageSrc; // 替换为你要发送GET请求的URL
        JavascriptExecutor js = driver;
        String base64 = (String) js.executeScript("return fetch('" + url + "').then(response => response.blob()).then(blob => { return new Promise((resolve, reject) => { const reader = new FileReader(); reader.onloadend = () => { resolve(reader.result);  }; reader.readAsDataURL(blob);});}).then(dataUrl => { return dataUrl;});");
        //取掉前缀
        base64 = base64.split(",")[1];
        byte[] bytes = Base64.decode(base64);
        // 获取文件头信息
        final int headerLength = 4;
        byte[] header = new byte[headerLength];
        System.arraycopy(bytes, 0, header, 0, headerLength);
        // 根据文件头判断文件类型
        String fileType = ImageUtil.getFileType(header);
        if(StrUtil.isNotBlank(fileType)) {
            String filename = RandomUtil.randomString(15) + "." + fileType;
            String filePath = ElementUtil.getFileDir() + "/" + Constants.IMAGE_DIR_NAME + "/" + filename;
            FileUtil.writeBytes(bytes, filePath);
            return filename;
        }else {
            log.info("无法解析该图片类型,src: {} ,base64：{}",imageSrc,base64);
            return null;
        }
    }
}
