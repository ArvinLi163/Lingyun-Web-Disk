package com.seven.cn.springboot_project.service.impl;

import com.seven.cn.springboot_project.dao.EchartsDao;
import com.seven.cn.springboot_project.entity.FileByDay;
import com.seven.cn.springboot_project.entity.FileInfo;
import com.seven.cn.springboot_project.entity.UserInfo;
import com.seven.cn.springboot_project.service.EchartsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: ArvinLi
 * @date: 2023/04/18 9:37
 **/
@Service
public class EchartsServiceImpl implements EchartsService {
    @Resource
    EchartsDao echartsDao;


    @Override
    public UserInfo getUserCount() {
        return echartsDao.getUserCount();
    }

    @Override
    public FileInfo getFileCount() {
        return echartsDao.getFileCount();
    }

    @Override
    public List<FileByDay> getFileByDay() {
        return echartsDao.getFileByDay();
    }
}
