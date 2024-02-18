package com.seven.cn.springboot_project.controller;

import com.seven.cn.springboot_project.entity.FileBean;
import com.seven.cn.springboot_project.entity.ResultModel;
import com.seven.cn.springboot_project.handler.HDFSHandler;
import com.seven.cn.springboot_project.service.FileBeanService;
import com.seven.cn.springboot_project.utils.FileUtils;
import com.seven.cn.springboot_project.utils.HDFSConnectUtils;
import com.seven.cn.springboot_project.utils.TimeUtil;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @description:hdfs相关接口的调用
 * @author: ArvinLi
 * @date: 2023/04/07 18:15
 **/
@Controller
public class HDFSController {
    /**
     * 创建目录
     * addFile?filename=
     */
    @Resource
    FileBeanService fileBeanService;
    @Resource
    HDFSHandler hdfsHandler;
    @Resource
    FileController fileController;

    @RequestMapping("/addFile")
    @ResponseBody
    public ResultModel<List<FileBean>> mkdir(@RequestParam String filename) {
        //fileBean表中查询,判断文件是否存在
        ResultModel<List<FileBean>> resultModel;
        FileBean byFilePath = fileBeanService.findByFilePath(filename);  // /lgy/xxxx
        if (byFilePath == null) {
            String realName = filename.substring(filename.lastIndexOf("/") + 1);//获取 xxx
            FileBean fileBean = new FileBean(realName, filename, "0", TimeUtil.getNowTime(), 1);
            hdfsHandler.mkdir(filename);
            fileBeanService.addFileBean(fileBean);
            resultModel = ResultModel.success("创建目录成功");
        } else {
            resultModel = ResultModel.error("创建目录失败");
        }
        return resultModel;
    }

    /**
     * 上传文件
     */
    @Resource
    HDFSConnectUtils hdfs;

    @RequestMapping("/upload")
    @ResponseBody
    public ResultModel<List<FileBean>> uploadfile(MultipartFile file, @RequestParam String filepath) {
        //1.获取客户端对象
        FileSystem fs = hdfs.getFileSystem();
        ResultModel<List<FileBean>> resultModel;
        //2.获取文件路径
        String name = file.getOriginalFilename();//上传文件的文件名
        String whole_filepath = filepath + "/" + name;  // filepath -->/lgy
        //判断该文件路径是否已经存在了
        FileBean byFilePath = fileBeanService.findByFilePath(whole_filepath);
        if (byFilePath == null) {
            //不存在
            //只能通过将文件变成输入流的方式进行上传
            long size = file.getSize();
            InputStream is = null;
            try {  //3.将文件转化为输入流
                is = file.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {//4.以输出流的方式上传到HDFS上
                FSDataOutputStream os = fs.create(new Path(whole_filepath));
                IOUtils.copyBytes(is, os, hdfs.getConf(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //5.将文件的相关信息写入mysql的fileBean表中去
            fileBeanService.addFileBean(new FileBean(name, whole_filepath, FileUtils.FileSizeUnitConvert(size), TimeUtil.getNowTime(), 0));
            resultModel = ResultModel.success("上传成功");
        } else {
            resultModel = ResultModel.error("上传失败");
        }
        return resultModel;
    }

    /**
     * 下载   downLoadFile?filePath=
     * 192.168.106.130:9870/webhdfs/v1/lgy/bigdata/HADOOP_ZOOKEEPER_HBASE搭建参考手册.docx?op=open
     */

    @RequestMapping("/downLoadFile")
    @ResponseBody
    public String downLoadFile(@RequestParam String filePath){
        //使用拼接网址的方式下载
        //System.out.println(filePath);
       String url=hdfs.getHDFS_NAMENODE()+"/webhdfs/v1"+filePath+"?op=open";
       return url;
    }

    /**
     * 修改
     * alterFiles
     */
    @RequestMapping("/alterFiles")
    @ResponseBody
    public void alterFile(@RequestParam String oldname,@RequestParam String  newname,@RequestParam String  filepath){
        //System.out.println(filepath);
        String oldPath = filepath + "/" + oldname;
        //System.out.println(oldPath);
        String newPath = filepath + "/" + newname;
        //System.out.println(newPath);
        hdfsHandler.rename(oldPath,newPath); //hdfs修改
        FileBean byFilePath = fileBeanService.findByFilePath(oldPath);
        byFilePath.setFileName(newPath.substring(newPath.lastIndexOf("/")+1));
        byFilePath.setFilePath(newPath);
        byFilePath.setFileTime(TimeUtil.getNowTime());
        fileBeanService.update(byFilePath);
    }
    /**
     * 真正的删除
     * deleteRubbishFiles?deleteFilepath=
     */
    @RequestMapping("/deleteRubbishFiles")
    @ResponseBody
    public void deleteRubbishFiles(@RequestParam String deleteFilepath){
        hdfsHandler.deleteFile(deleteFilepath);//hdfs删除
        fileBeanService.deleteByRubbishPath(deleteFilepath);// 删除rubbishFile表中数据

    }
}
