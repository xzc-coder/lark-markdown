package com.github.lark.markdown.parser.page.label.base;

import cn.hutool.core.util.StrUtil;
import com.github.lark.markdown.Constants;
import com.github.lark.markdown.parser.page.label.AbstractLabelParser;
import com.github.lark.markdown.parser.page.label.LabelAttributeName;
import com.github.lark.markdown.utils.MarkdownUtil;
import org.openqa.selenium.WebElement;

/**
 * @Author: xy-code
 * @Description: ne-code 标签解析器
 * @Date: 2023-10-29 16:27
 **/
public class LineCodeLabelParser extends AbstractLabelParser {

    private static final LineCodeLabelParser instance = new LineCodeLabelParser();


    public static LineCodeLabelParser getInstance() {
        return instance;
    }

    private LineCodeLabelParser() {
        this.labelParserList.add(new LineCodeContentLabelParser());
    }

    @Override
    protected String doParse(WebElement webElement, String content) {
        return MarkdownUtil.replaceMdPlaceholderContent(Constants.CODE_BLOCK_SYMBOL,content);
    }

    @Override
    public boolean isParser(WebElement webElement) {
        return StrUtil.equals(LabelAttributeName.NE_CODE,webElement.getTagName());
    }

    private class LineCodeContentLabelParser extends AbstractLabelParser {

        @Override
        public boolean isParser(WebElement webElement) {
            return StrUtil.equals(LabelAttributeName.NE_CODE_CONTENT,webElement.getTagName());
        }

        @Override
        public String parse(WebElement webElement) {
            return webElement.getAttribute(LabelAttributeName.INNER_TEXT);
        }
    }
}
