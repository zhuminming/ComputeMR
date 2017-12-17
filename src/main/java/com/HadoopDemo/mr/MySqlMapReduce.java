package com.HadoopDemo.mr;

import com.HadoopDemo.common.DBConfiguration;
import com.HadoopDemo.common.TrackerConfig;
import com.HadoopDemo.inputFormat.DBInputFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Author: zhuminming
 * @create: 2017/12/17 11:07
 * @GitHubAddress: https://github.com/zhuminming
 */
public class MySqlMapReduce extends AbstractMapReduce{
    private static final Logger LOG = LoggerFactory.getLogger(MySqlMapReduce.class);
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
    private Class<DBInputFormat>                 inputFormatClass;          //设置Mapreduce输入文件的切分规则
    private String                               inputQuery;                //
    private String                               inputCountQuery;           //
    private Class<? extends DBWritable >         inputClass;                //

    public MySqlMapReduce(String jarname,
                          Class<?> jarByClass,
                          Class<? extends Mapper<?, ?, ?, ?>> mapperClass,
                          Class<? extends DBWritable >         inputClass,
                          Class<?> outputKeyClass,
                          Class<?> outputValuesClass,
                          Class<? extends Partitioner<?, ?>> partitionerClass,
                          Class<? extends Reducer<?, ?, ?, ?>> combinerClass,
                          Class<? extends Reducer<?, ?, ?, ?>> reducerClass,
                          Integer numReduceTasks,
                          String inputQuery,
                          String inputCountQuery,
                          String inputPath,
                          String outputPath) throws IOException {
        this.jarname = jarname;
        this.jarByClass = jarByClass;
        this.mapperClass= mapperClass;
        this.inputClass=inputClass;
        this.outputKeyClass=outputKeyClass;
        this.outputValuesClass=outputValuesClass;
        this.partitionerClass=partitionerClass;
        this.combinerClass=combinerClass;
        this.reducerClass=reducerClass;
        this.numReduceTasks=numReduceTasks;
        this.inputPath=inputPath;
        this.outputPath=outputPath;
        this.inputFormatClass=DBInputFormat.class;
        this.trackerConfig = TrackerConfig.getInstance();
        this.config = com.HadoopDemo.common.MRConfig.createConfiguration(false, this.trackerConfig, this.inputPath);
        this.inputQuery=inputQuery;
        this.inputCountQuery=inputCountQuery;
    }
    @Override
    public boolean waitForCompletion() throws IOException, ClassNotFoundException, InterruptedException {
        return buildJob().waitForCompletion(true);
    }

    @Override
    protected Job buildJob() throws IOException {
        Job job = Job.getInstance(this.config,this.jarname);
        DBInputFormat.setInput(job,
                this.inputClass,
                this.inputQuery, this.inputCountQuery);
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
        //mapreduce输出
        FileOutputFormat.setOutputPath(job, new Path(this.outputPath));

        return job;
    }
}
