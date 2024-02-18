package com.seven.cn.springboot_project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @description:
 * @author: ArvinLi
 * @date: 2023/04/18 11:02
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FileByDay {
    private String createtime;
    private Double counts;
}
