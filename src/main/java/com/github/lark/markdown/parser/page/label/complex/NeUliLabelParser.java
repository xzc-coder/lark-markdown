package com.github.lark.markdown.parser.page.label.complex;

import cn.hutool.core.util.StrUtil;
import com.github.lark.markdown.Constants;
import com.github.lark.markdown.parser.page.label.AbstractLabelParser;
import com.github.lark.markdown.parser.page.label.LabelAttributeName;
import com.github.lark.markdown.parser.page.label.base.NeUliCLabelParser;
import org.openqa.selenium.WebElement;
/**
 * @Author: xy-code
 * @Description: ne-uli标签解析器
 * @Date: 2023-10-29 16:27
 **/
public class NeUliLabelParser extends AbstractLabelParser {

    private static final NeUliLabelParser instance = new NeUliLabelParser();


    public static NeUliLabelParser getInstance() {
        return instance;
    }

    private NeUliLabelParser() {
        this.labelParserList.add(NeUliCLabelParser.getInstance());
    }

    @Override
    public boolean isParser(WebElement webElement) {
        return StrUtil.equals(LabelAttributeName.NE_ULI, webElement.getTagName());
    }

    @Override
    protected String doParse(WebElement webElement, String content) {
        String level = webElement.getAttribute(LabelAttributeName.NE_LEVEL);
        if (StrUtil.isNotBlank(level)) {
            int levelVal = Integer.parseInt(level);
            StringBuilder prefix = new StringBuilder();
            for (int i = 0; i < levelVal; i++) {
                prefix.append(Constants.TAB);
            }
            return prefix + Constants.DISORDER_LIST_SYMBOL + content;
        } else {
            return Constants.DISORDER_LIST_SYMBOL + content;
        }
    }
}
