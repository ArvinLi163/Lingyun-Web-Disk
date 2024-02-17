package com.seven.cn.springboot_project.utils;

import java.text.DecimalFormat;

/**
 * @description:
 * @author: bansheng
 * @date: 2023/04/08 11:07
 **/
public class FileUtils {
    public static String FileSizeUnitConvert(long len) {
        String fileSize="";
        if (len < 1024) {
            fileSize= new DecimalFormat("#0.00").format(len) + " B";
        }
        if (len > 1024) {
            len /= 1024; // KB len = len /1024
            fileSize= new DecimalFormat("#0.00").format(len) + " KB";
        }
        if (len > 1024) {
            len /= 1024;// MB
            fileSize= new DecimalFormat("#0.00").format(len) + " MB";
        }
        if (len > 1024) {
            len /= 1024;// GB
            fileSize= new DecimalFormat("#0.00").format(len) + " G";
        }
        return fileSize;
    }
}
