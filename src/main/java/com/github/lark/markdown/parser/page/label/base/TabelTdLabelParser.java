package com.github.lark.markdown.parser.page.label.base;

import cn.hutool.core.util.StrUtil;
import com.github.lark.markdown.parser.page.label.AbstractLabelParser;
import com.github.lark.markdown.parser.page.label.LabelAttributeName;
import com.github.lark.markdown.parser.page.label.complex.ImageLabelParser;
import com.github.lark.markdown.parser.page.label.complex.NeParagraphLabelParser;
import com.github.lark.markdown.parser.page.label.complex.NeQuoteLabelParser;
import com.github.lark.markdown.utils.ElementUtil;
import org.openqa.selenium.WebElement;
/**
 * @Author: xy-code
 * @Description: 表格Td 标签解析器
 * @Date: 2023-10-29 16:27
 **/
public class TabelTdLabelParser extends AbstractLabelParser {


    private static final TabelTdLabelParser instance = new TabelTdLabelParser();

    public static TabelTdLabelParser getInstance() {
        return instance;
    }

    private TabelTdLabelParser() {
        this.labelParserList.add(new TabelTdContentLabelParser());
        this.labelParserList.add(new TabelTdParagraphLabelParser());
    }

    @Override
    public boolean isParser(WebElement webElement) {
        return StrUtil.equals(LabelAttributeName.TD,webElement.getTagName());
    }

    private class TabelTdContentLabelParser extends AbstractLabelParser {

        public TabelTdContentLabelParser() {
            this.labelParserList.add(NeQuoteLabelParser.getInstance());
            this.labelParserList.add(NeParagraphLabelParser.getInstance());
        }

        @Override
        public boolean isParser(WebElement webElement) {
            final String classValue = "ne-td-content";
            return StrUtil.equals(LabelAttributeName.DIV,webElement.getTagName()) && ElementUtil.containClass(webElement,classValue);
        }
    }

    private class TabelTdParagraphLabelParser extends AbstractLabelParser {

        public TabelTdParagraphLabelParser() {
            this.labelParserList.add(NeTextLabelParser.getInstance());
            this.labelParserList.add(LinkLabelParser.getInstance());
            this.labelParserList.add(ImageLabelParser.getInstance());
            this.labelParserList.add(NeMarkLabelParser.getInstance());
            this.labelParserList.add(LineCodeLabelParser.getInstance());
        }

        @Override
        public boolean isParser(WebElement webElement) {
            return StrUtil.equals(LabelAttributeName.NE_P,webElement.getTagName());
        }
    }
}
