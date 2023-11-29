package com.github.lark.markdown.parser.page.label.base;

import com.github.lark.markdown.Constants;
import com.github.lark.markdown.parser.page.label.AbstractLabelParser;
import com.github.lark.markdown.parser.page.label.LabelParser;
import org.openqa.selenium.WebElement;
/**
 * @Author: xy-code
 * @Description: 文本组合 标签解析器
 * @Date: 2023-10-29 16:27
 **/
public class TextMixLabelParser extends AbstractLabelParser {


    private static final TextMixLabelParser instance = new TextMixLabelParser();


    public static TextMixLabelParser getInstance() {
        return instance;
    }

    private TextMixLabelParser() {
        this.labelParserList.add(SpanLabelParser.getInstance());
        this.labelParserList.add(NeTextLabelParser.getInstance());
        this.labelParserList.add(LinkLabelParser.getInstance());
        this.labelParserList.add(BrLabelParser.getInstance());
        this.labelParserList.add(NeMarkLabelParser.getInstance());
    }

    @Override
    public boolean isParser(WebElement webElement) {
        for(LabelParser labelParser : this.labelParserList) {
            if(labelParser.isParser(webElement)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String parse(WebElement webElement) {
        String content = Constants.EMPTY;
        for(LabelParser labelParser : this.labelParserList) {
            if(labelParser.isParser(webElement)) {
                content = labelParser.parse(webElement);
                break;
            }
        }
        return content;
    }
}
