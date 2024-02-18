package com.seven.cn.springboot_project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: ArvinLi
 * @date: 2023/04/18 10:10
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileInfo {
    private  Double total;
    private  Double dircounts;
    private  Double filecounts;
}
