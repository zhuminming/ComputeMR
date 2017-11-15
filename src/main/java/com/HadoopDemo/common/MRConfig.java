package com.HadoopDemo.common;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.JobContext;
import java.io.IOException;


/**
 * Created by zmm on 2017/11/15.
 */
public class MRConfig {
    public static Configuration getConfiguration(){
        Configuration configuration = new Configuration();
        return configuration;
    }

    public static void setValueConfig(String inputPath ,Configuration configuration,TrackerConfig config) throws IOException {
        Long totalFileSize = getTotalFileSize(inputPath,configuration,config);
        Long splitFileSize = config.getMrInputSplitSize();
        int mapNum = (int) (totalFileSize/splitFileSize)+1;
        if(mapNum>config.getMrMaxMapNum()){
            splitFileSize = totalFileSize/config.getMrMaxMapNum()+1;  //设置FileSplit文件的大小
            mapNum = (int) (totalFileSize/splitFileSize) +1;          //设置map的个数
        }

        int numReduceTasks = (int) (mapNum * config.getMrMapReduceNumRate())+1;  //设置reduce的个数
        configuration.setFloat("mapreduce.input.fileinputformat.split.maxsize",splitFileSize);
        configuration.setFloat("mapreduce.input.fileinputformat.split.minsize.per.node",splitFileSize);
        configuration.setFloat("mapreduce.input.fileinputformat.split.minsize.per.rack",splitFileSize);

        configuration.setInt(JobContext.NUM_REDUCES, numReduceTasks);
    }

    public static Long getTotalFileSize(String inputPath ,Configuration configuration,TrackerConfig config) throws IOException {

        Long totalSize =0L;
        FileSystem fs = FileSystem.get(configuration);
        FileStatus[] stats = fs.listStatus(new Path(inputPath));
        for(FileStatus file : stats){
            if(file.isDirectory()&&!file.getPath().getName().startsWith(".")){
                for(FileStatus fileStatus : stats){
                    totalSize+=fileStatus.getLen();
                }
            }
        }
        return totalSize;
    }
}
