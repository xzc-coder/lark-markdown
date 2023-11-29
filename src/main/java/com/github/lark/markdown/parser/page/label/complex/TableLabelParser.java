package com.github.lark.markdown.parser.page.label.complex;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.lark.markdown.Constants;
import com.github.lark.markdown.parser.page.label.AbstractLabelParser;
import com.github.lark.markdown.parser.page.label.LabelAttributeName;
import com.github.lark.markdown.parser.page.label.LabelParser;
import com.github.lark.markdown.parser.page.label.base.TabelTrLabelParser;
import com.github.lark.markdown.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @Author: xy-code
 * @Description: 表格 标签解析器
 * @Date: 2023-10-29 16:27
 **/
public class TableLabelParser extends AbstractLabelParser {

    private static final TableLabelParser instance = new TableLabelParser();


    public static TableLabelParser getInstance() {
        return instance;
    }

    private TableLabelParser() {
        this.labelParserList.add(TabelTrLabelParser.getInstance());
    }

    @Override
    public boolean isParser(WebElement webElement) {
        return StrUtil.equals(LabelAttributeName.NE_TABLE_HOLE,webElement.getTagName());
    }

    @Override
    public String parse(WebElement webElement) {
        String tableBodyXpath = ".//tbody";
        WebElement tableBodyElement = webElement.findElement(By.xpath(tableBodyXpath));
        List<WebElement> childElements = ElementUtil.getChildElements(tableBodyElement);
        StringBuilder contentBuilder = new StringBuilder();
        if(CollUtil.isNotEmpty(childElements)) {
            int trNum = childElements.size();
            final int alignRow = 1;
            //获取有多少列
            WebElement firstRowElement = childElements.get(0);
            int tdNum = ElementUtil.getChildElements(firstRowElement).size();
            for(int i = 0;i < trNum;i++) {
                if(i == alignRow) {
                    contentBuilder.append(alignStr(tdNum));
                }
                WebElement childElement = childElements.get(i);
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


    private String alignStr(int tdNum) {
        StringBuilder str = new StringBuilder();
        str.append(Constants.TABLE_SYMBOL);
        for(int i = 0;i < tdNum;i++) {
            str.append(Constants.TABLE_ALIGN_SYMBOL).append(Constants.TABLE_SYMBOL);
        }
        return str.append(Constants.NEXT_ROW).toString();
    }
}
