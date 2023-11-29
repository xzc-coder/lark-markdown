package com.github.lark.markdown.utils;

import com.github.lark.markdown.Constants;

/**
 * @Author: xy-code
 * @Description: Markdown相关的工具类
 * @Date: 2023-10-29 16:27
 **/
public class MarkdownUtil {

    private MarkdownUtil() {
    }


    public static String replaceMdPlaceholderContent(String symbolContent, String content) {
        return symbolContent.replace(Constants.MD_CONTENT_PLACEHOLDER, content);
    }

    public static String replaceMdPlaceholderContent(String symbolContent, String... contents) {
        String[] strings = symbolContent.split(Constants.MD_CONTENT_PLACEHOLDER);
        StringBuilder contentBuilder = new StringBuilder();
        for (int i = 0; i < strings.length - 1; i++) {
            contentBuilder.append(strings[i]);
            contentBuilder.append(contents[i]);
        }
        contentBuilder.append(strings[strings.length - 1]);
        return contentBuilder.toString();
    }
}
