package com.seven.cn.springboot_project.handler;

import com.seven.cn.springboot_project.utils.HDFSConnectUtils;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @description:hdfs api调用的工具类
 * @author: bansheng
 * @date: 2023/04/07 16:04
 **/
@Component
public class HDFSHandler {
    @Resource
    HDFSConnectUtils hdfs;

    /**
     * 创建目录
     *
     * @return
     */
    public boolean mkdir(String hadoopPath) {
        FileSystem fs = hdfs.getFileSystem();
        try {
            return fs.mkdirs(new Path(hadoopPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 修改
     */
    public void rename(String oldFilePath,String newFilePath){
        FileSystem fs = hdfs.getFileSystem();
        try {
            fs.rename(new Path(oldFilePath), new Path(newFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 永久删除
     */
    public void deleteFile(String hadoopPath){
        FileSystem fs = hdfs.getFileSystem();
        try {
            fs.delete(new Path(hadoopPath),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
