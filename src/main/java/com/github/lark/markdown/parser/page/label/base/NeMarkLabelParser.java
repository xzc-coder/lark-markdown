package com.github.lark.markdown.parser.page.label.base;

import cn.hutool.core.util.StrUtil;
import com.github.lark.markdown.parser.page.label.AbstractLabelParser;
import com.github.lark.markdown.parser.page.label.LabelAttributeName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
/**
 * @Author: xy-code
 * @Description: ne-mark 标签解析器
 * @Date: 2023-10-29 16:27
 **/
public class NeMarkLabelParser extends AbstractLabelParser {


    private static final NeMarkLabelParser instance = new NeMarkLabelParser();


    public static NeMarkLabelParser getInstance() {
        return instance;
    }

    private NeMarkLabelParser() {
        this.labelParserList.add(SpanLabelParser.getInstance());
        this.labelParserList.add(NeTextLabelParser.getInstance());
        this.labelParserList.add(LinkLabelParser.getInstance());
        this.labelParserList.add(BrLabelParser.getInstance());
    }

    @Override
    public boolean isParser(WebElement webElement) {
        return StrUtil.equals(LabelAttributeName.NE_MARK,webElement.getTagName());
    }

    @Override
    public String parse(WebElement webElement) {
        String contentXpath = "./ne-mark-content";
        WebElement contentWebElement = webElement.findElement(By.xpath(contentXpath));
        String content = parseChildElement(contentWebElement);
        return content;
    }
}
