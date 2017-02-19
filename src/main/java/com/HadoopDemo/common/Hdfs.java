package com.HadoopDemo.common;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件名：Hdfs
 * 功能：HDFS（分布式文件系统）操作工具
 * 创建人：zhuminming
 * 创建日期：2017-02-09
 */
public class Hdfs implements Serializable{
    private FileSystem fileSystem;
    public Hdfs(TrackerConfig config) throws IOException {
        Configuration conf = new Configuration();
        fileSystem = FileSystem.newInstance(conf);
    }

    /**
     * 功能：判断文件是否存在
     * @throws IOException
     */
    public boolean isFileExist(Path path) throws IOException {
        return fileSystem.isFile(path);
    }

    /**
     * 功能：获取文件路径集合
     * @throws IOException
     */
    public List<Path> getFileList(Path path) throws IOException{
        FileStatus[] status = fileSystem.listStatus(path);
        List<Path> list = new ArrayList<Path>();
        for(FileStatus file : status){
            if(file.isDirectory()){
                list.addAll(getFileList(file.getPath()));
            }else if(file.isFile()){
                list.add(file.getPath());
            }
        }
        return list;
    }

    public void close() throws IOException{
        fileSystem.close();
    }
}
