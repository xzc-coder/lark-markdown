package com.github.lark.markdown.parser;

import org.openqa.selenium.WebElement;
/**
 * @Author: xy-code
 * @Description: 标题解析器
 * @Date: 2023-10-29 16:27
 **/
public class TitleParser {

    /**
     * 解析返回标题
     * @param titleElement 标题元素
     * @return 标题内容
     */
    public String parse(WebElement titleElement) {
        return titleElement.getText();
    }
}
