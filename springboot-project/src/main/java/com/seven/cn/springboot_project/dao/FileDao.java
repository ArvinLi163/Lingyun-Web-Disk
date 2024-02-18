package com.seven.cn.springboot_project.dao;

import com.seven.cn.springboot_project.entity.FileBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: ArvinLi
 * @date: 2023/04/07 17:02
 **/
@Mapper
@Component("FileMapper")
public interface FileDao {
    //增加
    @Insert("insert into fileBean(fileName,filePath,fileSize,fileTime,isDirectory) values(#{fileName},#{filePath},#{fileSize},#{fileTime},#{isDirectory}) ")
    int addFileBean(FileBean fileBean);

    //向collectFile表中添加
    @Insert("insert into collectFile(fileName,filePath,fileSize,fileTime,isDirectory) values(#{fileName},#{filePath},#{fileSize},#{fileTime},#{isDirectory}) ")
    int addCollectFile(FileBean fileBean);

    //向rubbishFile表中添加
    @Insert("insert into rubbishFile(fileName,filePath,fileSize,fileTime,isDirectory) values(#{fileName},#{filePath},#{fileSize},#{fileTime},#{isDirectory}) ")
    int addRubbishFile(FileBean fileBean);

    //删
    @Delete("delete from fileBean where fileId=#{fileId}")
    int deleteById(int fileId);

    //删
    @Delete("delete from fileBean where filePath=#{filePath}")
    int deleteByPath(String filePath);

    //删除rubbishFile表中数据
    @Delete("delete from rubbishFile where filePath=#{filePath}")
    int deleteByRubbishPath(String filePath);

    //改
    @Update("update fileBean set fileName=#{fileName},filePath=#{filePath},fileTime=#{fileTime} where fileId=#{fileId}")
    int update(FileBean fileBean);

    //查  查找所有用户
    @Select("select * from collectFile")
    List<FileBean> findCollectAll();

    //查  查找所有用户
    @Select("select * from rubbishFile")
    List<FileBean> findRubbishAll();

    //查  查找所有用户
    @Select("select * from fileBean")
    List<FileBean> findAll();

    //分页查找所有用户
    @Select("select * from fileBean limit #{page},#{size}")
    List<FileBean> findPageAll(int page, int size);

    //查  查找所有用户
    @Select("select * from fileBean where filePath=#{filePath}")
    FileBean findByFilePath(String filePath);

    //查  查找所有用户
    @Select("select * from fileBean where fileId=#{fileId}")
    FileBean findById(int fileId);

    //查
    @Select("select * from fileBean where filePath like concat('%',#{filePath},'%')")
    List<FileBean> findBySonPath(String filePath);
    //搜索
    @Select("select * from fileBean where fileName like concat('%',#{fileName},'%') and filePath like concat('%',#{filePath},'%') ")
    List<FileBean> findSearchName(String fileName,String filePath);

    @Select("select * from collectFile where filePath like concat('%',#{filePath},'%')")
    List<FileBean> findByCollectSonPath(String filePath);

    @Select("select * from rubbishFile where filePath like concat('%',#{filePath},'%')")
    List<FileBean> findByRubbishSonPath(String filePath);

}
