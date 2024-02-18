package com.seven.cn.springboot_project.controller;

import com.seven.cn.springboot_project.entity.*;
import com.seven.cn.springboot_project.service.EchartsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: ArvinLi
 * @date: 2023/04/18 9:38
 **/
@Controller
public class EchartsController {
    @Resource
    EchartsService echartsService;

    /**
     * 查询总的用户数
     * @return
     */
    @RequestMapping("/getUserCount")
    @ResponseBody
    public ResultModel<UserInfo> userCount(){
        UserInfo userCount = echartsService.getUserCount();
        ResultModel<UserInfo> resultModel = new ResultModel<>();
        if(userCount!=null){
            resultModel=ResultModel.success("查询成功",userCount);
        }else {
            resultModel=ResultModel.error("查询失败");
        }
        return  resultModel;
    }

    /**
     * 网盘总数、文件夹总数、文件总数
     * @return
     */
    @RequestMapping("/getFileCount")
    @ResponseBody
    public ResultModel<FileInfo> fileCount(){
        FileInfo fileCount = echartsService.getFileCount();
        ResultModel<FileInfo> resultModel = new ResultModel<>();
        if(fileCount!=null){
            resultModel=ResultModel.success("查询成功",fileCount);
        }else{
            resultModel=ResultModel.error("查询失败");
        }
        return resultModel;
    }


    @RequestMapping("/getFileCountByDay")
    @ResponseBody
    public ResultModel<FileByDayList> FileCount(){
        List<FileByDay> fileByDays = echartsService.getFileByDay();
        FileByDayList fileByDayList = new FileByDayList();
        ArrayList<String> name = new ArrayList<>(); //[2023-04-07,2023-04-09]
        ArrayList<Double> value = new ArrayList<>();// [3,5,1]
        for (FileByDay fileByDay:fileByDays) {
            name.add(fileByDay.getCreatetime());
            value.add(fileByDay.getCounts());
        }
        fileByDayList.setName(name);
        fileByDayList.setValue(value);
        ResultModel<FileByDayList> resultModel = new ResultModel<>();
        if(fileByDayList!=null){
            resultModel=ResultModel.success("查询成功",fileByDayList);
            System.out.println(fileByDayList);
        }else{
            resultModel=ResultModel.error("查询失败");
        }
      return resultModel;
    }


}
