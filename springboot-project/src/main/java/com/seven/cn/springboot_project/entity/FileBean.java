package com.seven.cn.springboot_project.entity;

import lombok.*;


/**
 * @description:存储文件信息的实体类
 * @author: bansheng
 * @date: 2023/04/07 17:05
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileBean {
    private int fileId;
    private String fileName;
    private String filePath;
    private String fileSize;
    private String fileTime;
    private int isDirectory;
    public FileBean(String fileName, String filePath, String fileSize, String fileTime, int isDirectory){
        this.fileName=fileName;
        this.filePath=filePath;
        this.fileSize=fileSize;
        this.fileTime=fileTime;
        this.isDirectory = isDirectory;

    }
}
