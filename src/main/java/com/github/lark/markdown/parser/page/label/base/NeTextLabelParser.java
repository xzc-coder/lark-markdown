package com.github.lark.markdown.parser.page.label.base;

import cn.hutool.core.util.StrUtil;
import com.github.lark.markdown.Constants;
import com.github.lark.markdown.parser.page.label.AbstractLabelParser;
import com.github.lark.markdown.parser.page.label.LabelAttributeName;
import com.github.lark.markdown.utils.MarkdownUtil;
import org.openqa.selenium.WebElement;
/**
 * @Author: xy-code
 * @Description: ne-text 标签解析器
 * @Date: 2023-10-29 16:27
 **/
public class NeTextLabelParser extends AbstractLabelParser {


    private static final NeTextLabelParser instance = new NeTextLabelParser();

    public static NeTextLabelParser getInstance() {
        return instance;
    }

    private NeTextLabelParser() {
    }

    @Override
    public boolean isParser(WebElement webElement) {
        return StrUtil.equals(LabelAttributeName.NE_TEXT,webElement.getTagName());
    }

    @Override
    public String parse(WebElement webElement) {
        String text = styleHandle(webElement);
        return text;
    }


    private String styleHandle(WebElement webElement) {
        String text = webElement.getText();
        String outerHTML = webElement.getAttribute(LabelAttributeName.OUTER_HTML);
        String[] contents = outerHTML.split(Constants.HTML_NEXT_ROW);
        //判断是否包含了换行，包含则添加一个换行
        if(contents.length > 1) {
            text = text + Constants.NEXT_ROW;
        }
        //取掉文本左边的空格，否则会导致变成代码块bug
        text = StrUtil.trimStart(text);
        String bold = webElement.getAttribute(LabelAttributeName.NE_BOLD);
        String italic = webElement.getAttribute(LabelAttributeName.NE_ITALIC);
        //粗斜体
        if(StrUtil.equals(Constants.TRUE,bold) && StrUtil.equals(Constants.TRUE,italic)) {
            text = MarkdownUtil.replaceMdPlaceholderContent(Constants.BOLD_ITALIC_SYMBOL,text);
        }else if(StrUtil.equals(Constants.TRUE,bold)) {
            text = MarkdownUtil.replaceMdPlaceholderContent(Constants.BOLD_SYMBOL,text);
        }else if(StrUtil.equals(Constants.TRUE,italic)) {
            text = MarkdownUtil.replaceMdPlaceholderContent(Constants.ITALIC_SYMBOL,text);
        }
        //删除线
        String strikethrough = webElement.getAttribute(LabelAttributeName.NE_STRIKETHROUGH);
        if(StrUtil.equals(Constants.TRUE,strikethrough)) {
            text = MarkdownUtil.replaceMdPlaceholderContent(Constants.STRIKETHROUGH_SYMBOL, text);
        }
        //下划线
        String underline = webElement.getAttribute(LabelAttributeName.NE_UNDERLINE);
        if(StrUtil.equals(Constants.TRUE,underline)) {
            text = MarkdownUtil.replaceMdPlaceholderContent(Constants.UNDERLINE_SYMBOL, text);
        }
        return text;
    }
}
