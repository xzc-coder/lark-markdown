package com.github.lark.markdown.parser.page.label.complex;

import cn.hutool.core.util.StrUtil;
import com.github.lark.markdown.Constants;
import com.github.lark.markdown.parser.page.label.AbstractLabelParser;
import com.github.lark.markdown.parser.page.label.LabelAttributeName;
import com.github.lark.markdown.parser.page.label.base.NeOliCLabelParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
/**
 * @Author: xy-code
 * @Description: ne-oli-i 标签解析器
 * @Date: 2023-10-29 16:27
 **/
public class NeOliParser extends AbstractLabelParser {


    private static final NeOliParser instance = new NeOliParser();


    public static NeOliParser getInstance() {
        return instance;
    }

    private NeOliParser() {
        this.labelParserList.add(NeOliCLabelParser.getInstance());
    }

    @Override
    public boolean isParser(WebElement webElement) {
        return StrUtil.equals(LabelAttributeName.NE_OLI,webElement.getTagName());
    }

    @Override
    protected String doParse(WebElement webElement, String content) {
        //获取序号
        String orderXpath = "./ne-oli-i//span[last()]";
        String orderNumber = webElement.findElement(By.xpath(orderXpath)).getText();
        content = orderNumber + Constants.ORDERED_LIST_SYMBOL + content;
        return content;
    }
}
