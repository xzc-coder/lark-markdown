package com.github.lark.markdown.utils;

/**
 * @Author: xy-code
 * @Description: 图片相关的工具类
 * @Date: 2023-10-29 16:27
 **/
public class ImageUtil {

    private ImageUtil() {
    }

    public static String getFileType(byte[] header) {
        if (isJPEG(header)) {
            return "jpeg";
        } else if (isGIF(header)) {
            return "gif";
        } else if (isPNG(header)) {
            return "png";
        } else if (isWebP(header)) {
            return "webp";
        } else if (isSVG(header)) {
            return "svg";
        } else if (isPSD(header)) {
            return "psd";
        } else if (isBMP(header)) {
            return "bmp";
        } else if (isTIFF(header)) {
            return "tif";
        } else {
            return null;
        }
    }

    public static boolean isJPEG(byte[] header) {
        return header[0] == (byte) 0xFF && header[1] == (byte) 0xD8 && header[2] == (byte) 0xFF;
    }

    public static boolean isGIF(byte[] header) {
        return header[0] == 'G' && header[1] == 'I' && header[2] == 'F';
    }

    public static boolean isPNG(byte[] header) {
        return header[0] == (byte) 0x89 && header[1] == 'P' && header[2] == 'N' && header[3] == 'G';
    }

    public static boolean isWebP(byte[] header) {
        return header[0] == 'R' && header[1] == 'I' && header[2] == 'F' && header[3] == 'F';
    }

    public static boolean isSVG(byte[] header) {
        return header[0] == '<' && header[1] == 's' && header[2] == 'v' && header[3] == 'g';
    }

    public static boolean isPSD(byte[] header) {
        return header[0] == '8' && header[1] == 'B' && header[2] == 'P' && header[3] == 'S';
    }


    public static boolean isBMP(byte[] header) {
        return header[0] == 'B' && header[1] == 'M';
    }

    private static boolean isTIFF(byte[] header) {
        return header[0] == 'I' && header[1] == 'I' && header[2] == ' ' && header[3] == ' ';
    }
}
