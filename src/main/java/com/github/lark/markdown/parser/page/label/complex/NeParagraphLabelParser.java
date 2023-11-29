package com.github.lark.markdown.parser.page.label.complex;

import cn.hutool.core.util.StrUtil;
import com.github.lark.markdown.Constants;
import com.github.lark.markdown.parser.page.label.AbstractLabelParser;
import com.github.lark.markdown.parser.page.label.LabelAttributeName;
import com.github.lark.markdown.parser.page.label.base.LineCodeLabelParser;
import com.github.lark.markdown.parser.page.label.base.TextMixLabelParser;
import org.openqa.selenium.WebElement;
/**
 * @Author: xy-code
 * @Description: ne-p 标签解析器
 * @Date: 2023-10-29 16:27
 **/
public class NeParagraphLabelParser extends AbstractLabelParser {


    private static final NeParagraphLabelParser instance = new NeParagraphLabelParser();


    public static NeParagraphLabelParser getInstance() {
        return instance;
    }

    private NeParagraphLabelParser() {
        this.labelParserList.add(NeUliLabelParser.getInstance());
        this.labelParserList.add(NeOliParser.getInstance());
        this.labelParserList.add(TextMixLabelParser.getInstance());
        this.labelParserList.add(ImageLabelParser.getInstance());
        this.labelParserList.add(LineCodeLabelParser.getInstance());

    }

    @Override
    public boolean isParser(WebElement webElement) {
        return StrUtil.equals(LabelAttributeName.NE_P,webElement.getTagName());
    }

    @Override
    protected String parseChildElement(WebElement webElement) {
        return super.parseChildElement(webElement) + Constants.NEXT_ROW;
    }
}
