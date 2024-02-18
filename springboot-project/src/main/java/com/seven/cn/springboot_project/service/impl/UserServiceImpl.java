package com.seven.cn.springboot_project.service.impl;

import com.seven.cn.springboot_project.dao.FileDao;
import com.seven.cn.springboot_project.dao.UserDao;
import com.seven.cn.springboot_project.entity.FileBean;
import com.seven.cn.springboot_project.entity.User;
import com.seven.cn.springboot_project.handler.HDFSHandler;
import com.seven.cn.springboot_project.service.UserService;
import com.seven.cn.springboot_project.utils.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description:实现类
 * @author: ArvinLi
 * @date: 2023/03/30 14:24
 **/
@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserDao userDao;
   @Resource
    FileDao fileDao;
    @Resource
    HDFSHandler handler;//引入关于hdfs api的一些方法
    @Override
    public User findUser(User user) {
        return userDao.findUser(user);

    }

    @Override
    public int register(User user) {
        //查询用户的用户名
        User userByUserName = userDao.findUserByUserName(user.getUsername());
        int num;
        //如果用户名存在 num=0
        // 如果用户不存在，添加该用户并在HDFS上创建该用户目录,将文件名、存储路径、大小等信息在mysqL插入
        if(userByUserName!=null){
            num=0;
        }else{
            num= userDao.addUser(user);
            String username= user.getUsername(); //获取用户名
            String hadoopPath="/"+username; //hdfs上的用户目录
            FileBean fileBean = new FileBean(username, hadoopPath, "0", TimeUtil.getNowTime(), 1);
            fileDao.addFileBean(fileBean);
            handler.mkdir(hadoopPath);
        }
        return num;
    }
}
