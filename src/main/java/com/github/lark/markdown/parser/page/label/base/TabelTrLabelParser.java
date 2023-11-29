package com.github.lark.markdown.parser.page.label.base;

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
 * @Description: 表格Tr 标签解析器
 * @Date: 2023-10-29 16:27
 **/
public class TabelTrLabelParser extends AbstractLabelParser {

    private static final TabelTrLabelParser instance = new TabelTrLabelParser();

    public static TabelTrLabelParser getInstance() {
        return instance;
    }

    private TabelTrLabelParser() {
        this.labelParserList.add(TabelTdLabelParser.getInstance());
    }

    @Override
    public boolean isParser(WebElement webElement) {
        return StrUtil.equals(LabelAttributeName.TR,webElement.getTagName());
    }

    @Override
    public String parse(WebElement webElement) {
        List<WebElement> childElements = ElementUtil.getChildElements(webElement);
        StringBuilder contentBuilder = new StringBuilder();
        if(CollUtil.isNotEmpty(childElements)) {
            contentBuilder.append(Constants.TABLE_SYMBOL);
            for(WebElement childElement : childElements) {
                LabelParser labelParser = getLabelParser(childElement);
                if(labelParser != null) {
                    String childContent = labelParser.parse(childElement);
                    if(childContent != null) {
                        contentBuilder.append(childContent).append(Constants.TABLE_SYMBOL);
                    }
                }
            }
        }
        String content = contentBuilder.toString();
        content = content.replaceAll(Constants.NEXT_ROW,"");
        return content + Constants.NEXT_ROW;
    }
}
