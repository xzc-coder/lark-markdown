package com.github.lark.markdown.parser.page.label;

import org.openqa.selenium.WebElement;
/**
 * @Author: xy-code
 * @Description: 标签解析器接口
 * @Date: 2023-10-29 16:27
 **/
public interface LabelParser {

    /**
     * 是否能解析
     * @param webElement 元素
     * @return 是否能解析 true 能 false 不能
     */
    boolean isParser(WebElement webElement);

    /**
     * 进行解析操作
     * @param webElement 元素
     * @return 解析后的结果
     */
    String parse(WebElement webElement);
}
