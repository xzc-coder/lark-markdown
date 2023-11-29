package com.github.lark.markdown.parser.page.label.complex;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.lark.markdown.Constants;
import com.github.lark.markdown.parser.page.label.AbstractLabelParser;
import com.github.lark.markdown.parser.page.label.LabelAttributeName;
import com.github.lark.markdown.parser.page.label.LabelParser;
import com.github.lark.markdown.utils.ElementUtil;
import org.openqa.selenium.WebElement;

import java.util.List;
/**
 * @Author: xy-code
 * @Description: ne-quote 标签解析器
 * @Date: 2023-10-29 16:27
 **/
public class NeQuoteLabelParser extends AbstractLabelParser {

    private static final NeQuoteLabelParser instance = new NeQuoteLabelParser();


    public static NeQuoteLabelParser getInstance() {
        return instance;
    }

    private NeQuoteLabelParser() {
        this.labelParserList.add(NeParagraphLabelParser.getInstance());
        this.labelParserList.add(NeUliLabelParser.getInstance());
        this.labelParserList.add(NeOliParser.getInstance());
    }

    @Override
    public String parse(WebElement webElement) {
        List<WebElement> childElements = ElementUtil.getChildElements(webElement);
        StringBuilder contentBuilder = new StringBuilder();
        if(CollUtil.isNotEmpty(childElements)) {
            for(WebElement childElement : childElements) {
                LabelParser labelParser = getLabelParser(childElement);
                if(labelParser != null) {
                    String childContent = labelParser.parse(childElement);
                    if(childContent != null) {
                        if(childContent.contains(Constants.NEXT_ROW)) {
                            childContent = Constants.QUOTE_SYMBOL + childContent;
                        }
                        contentBuilder.append(childContent);
                    }
                }
            }
        }
        return contentBuilder.toString();
    }

    @Override
    public boolean isParser(WebElement webElement) {
        return StrUtil.equals(LabelAttributeName.NE_QUOTE,webElement.getTagName());
    }
}
