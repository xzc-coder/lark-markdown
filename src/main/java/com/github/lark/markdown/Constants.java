package com.github.lark.markdown;


import cn.hutool.system.SystemUtil;

/**
 * @Author: xy-code
 * @Description: 常量
 * @Date: 2023-10-29 16:27
 **/
public class Constants {

    private Constants() {
    }

    public static final String TRUE = "true";
    public static final int MAX_LEVEL = 1;
    public static final int MIN_LEVEL = 6;

    public static final String SPACE = " ";
    public static final String EMPTY = "";
    public static final String NEXT_ROW = SystemUtil.get(SystemUtil.LINE_SEPARATOR);

    public static final String TAB = "\t";
    public static final String HTML_NEXT_ROW = "\n";

    public static final String MD_CONTENT_PLACEHOLDER = "value";
    public static final String TITLE_SYMBOL = "#";
    public static final String ITALIC_SYMBOL = "*" + MD_CONTENT_PLACEHOLDER + "*";
    public static final String BOLD_SYMBOL = "**" + MD_CONTENT_PLACEHOLDER + "**";
    public static final String BOLD_ITALIC_SYMBOL = "***" + MD_CONTENT_PLACEHOLDER + "***";
    public static final String UNDERLINE_SYMBOL = "<u>" + MD_CONTENT_PLACEHOLDER + "</u>";
    public static final String STRIKETHROUGH_SYMBOL = "~~" + MD_CONTENT_PLACEHOLDER + "~~";
    public static final String QUOTE_SYMBOL = ">";

    public static final String TABLE_SYMBOL = "|";

    public static final String TABLE_ALIGN_SYMBOL = "---";
    public static final String DISORDER_LIST_SYMBOL = "* ";
    public static final String ORDERED_LIST_SYMBOL = ". ";
    public static final String LINK_NAME_SYMBOL = "[" + MD_CONTENT_PLACEHOLDER + "]";
    public static final String LINK_HREF_SYMBOL = "(" + MD_CONTENT_PLACEHOLDER + ")";
    public static final String LINE_CODE_SYMBOL = "`" + MD_CONTENT_PLACEHOLDER + "`";
    public static final String CODE_BLOCK_SYMBOL = "```" + MD_CONTENT_PLACEHOLDER + "```";
    public static final String IMAGE_SYMBOL = "![" + MD_CONTENT_PLACEHOLDER + "](" + MD_CONTENT_PLACEHOLDER + ")";
    public static final String SPLIT_LINE_SYMBOL = "---";


    public static final String FILE_PREFIX = "file://";

    public static final String IMAGE_DIR_NAME = "images";

    public static final String CODE_BLOCK_DEFAULT_LANGUAGE = EMPTY;
    public static final String IMAGE_DEFAULT_TITLE = "image";

    public static final String MAIN_STAGE_TITLE = "yuque-md";

    public static final String MAIN_ICON_FILE_NAME = "image/icon.png";

    public static final String SET_ICON_FILE_NAME = "image/set.png";

    public static final String CHROME_DRIVER_FILE_NAME = "driver/chromedriver.exe";

    public static final String CHROME_FILE_NAME = "chrome/chrome.exe";

    public static final String CSS_FILE_NAME = "style/main.css";

    public static final String MAIN_UI_FILE_NAME = "ui/main.fxml";

    public static final String SET_UI_FILE_NAME = "ui/set.fxml";

    public static final String CONTENT_FILE_NAME = "config.properties";

    public static final String BROWSER_WAITE_TIME_SECOND_KEY = "browserWaiteTimeSecond";

    public static final String BROWSER_ROLL_INTERVAL_MILL_KEY = "browserRollIntervalMill";

    public static final String MARKDOWN_SAVE_PATH_KEY = "markdownSavePath";


    public static final String CHROME_DRIVER_KEY = "webdriver.chrome.driver";
}
