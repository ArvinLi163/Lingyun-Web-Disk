package com.seven.cn.springboot_project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @description:
 * @author: bansheng
 * @date: 2023/04/18 11:15
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileByDayList {
    private List<String> name;
    private List<Double> value;
}
