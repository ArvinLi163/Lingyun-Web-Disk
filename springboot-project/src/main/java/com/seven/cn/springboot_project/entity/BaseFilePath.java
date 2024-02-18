package com.seven.cn.springboot_project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:处理前端展示文件的路径
 * @author: ArvinLi
 * @date: 2023/04/09 9:11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseFilePath {
    private String basePath;
    private String absolutePath; //绝对路径
}
