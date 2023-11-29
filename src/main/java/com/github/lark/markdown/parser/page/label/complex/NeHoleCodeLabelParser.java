package com.github.lark.markdown.parser.page.label.complex;

import cn.hutool.core.util.StrUtil;
import com.github.lark.markdown.Constants;
import com.github.lark.markdown.parser.page.label.AbstractLabelParser;
import com.github.lark.markdown.parser.page.label.LabelAttributeName;
import com.github.lark.markdown.utils.ElementUtil;
import com.github.lark.markdown.utils.MarkdownUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
/**
 * @Author: xy-code
 * @Description: 代码块 标签解析器
 * @Date: 2023-10-29 16:27
 **/
public class NeHoleCodeLabelParser extends AbstractLabelParser {

    private static final NeHoleCodeLabelParser instance = new NeHoleCodeLabelParser();


    public static NeHoleCodeLabelParser getInstance() {
        return instance;
    }

    private NeHoleCodeLabelParser() {
    }

    @Override
    public boolean isParser(WebElement webElement) {
        final String typeXpath = "./ne-card";
        final String dataCardNameValue = "codeblock";
        return StrUtil.equals(LabelAttributeName.NE_HOLE,webElement.getTagName()) && dataCardNameValue.equals(webElement.findElement(By.xpath(typeXpath)).getAttribute(LabelAttributeName.DATA_CARD_NAME));
    }

    @Override
    public String parse(WebElement webElement) {
        String language = null;
        //有两种情况，一种是有标题头的 一种是没有标题头的
        final String titleXpath = ".//span[contains(@class,'ne-codeblock-mode-name')]";
        final String contentXpath = ".//div[contains(@class,'cm-content')]";
        //滚动比较耗时，不如不要
//        ElementUtil.scrollToElementDisplayed(ElementUtil.getDriver(),By.xpath(contentXpath));
        //判断是哪种情况
        if(ElementUtil.elementIsExists(webElement,By.xpath(titleXpath))) {
            language = webElement.findElement(By.xpath(titleXpath)).getText();
        }else {
            //添加默认的语言
            language = Constants.CODE_BLOCK_DEFAULT_LANGUAGE;
        }
        String content = getContent(webElement,contentXpath);
        content = language + Constants.NEXT_ROW + content;
        content = MarkdownUtil.replaceMdPlaceholderContent(Constants.CODE_BLOCK_SYMBOL,content);
        return content + Constants.NEXT_ROW;
    }


    private String getContent(WebElement webElement,String contentXpath) {
        StringBuilder contentBuilder = new StringBuilder(Constants.EMPTY);
        String divContentXpath = contentXpath + "/div";
        List<WebElement> divElements = webElement.findElements(By.xpath(divContentXpath));
        for(WebElement divElement : divElements) {
            contentBuilder.append(divElement.getAttribute(LabelAttributeName.INNER_TEXT)).append(Constants.NEXT_ROW);
        }
        return contentBuilder.toString();
    }
}
