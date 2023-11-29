package com.github.lark.markdown.parser.page.label.complex;

import cn.hutool.core.util.StrUtil;
import com.github.lark.markdown.Constants;
import com.github.lark.markdown.parser.page.label.AbstractLabelParser;
import com.github.lark.markdown.parser.page.label.LabelAttributeName;
import com.github.lark.markdown.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
/**
 * @Author: xy-code
 * @Description: 分割 标签解析器
 * @Date: 2023-10-29 16:27
 **/
public class SplitLineLabelParser extends AbstractLabelParser {

    private final static String SPLIT_LINE_XPATH = ".//div[contains(@class,'ne-hr-line')]";

    private static final SplitLineLabelParser instance = new SplitLineLabelParser();


    public static SplitLineLabelParser getInstance() {
        return instance;
    }

    private SplitLineLabelParser() {

    }

    @Override
    public boolean isParser(WebElement webElement) {
        return StrUtil.equals(LabelAttributeName.NE_HOLE,webElement.getTagName()) && ElementUtil.elementIsExists(webElement, By.xpath(SPLIT_LINE_XPATH));
    }

    @Override
    public String parse(WebElement webElement) {
        return Constants.SPLIT_LINE_SYMBOL + Constants.NEXT_ROW;
    }
}
