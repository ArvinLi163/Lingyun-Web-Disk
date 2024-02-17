package com.seven.cn.springboot_project.service;

import com.seven.cn.springboot_project.entity.FileBean;

import java.util.List;

/**
 * @description:
 * @author: bansheng
 * @date: 2023/04/07 18:32
 **/
public interface FileBeanService {
    //查询
    FileBean findByFilePath(String filePath);

    //添加
    int addFileBean(FileBean fileBean);

    //查询当前用户下的所有子路径
    List<FileBean> findBySonPath(String filePath);

    //搜索
    List<FileBean> findSearchName(String fileName,String filePath);

    //删除
    int deleteByPath(String filePath);

    //向rubbishFile表中添加
    int addRubbishFile(FileBean fileBean);

    //修改
    int update(FileBean fileBean);

    //向collectFile表中添加
    int addCollectFile(FileBean fileBean);

    //删除rubbishFile表中数据
    int deleteByRubbishPath(String filePath);

    //============================侧边栏=====================================
    //
    List<FileBean> findAll();
    List<FileBean> findCollectAll();
    List<FileBean> findRubbishAll();
    List<FileBean> findByCollectSonPath(String filePath);
    List<FileBean> findByRubbishSonPath(String filePath);
}
