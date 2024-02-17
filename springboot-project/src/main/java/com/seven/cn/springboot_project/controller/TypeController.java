package com.seven.cn.springboot_project.controller;

import com.seven.cn.springboot_project.entity.FileBean;
import com.seven.cn.springboot_project.entity.ResultModel;
import com.seven.cn.springboot_project.service.FileBeanService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: bansheng
 * @date: 2023/04/09 20:16
 **/
@Controller
public class TypeController {

    @Resource
    FileBeanService fileBeanService;
    private ResultModel<List<FileBean>> resultModel;
    /**
     * 视频
     * videoLists?filepath
     */
    @RequestMapping("/videoLists")
    @ResponseBody
    public ResultModel<List<FileBean>> getVideoLists(@RequestParam String filepath) {
        List<FileBean> fileBeans = fileBeanService.findBySonPath(filepath);
        //System.out.println(fileBeans);
        for (int i = 0; i < fileBeans.size(); i++) {
            String substring = fileBeans.get(i)
                    .getFileName().substring(fileBeans.get(i).getFileName().lastIndexOf(".") + 1);
            //System.out.println(substring);
            //移除非视频类型的
            if (!(substring.equals("mp4") || substring.equals("avi") || substring.equals("mov")
                    || substring.equals("rmvb") || substring.equals("asf") || substring.equals("rm")
                    || substring.equals("navi") || substring.equals("mkv") || substring.equals("webm"))) {
                fileBeans.remove(fileBeans.get(i));
                i--;
            }
        }
        if (fileBeans != null) {
            resultModel = ResultModel.success("查询列表成功", fileBeans, fileBeans.size());
        } else {
            resultModel = ResultModel.error("查询列表失败");
        }
        return resultModel;
    }

    /**
     * 音乐
     * musicLists?filepath
     */
    @RequestMapping("/musicLists")
    @ResponseBody
    public ResultModel<List<FileBean>> getMusicLists(@RequestParam String filepath) {
        List<FileBean> fileBeans = fileBeanService.findBySonPath(filepath);
        for (int i = 0; i < fileBeans.size(); i++) {
            String substring = fileBeans.get(i)
                    .getFileName().substring(fileBeans.get(i).getFileName().lastIndexOf(".") + 1);
            //移除非音乐类型的
            if (!substring.equals("mp3") || substring.equals("wma") || substring.equals("midi")) {
                fileBeans.remove(fileBeans.get(i));
                i--;
            }
        }
        if (fileBeans != null) {
            resultModel = ResultModel.success("查询列表成功", fileBeans, fileBeans.size());
        } else {
            resultModel = ResultModel.error("查询列表失败");
        }
        return resultModel;
    }
/**
 * 图片
 *pictureLists?filepath
 */
@RequestMapping("/pictureLists")
@ResponseBody
public ResultModel<List<FileBean>> getPictureLists(@RequestParam String filepath) {
    List<FileBean> fileBeans = fileBeanService.findBySonPath(filepath);
    for (int i = 0; i < fileBeans.size(); i++) {
        String substring = fileBeans.get(i)
                .getFileName().substring(fileBeans.get(i).getFileName().lastIndexOf(".") + 1);
        //System.out.println(substring);
        //移除非文档类型的
        if (!(substring.equals("jpg") || substring.equals("png") || substring.equals("jpeg") || substring.equals("ico")
                || substring.equals("pic") || substring.equals("webp")
                || substring.equals("svg") || substring.equals("vsd"))) {
            fileBeans.remove(fileBeans.get(i));
            i--;
        }
    }
    if (fileBeans != null) {
        resultModel = ResultModel.success("查询列表成功", fileBeans, fileBeans.size());
    } else {
        resultModel = ResultModel.error("查询列表失败");
    }
    return resultModel;
}





    /**
     * 文档
     * documentLists?filepath
     * .xlsx, .xls, .mp4, .jpg, .png, .txt, .mp3, .avi, .doc, .docx, .jpeg, .pdf ,.zip ,.md ,.pptx
     */
    @RequestMapping("/documentLists")
    @ResponseBody
    public ResultModel<List<FileBean>> getDocumentLists(@RequestParam String filepath) {
        List<FileBean> fileBeans = fileBeanService.findBySonPath(filepath);
        for (int i = 0; i < fileBeans.size(); i++) {
            String substring = fileBeans.get(i)
                    .getFileName().substring(fileBeans.get(i).getFileName().lastIndexOf(".") + 1);
            //System.out.println(substring);
            //移除非文档类型的
            if (!(substring.equals("xlsx") || substring.equals("xls")
                    || substring.equals("txt") || substring.equals("doc") || substring.equals("docx")
                    || substring.equals("pdf") || substring.equals("md") || substring.equals("pptx"))) {
                fileBeans.remove(fileBeans.get(i));
                i--;
            }
        }
        if (fileBeans != null) {
            resultModel = ResultModel.success("查询列表成功", fileBeans, fileBeans.size());
        } else {
            resultModel = ResultModel.error("查询列表失败");
        }
        return resultModel;
    }

    /**
     * 收藏
     *collectionListsFiles?filepath
     */
    @RequestMapping("/collectionListsFiles")
    @ResponseBody
    public ResultModel<List<FileBean>> collectionListsFiles(@RequestParam String filepath) {
        List<FileBean> fileBeans = fileBeanService.findByCollectSonPath(filepath);
        if (fileBeans != null) {
            resultModel = ResultModel.success("查询列表成功", fileBeans, fileBeans.size());
        } else {
            resultModel = ResultModel.error("查询列表失败");
        }
        return resultModel;
    }

    /**
     * 回收站
     *rubbishListsFiles?filepath
     */
    @RequestMapping("/rubbishListsFiles")
    @ResponseBody
    public ResultModel<List<FileBean>> rubbishListsFiles(@RequestParam String filepath) {
        List<FileBean> fileBeans = fileBeanService.findByRubbishSonPath(filepath);
        if (fileBeans != null) {
            resultModel = ResultModel.success("查询列表成功", fileBeans, fileBeans.size());
        } else {
            resultModel = ResultModel.error("查询列表失败");
        }
        return resultModel;
    }
}
