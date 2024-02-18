package com.seven.cn.springboot_project.entity;

import lombok.*;

/**
 * @description:
 * @author: ArvinLi
 * @date: 2023/03/28 21:50
 **/
@Data  //引入lombok(工具类库),简化代码书写
@AllArgsConstructor //注在类上，提供类的全参构造
@NoArgsConstructor  //注在类上，提供类的无参构造
@ToString  //注解在类上, 为类提供 toString() 方法
public class User {
    private int userId;
    private String username;
    private String password;

}
