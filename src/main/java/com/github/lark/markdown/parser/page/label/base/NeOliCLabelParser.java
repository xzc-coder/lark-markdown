package com.github.lark.markdown.parser.page.label.base;

import cn.hutool.core.util.StrUtil;
import com.github.lark.markdown.parser.page.label.AbstractLabelParser;
import com.github.lark.markdown.parser.page.label.LabelAttributeName;
import com.github.lark.markdown.parser.page.label.complex.ImageLabelParser;
import org.openqa.selenium.WebElement;
/**
 * @Author: xy-code
 * @Description: ne-oli-c 标签解析器
 * @Date: 2023-10-29 16:27
 **/
public class NeOliCLabelParser extends AbstractLabelParser {


    private static final NeOliCLabelParser instance = new NeOliCLabelParser();


    public static NeOliCLabelParser getInstance() {
        return instance;
    }

    private NeOliCLabelParser() {
        this.labelParserList.add(TextMixLabelParser.getInstance());
        this.labelParserList.add(ImageLabelParser.getInstance());
    }

    @Override
    public boolean isParser(WebElement webElement) {
        return StrUtil.equals(LabelAttributeName.NE_OLI_C,webElement.getTagName());
    }

    @Override
    public String parse(WebElement webElement) {
        return parseChildElement(webElement);
    }
}
