package com.github.lark.markdown.parser;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.NumberUtil;
import com.github.lark.markdown.Constants;
import com.github.lark.markdown.controller.MainController;
import com.github.lark.markdown.model.MarkdownUrlItem;
import com.github.lark.markdown.parser.page.label.LabelParser;
import com.github.lark.markdown.parser.page.label.complex.*;
import com.github.lark.markdown.utils.ElementUtil;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xy-code
 * @Description: 内容解析器
 * @Date: 2023-10-29 16:27
 **/
public class ContentParser {

    private List<LabelParser> labelParserList = new ArrayList<>();

    public ContentParser() {
        this.labelParserList.add(new NeH1LabelParser());
        this.labelParserList.add(new NeH2LabelParser());
        this.labelParserList.add(new NeH3LabelParser());
        this.labelParserList.add(new NeH4LabelParser());
        this.labelParserList.add(NeParagraphLabelParser.getInstance());
        this.labelParserList.add(NeQuoteLabelParser.getInstance());
        this.labelParserList.add(NeOliParser.getInstance());
        this.labelParserList.add(NeUliLabelParser.getInstance());
        this.labelParserList.add(NeHoleCodeLabelParser.getInstance());
        this.labelParserList.add(SplitLineLabelParser.getInstance());
        this.labelParserList.add(ImageLabelParser.getInstance());
        this.labelParserList.add(TableLabelParser.getInstance());
    }

    /**
     * 解析url并最终写入文件目录
     * @param markdownUrlItem 文章的对象信息
     * @param contentElement 内容元素
     * @param title 标题
     * @param filePath 要写入的文件目录
     * @param mainController MainController
     */
    public void parseAndWrite(MarkdownUrlItem markdownUrlItem, WebElement contentElement, String title, String filePath, MainController mainController) {
        //获取所有下级标签
        FileWriter fileWriter = new FileWriter(new File(filePath), Charset.defaultCharset());
        //先添加标题
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append(Constants.TITLE_SYMBOL).append(Constants.SPACE).append(title).append(Constants.NEXT_ROW).append(Constants.NEXT_ROW);
        List<WebElement> elementList = ElementUtil.getChildElements(contentElement);
        int count = elementList.size();
        int current = 0;
        int stopNumber = count;
        if(CollUtil.isNotEmpty(elementList)) {
            for(WebElement webElement : elementList) {
                LabelParser labelParser = getParagraphParser(webElement);
                if(labelParser != null) {
                    String content = labelParser.parse(webElement);
                    if(content != null) {
                        contentBuilder.append(content);
                    }
                }
                current++;
                markdownUrlItem.setProgress((int) (NumberUtil.div(current,count) * 100));
                mainController.tableRefresh();
                if(stopNumber == current) {
                    break;
                }
            }
        }
        fileWriter.write(contentBuilder.toString());
    }


    /**
     * 获取元素的解析器
     * @param webElement 元素
     * @return 能解析的解析器
     */
    private LabelParser getParagraphParser(WebElement webElement) {
        LabelParser result = null;
        for(LabelParser paragraphLabelParser : this.labelParserList) {
            if(paragraphLabelParser.isParser(webElement)) {
                result = paragraphLabelParser;
                break;
            }
        }
        return result;
    }

}
