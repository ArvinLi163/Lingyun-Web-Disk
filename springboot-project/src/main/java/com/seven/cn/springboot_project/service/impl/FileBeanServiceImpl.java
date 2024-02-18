package com.seven.cn.springboot_project.service.impl;

import com.seven.cn.springboot_project.dao.FileDao;
import com.seven.cn.springboot_project.entity.FileBean;
import com.seven.cn.springboot_project.service.FileBeanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:FileBean相关方法的实现类
 * @author: ArvinLi
 * @date: 2023/04/07 18:41
 **/
@Service
public class FileBeanServiceImpl implements FileBeanService {
    @Resource
    FileDao fileDao;

    //查询
    @Override
    public FileBean findByFilePath(String filePath) {
        return fileDao.findByFilePath(filePath);
    }

    //添加
    @Override
    public int addFileBean(FileBean fileBean) {
        return fileDao.addFileBean(fileBean);
    }

    //查询子路径
    @Override
    public List<FileBean> findBySonPath(String filePath) {
        return fileDao.findBySonPath(filePath);
    }

    @Override
    public List<FileBean> findSearchName(String fileName,String filePath) {
        return fileDao.findSearchName(fileName,filePath);
    }

    @Override
    public int deleteByPath(String filePath) {
        return fileDao.deleteByPath(filePath);
    }

    //向rubbishFile表中添加
    @Override
    public int addRubbishFile(FileBean fileBean) {
        return fileDao.addRubbishFile(fileBean);
    }

    //修改
    @Override
    public int update(FileBean fileBean) {
        return fileDao.update(fileBean);
    }

    //向collectFile表中添加
    @Override
    public int addCollectFile(FileBean fileBean) {
        return fileDao.addCollectFile(fileBean);
    }

    //删除rubbishFile表中数据
    @Override
    public int deleteByRubbishPath(String filePath) {
        return fileDao.deleteByRubbishPath(filePath);
    }

    //==================侧边栏===========================================
    @Override
    public List<FileBean> findAll() {
        return fileDao.findAll();
    }

    @Override
    public List<FileBean> findCollectAll() {
        return fileDao.findCollectAll();
    }

    @Override
    public List<FileBean> findRubbishAll() {
        return fileDao.findRubbishAll();
    }

    @Override
    public List<FileBean> findByCollectSonPath(String filePath) {
        return fileDao.findByCollectSonPath(filePath);
    }

    @Override
    public List<FileBean> findByRubbishSonPath(String filePath) {
        return fileDao.findByRubbishSonPath(filePath);
    }


}
