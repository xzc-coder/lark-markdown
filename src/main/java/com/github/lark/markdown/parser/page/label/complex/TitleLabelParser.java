package com.github.lark.markdown.parser.page.label.complex;

import com.github.lark.markdown.parser.page.label.AbstractLabelParser;
import com.github.lark.markdown.parser.page.label.base.NeHeadingContentLabelParser;
/**
 * @Author: xy-code
 * @Description: 标题 标签解析器
 * @Date: 2023-10-29 16:27
 **/
public abstract class TitleLabelParser extends AbstractLabelParser {

    public TitleLabelParser() {
        this.labelParserList.add(NeHeadingContentLabelParser.getInstance());
    }
}
