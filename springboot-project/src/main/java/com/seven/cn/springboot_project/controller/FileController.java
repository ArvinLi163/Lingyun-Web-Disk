package com.seven.cn.springboot_project.controller;

import com.seven.cn.springboot_project.entity.BaseFilePath;
import com.seven.cn.springboot_project.entity.FileBean;
import com.seven.cn.springboot_project.entity.ResultModel;
import com.seven.cn.springboot_project.service.FileBeanService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: bansheng
 * @date: 2023/04/08 16:48
 **/
@Controller
public class FileController {
    /**
     * 加载列表数据  一级目录
     * lists
     */
    private ResultModel<List<FileBean>> resultModel;

    @Resource
    FileBeanService fileBeanService;

    @RequestMapping("/lists")
    @ResponseBody
    public ResultModel<List<FileBean>> getLists(@RequestParam(defaultValue = "1") Integer pagenum, @RequestParam(defaultValue = "5") Integer pagesize, @RequestParam() String basepath) {
        List<FileBean> bySonPath = fileBeanService.findBySonPath(basepath);//basepath "/用户名"
        //System.out.println(bySonPath);
        //去除 "/用户名/xxx/xxx" 只显示 "/用户名/xxx"
        for (int i = 0; i < bySonPath.size(); i++) {
            String[] split = bySonPath.get(i).getFilePath().split("/");//
            // System.out.println(Arrays.toString(split));
            if (split.length != (basepath.split("/").length + 1)) {
                bySonPath.remove(bySonPath.get(i));
                i--;
            }
        }

        if (bySonPath != null) {
            //给前端返回提示信息并返回数据库查询到的相关信息
            resultModel = ResultModel.success("查询列表成功", bySonPath, bySonPath.size());
        } else {
            resultModel = ResultModel.error("查询列表失败");
        }
        return resultModel;
    }

    /**
     * 查询子路径 二级目录
     * searchFiles?filePath=
     */
    @RequestMapping("/searchFiles")
    @ResponseBody
    public ResultModel<List<FileBean>> getSearchPath(@RequestParam String filePath) {
        List<FileBean> sonFileDatas = fileBeanService.findBySonPath(filePath);// filePath "/lgy/xxx"
        System.out.println(sonFileDatas);
        //筛选出要展示的文件路径
        for (int i = 0; i < sonFileDatas.size(); i++) {
            String[] splits = sonFileDatas.get(i).getFilePath().split("/");
            if (splits.length < (filePath.split("/").length + 1)) {
                //移除长度不为4的数组
                sonFileDatas.remove(sonFileDatas.get(i));
                i--;
            }
        }
            //创建集合
            ArrayList<BaseFilePath> baseFilePaths = new ArrayList<>();
            //处理路径
            String[] FileSplits = filePath.split("/"); //长度为3的数组
            //定义全路径
            String absolutePath = "";
            for (int i = 1; i < FileSplits.length; i++) {
                absolutePath += "/" + FileSplits[i]; // /lgy  /lgy/bigdata
                BaseFilePath baseFilePath = new BaseFilePath(FileSplits[i], absolutePath);
                baseFilePaths.add(baseFilePath);
            }
        //ResultModel<List<FileBean>> resultModel;
            //赋值 用于前端对数据进行展示
            if (sonFileDatas != null) {
                resultModel = ResultModel.success("查询列表成功", sonFileDatas, baseFilePaths, sonFileDatas.size());
            } else {
                resultModel = ResultModel.error("查询列表失败");
            }
        return resultModel;
    }


    /**
     * 搜索
     * searchFiles22?searchFilename=
     */
    @RequestMapping("/searchFiles22")
    @ResponseBody
    public ResultModel<List<FileBean>> search(@RequestParam String searchFilename,@RequestParam String searchFilepath) {
        List<FileBean> searchDatas = fileBeanService.findSearchName(searchFilename,searchFilepath);
        ResultModel<List<FileBean>> resultModel;
        if (searchDatas != null) {
            resultModel = ResultModel.success("查询列表成功", searchDatas, searchDatas.size());
        } else {
            resultModel = ResultModel.error("查询列表失败");
        }
        return resultModel;
    }


    /**
     * 删除
     * deleteFiles?deleteFilepath=
     */
    @RequestMapping("/deleteFiles")
    @ResponseBody
    public void deleteFiles(@RequestParam String deleteFilepath) {
        FileBean byFilePath = fileBeanService.findByFilePath(deleteFilepath);
        fileBeanService.addRubbishFile(byFilePath);//向rubbishFile表中添加
        fileBeanService.deleteByPath(deleteFilepath);//在fileBean表中删除
    }


    /**
     * 收藏功能
     * collectFiles?filepath=
     */
    @RequestMapping("/collectFiles")
    @ResponseBody
    public void collectFile(@RequestParam String filepath){
        FileBean byFilePath = fileBeanService.findByFilePath(filepath);
        fileBeanService.addCollectFile(byFilePath);
        // fileBeanService.deleteByPath(filepath); //避免音乐和收藏有一个不能显示
    }

}
