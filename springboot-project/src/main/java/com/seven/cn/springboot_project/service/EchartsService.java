package com.seven.cn.springboot_project.service;

import com.seven.cn.springboot_project.entity.FileByDay;
import com.seven.cn.springboot_project.entity.FileInfo;
import com.seven.cn.springboot_project.entity.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: ArvinLi
 * @date: 2023/04/18 9:35
 **/
public interface EchartsService {
    UserInfo getUserCount();
    FileInfo getFileCount();

    List<FileByDay> getFileByDay();
}
