package com.github.lark.markdown.parser.page.label.base;

import cn.hutool.core.util.StrUtil;
import com.github.lark.markdown.Constants;
import com.github.lark.markdown.parser.page.label.AbstractLabelParser;
import com.github.lark.markdown.parser.page.label.LabelAttributeName;
import com.github.lark.markdown.utils.MarkdownUtil;
import org.openqa.selenium.WebElement;
/**
 * @Author: xy-code
 * @Description: ne-link 标签解析器
 * @Date: 2023-10-29 16:27
 **/
public class LinkLabelParser extends AbstractLabelParser {


    private static final LinkLabelParser instance = new LinkLabelParser();


    public static LinkLabelParser getInstance() {
        return instance;
    }

    private LinkLabelParser() {
        this.labelParserList.add(SpanLabelParser.getInstance());
        this.labelParserList.add(NeTextLabelParser.getInstance());
        this.labelParserList.add(BrLabelParser.getInstance());
    }

    @Override
    public boolean isParser(WebElement webElement) {
        String classVal = "ne-link";
        return StrUtil.equals(LabelAttributeName.A,webElement.getTagName()) && classVal.equals(webElement.getAttribute(LabelAttributeName.CLASS));
    }

    @Override
    public String parse(WebElement webElement) {
        String href = webElement.getAttribute(LabelAttributeName.HREF);
        String name = parseChildElement(webElement);
        name = MarkdownUtil.replaceMdPlaceholderContent(Constants.LINK_NAME_SYMBOL,name);
        href = MarkdownUtil.replaceMdPlaceholderContent(Constants.LINK_HREF_SYMBOL,href);
        return name + href;
    }
}
