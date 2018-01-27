package com.HadoopDemo.mr;

import com.HadoopDemo.common.Hdfs;
import com.HadoopDemo.common.StringUtil;
import com.HadoopDemo.common.TrackerConfig;
import com.HadoopDemo.inputFormat.hfile.HFileInputFormat;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author: zhuminming
 * @create: 2017/12/17 10:56
 * @GitHubAddress: https://github.com/zhuminming
 */
public class HFileMapReduce extends AbstractMapReduce{
    private static final Logger LOG = LoggerFactory.getLogger(HFileMapReduce.class);
    private boolean                              isLocal;                   //是否是本地
    private String                               jarname;                   //设置jar名称
    private Class<?>                             jarByClass;                //设置mapreduce所在的类名
    private Class<? extends Mapper<?, ?, ?, ?>>  mapperClass;               //设置map类名
    private Class<?>                             outputKeyClass;            //设置map输出key值类型
    private Class<?>                             outputValuesClass;         //设置map输出value值类型
    private Class<? extends Partitioner<?, ?>>   partitionerClass;          //设置partitioner类名
    private Class<? extends Reducer<?, ?, ?, ?>> combinerClass;             //设置combiner类名
    private Class<? extends Reducer<?, ?, ?, ?>> reducerClass;              //设置redicer类名
    private Integer                              numReduceTasks;            //设置reduce个数
    private String                               inputPath;                 //设置Mapreduce输入路径
    private String                               outputPath;                //设置Mapreduce输出路径
    private Class<HFileInputFormat> inputFormatClass;          //设置Mapreduce输入文件的切分规则
    public HFileMapReduce(boolean isLocal,
                          String jarname,
                          Class<?> jarByClass,
                          Class<? extends Mapper<?, ?, ?, ?>> mapperClass,
                          Class<?> outputKeyClass,
                          Class<?> outputValuesClass,
                          Class<? extends Partitioner<?, ?>> partitionerClass,
                          Class<? extends Reducer<?, ?, ?, ?>> combinerClass,
                          Class<? extends Reducer<?, ?, ?, ?>> reducerClass,
                          Integer numReduceTasks,
                          String inputPath,
                          String outputPath) throws IOException {
        this.isLocal = isLocal;
        this.jarname = jarname;
        this.jarByClass = jarByClass;
        this.mapperClass= mapperClass;
        this.outputKeyClass=outputKeyClass;
        this.outputValuesClass=outputValuesClass;
        this.partitionerClass=partitionerClass;
        this.combinerClass=combinerClass;
        this.reducerClass=reducerClass;
        this.numReduceTasks=numReduceTasks;
        this.inputPath=inputPath;
        this.outputPath=outputPath;
        this.inputFormatClass=HFileInputFormat.class;
        this.trackerConfig = TrackerConfig.getInstance();
        this.config = com.HadoopDemo.common.MRConfig.createConfiguration(isLocal, this.trackerConfig, this.inputPath);
    }

    @Override
    public boolean waitForCompletion() throws IOException, ClassNotFoundException, InterruptedException {
        return buildJob().waitForCompletion(true);
    }

    @Override
    protected Job buildJob() throws IOException {
        Job job = Job.getInstance(this.config,this.jarname);
        //设置mapreduce所在的类名
        job.setJarByClass(this.jarByClass);
        //map类型
        job.setMapperClass(this.mapperClass);
        //map输出key值类型
        job.setOutputKeyClass(this.outputKeyClass);
        //map输出value值类型
        job.setOutputValueClass(this.outputValuesClass);
        //partitioner类名
        if(this.partitionerClass!=null){
            job.setPartitionerClass(this.partitionerClass);
        }
        //combiner类名
        if(this.combinerClass!=null){
            job.setCombinerClass(this.combinerClass);
        }
        //reducer类名
        if(this.reducerClass!=null){
            job.setReducerClass(this.reducerClass);
            if(this.numReduceTasks!=null){
                job.setNumReduceTasks(this.numReduceTasks);
            }
        }

        //mapreduce输入
        job.setInputFormatClass(this.inputFormatClass);
        setInputPath(job,this.inputPath);
        //mapreduce输出
        FileOutputFormat.setOutputPath(job, new Path(this.outputPath));

        return job;
    }
    /**
     * 功能：设置mapreduce输入路径
     * @throws IOException
     */
    public void setInputPath(Job job,String pathString) throws IOException{
        boolean hasInput = false;
        if(isLocal){
            for(String pathStr : pathString.split(StringUtil.VAL_SPLIT)){
                if(new File(pathStr).exists()){
                    Path path = new Path(pathStr);
                    FileInputFormat.addInputPath(job, path);
                    hasInput = true;
                }else{
                    LOG.warn("path " + pathStr + "is not exit !");
                }
            }
        }else{
            Hdfs hdfs = null;
            try{
                hdfs = new Hdfs(this.trackerConfig);
                for(String pathStr : pathString.split(StringUtil.VAL_SPLIT)){
                    Path path = new Path(pathStr);
                    if(hdfs.isFileExist(path)){
                        List<Path> list = hdfs.getFileList(path);
                        for(Path pt : list){
                            FileInputFormat.addInputPath(job,pt);
                            hasInput = true;
                        }
                    }else{
                        LOG.warn("path " + pathStr + "is not exit !");
                    }
                }
            }finally {
                if(hdfs!=null){
                    hdfs.close();
                }
            }
            if (!hasInput) {
                throw new IOException("Set input path failed !");
            }
        }
    }
}
