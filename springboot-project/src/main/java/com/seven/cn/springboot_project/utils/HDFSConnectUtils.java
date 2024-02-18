package com.seven.cn.springboot_project.utils;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

/**
 * @description:hdfs工具类
 * @author: ArvinLi
 * @date: 2023/04/06 14:05
 **/
@Component
public class HDFSConnectUtils {
    @Value("${HDFS_URL}")
    private String HDFS_URL;
    @Value("${HDFS_USER}")
    private String HDFS_USER;
    @Value("${HDFS_NAMENODE}")
    private String HDFS_NAMENODE;
    private FileSystem fs=null;
    private Configuration conf;

    public FileSystem getFileSystem(){
        conf = new Configuration();
        //设置副本数量
        conf.set("dfs.replication","2");
        //处理数据块的异常设置
        conf.set("dfs.client.block.write.replace-datanode-on-failure.enable", "true");
        conf.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");
        //使用datanode的映射
        conf.set("dfs.client.use.datanode.hostname","true");
        try {
            fs = FileSystem.get(URI.create(HDFS_URL), conf, HDFS_USER);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return fs;
    }
    public Configuration getConf(){
        return conf;
    }
    public String getHDFS_NAMENODE(){ return HDFS_NAMENODE;}
}
