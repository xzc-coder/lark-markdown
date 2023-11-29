package com.github.lark.markdown.parser.page.label.base;

import cn.hutool.core.util.StrUtil;
import com.github.lark.markdown.parser.page.label.AbstractLabelParser;
import com.github.lark.markdown.parser.page.label.LabelAttributeName;
import org.openqa.selenium.WebElement;
/**
 * @Author: xy-code
 * @Description: ne-heading-content 标签解析器
 * @Date: 2023-10-29 16:27
 **/
public class NeHeadingContentLabelParser extends AbstractLabelParser {

    private static final NeHeadingContentLabelParser instance = new NeHeadingContentLabelParser();

    public static NeHeadingContentLabelParser getInstance() {
        return instance;
    }

    private NeHeadingContentLabelParser() {
        this.labelParserList.add(TextMixLabelParser.getInstance());
    }

    @Override
    public boolean isParser(WebElement webElement) {
        return StrUtil.equals(LabelAttributeName.NE_HEADING_CONTENT,webElement.getTagName());
    }

    @Override
    public String parse(WebElement webElement) {
        String text = this.parseChildElement(webElement);
        return text;
    }
}
