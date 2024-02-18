package com.seven.cn.springboot_project.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:对时间进行格式化的工具类
 * @author: ArvinLi
 * @date: 2023/04/07 17:21
 **/
@Component
public class TimeUtil {
 public static String getTime(Long time){
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     String times = sdf.format(time);
     return times;
 }
public static String getNowTime(){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String times = sdf.format(new Date());
    return times;
}
}
