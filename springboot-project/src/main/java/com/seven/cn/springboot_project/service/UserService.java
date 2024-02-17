package com.seven.cn.springboot_project.service;

import com.seven.cn.springboot_project.entity.User;

/**
 * @description:
 * @author: bansheng
 * @date: 2023/03/30 14:23
 **/
public interface UserService {
    //验证用户名、密码
    User findUser(User user);
    //注册
    int register(User user);
}
