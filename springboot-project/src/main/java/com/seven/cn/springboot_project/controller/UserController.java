package com.seven.cn.springboot_project.controller;

import com.seven.cn.springboot_project.entity.ResultModel;
import com.seven.cn.springboot_project.entity.User;
import com.seven.cn.springboot_project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


/**
 * @description:
 * @author: bansheng
 * @date: 2023/03/31 11:02
 **/
@Controller
public class UserController{
    @Resource
    UserService userService;

    //登录  验证数据库是否有该用户，若存在就让其登录
    @RequestMapping("/login")
    @ResponseBody
    public ResultModel<User> login(@RequestBody User user){
        User findUser = userService.findUser(user);
        ResultModel<User> model;
        if(findUser!=null){
            //数据库存在该用户
            model=ResultModel.success("登录成功",findUser);
        }else {
            model=ResultModel.error("用户名或密码错误，或用户未注册");
        }
        return model;
    }

    //注册
    @RequestMapping("/register")
    @ResponseBody
    public ResultModel<Integer> register(@RequestBody User user){
        int num=userService.register(user);
        ResultModel<Integer> model;
        if(num >0){
            //数据库添加了
            model=ResultModel.success("注册成功",num);
        }else {
            model=ResultModel.error("用户名重复了！换个名字吧");
        }
        return model;
    }
}
