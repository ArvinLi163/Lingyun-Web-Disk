package com.seven.cn.springboot_project.dao;

import com.seven.cn.springboot_project.entity.FileByDay;
import com.seven.cn.springboot_project.entity.FileInfo;
import com.seven.cn.springboot_project.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: ArvinLi
 * @date: 2023/04/18 9:31
 **/
@Mapper
@Component("EchartsMapper")
public interface EchartsDao {
    @Select("select * from user_info")
    UserInfo getUserCount();

    @Select("select * from file_info")
    FileInfo getFileCount();

    @Select("select * from filebyday_info")
    List<FileByDay> getFileByDay();
}
