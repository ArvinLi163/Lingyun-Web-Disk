package com.seven.cn.springboot_project.dao;

import com.seven.cn.springboot_project.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: ArvinLi
 * @date: 2023/03/30 14:46
 **/
@Mapper
@Component("userMapper")
public interface UserDao {
    //添加用户
    @Insert("insert into user(username,password) values(#{username},#{password})")
    int addUser(User user);

    //删除用户(通过userId)
    @Delete("delete from user where userId=#{userId}")
    int deleteById(int userId);

    //修改用户
    @Update("update user set password=#{password} where username=#{username}")
    int update(User user);

    //查找所有用户
    @Select("select * from user")
    List<User> findAll();

    //根据用户信息去查找用户
    @Select("select * from user where username=#{username} and password=#{password}")
    User findUser(User user);
    //查找用户是否存在
    @Select("select * from user where username=#{username}")
    User findUserByUserName(String username);
    //@Param注解的作用是给参数命名，参数命名后就能根据名称得到参数值，正确的将参数传入sql语句中
    //User findUserByUserName(@Param("username") String username);

}
