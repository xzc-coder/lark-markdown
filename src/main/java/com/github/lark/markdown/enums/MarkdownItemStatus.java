package com.github.lark.markdown.enums;

/**
 * @Author: xy-code
 * @Description: MarkdownItem状态
 * @Date: 2023-10-29 16:27
 **/
public enum MarkdownItemStatus {

    WAIT(1,"等待中"),
    LOADING(2,"加载中"),
    START(3,"进行中"),
    COMPLETE(4,"完成"),
    ERROR(9999,"错误");


    private final int status;
    private final String statusDesc;

    MarkdownItemStatus(int status, String statusDesc) {
        this.status = status;
        this.statusDesc = statusDesc;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public static MarkdownItemStatus findStatus(int status) {
        MarkdownItemStatus[] values = MarkdownItemStatus.values();
        for(MarkdownItemStatus markdownItemStatus : values) {
            if(status == markdownItemStatus.getStatus()) {
                return markdownItemStatus;
            }
        }
        return null;
    }
}
