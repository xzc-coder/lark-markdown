package com.github.lark.markdown.parser.page.label.complex;

import cn.hutool.core.util.StrUtil;
import com.github.lark.markdown.Constants;
import com.github.lark.markdown.parser.page.label.LabelAttributeName;
import org.openqa.selenium.WebElement;

import java.util.Collections;

/**
 * @Author: xy-code
 * @Description: ne-h2 标签解析器
 * @Date: 2023-10-29 16:27
 **/
public class NeH2LabelParser extends TitleLabelParser {

    private static final int MD_TITLE_LEVEL = 3;

    @Override
    public boolean isParser(WebElement webElement) {
        return StrUtil.equals(LabelAttributeName.NE_H2,webElement.getTagName());
    }


    @Override
    protected String doParse(WebElement webElement, String content) {
        content = StrUtil.join("", Collections.nCopies(MD_TITLE_LEVEL, Constants.TITLE_SYMBOL)) + Constants.SPACE + content;
        return content + Constants.NEXT_ROW;
    }
}
