package com.github.lark.markdown.parser.page.label.base;

import cn.hutool.core.util.StrUtil;
import com.github.lark.markdown.parser.page.label.AbstractLabelParser;
import com.github.lark.markdown.parser.page.label.LabelAttributeName;
import org.openqa.selenium.WebElement;
/**
 * @Author: xy-code
 * @Description: span 标签解析器
 * @Date: 2023-10-29 16:27
 **/
public class SpanLabelParser extends AbstractLabelParser {


    private static final SpanLabelParser instance = new SpanLabelParser();

    public static SpanLabelParser getInstance() {
        return instance;
    }

    private SpanLabelParser() {
        this.labelParserList.add(BrLabelParser.getInstance());
    }

    @Override
    public boolean isParser(WebElement webElement) {
        return StrUtil.equals(LabelAttributeName.SPAN,webElement.getTagName());
    }

    @Override
    public String parse(WebElement webElement) {
        String content = parseChildElement(webElement);
        if(StrUtil.isEmpty(content)) {
            content = webElement.getText();
        }
        return content;
    }
}
