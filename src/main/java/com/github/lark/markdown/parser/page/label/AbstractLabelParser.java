package com.github.lark.markdown.parser.page.label;

import cn.hutool.core.collection.CollUtil;
import com.github.lark.markdown.utils.ElementUtil;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
/**
 * @Author: xy-code
 * @Description: 抽象的 标签解析器
 * @Date: 2023-10-29 16:27
 **/
public abstract class AbstractLabelParser implements LabelParser {

    /**
     * 标签解析器集合
     */
    protected List<LabelParser> labelParserList = new ArrayList<>();

    @Override
    public String parse(WebElement webElement) {
        return doParse(webElement,parseChildElement(webElement));
    }

    /**
     * 解析所有字标签
     * @param webElement 当前标签
     * @return 解析后的字符串
     */
    protected String parseChildElement(WebElement webElement) {
        List<WebElement> childElements = ElementUtil.getChildElements(webElement);
        StringBuilder contentBuilder = new StringBuilder();
        if(CollUtil.isNotEmpty(childElements)) {
            for(WebElement childElement : childElements) {
                LabelParser labelParser = getLabelParser(childElement);
                if(labelParser != null) {
                    String childContent = labelParser.parse(childElement);
                    if(childContent != null) {
                        contentBuilder.append(childContent);
                    }
                }
            }
        }
        return contentBuilder.toString();
    }

    /**
     * 子类实现，自己实现解析后的操作
     * @param webElement 当前元素
     * @param content 解析后的内容
     * @return 最终的解析结果
     */
    protected String doParse(WebElement webElement, String content) {
        return content;
    }

    /**
     * 拿到能解析改标签的解析器
     * @param webElement 元素
     * @return 解析器
     */
    protected LabelParser getLabelParser(WebElement webElement) {
        LabelParser result = null;
        for(LabelParser labelParser : this.labelParserList) {
            if(labelParser.isParser(webElement)) {
                result = labelParser;
                break;
            }
        }
        return result;
    }
}
