package com.github.lark.markdown.parser.page.label.base;

import cn.hutool.core.util.StrUtil;
import com.github.lark.markdown.Constants;
import com.github.lark.markdown.parser.page.label.AbstractLabelParser;
import com.github.lark.markdown.parser.page.label.LabelAttributeName;
import org.openqa.selenium.WebElement;

/**
 * @Author: xy-code
 * @Description: Br标签解析器
 * @Date: 2023-10-29 16:27
 **/
public class BrLabelParser extends AbstractLabelParser {

    private static final BrLabelParser instance = new BrLabelParser();


    public static BrLabelParser getInstance() {
        return instance;
    }

    private BrLabelParser() {
    }

    @Override
    public boolean isParser(WebElement webElement) {
        return StrUtil.equals(LabelAttributeName.BR,webElement.getTagName());
    }

    @Override
    public String parse(WebElement webElement) {
        return Constants.NEXT_ROW;
    }
}
