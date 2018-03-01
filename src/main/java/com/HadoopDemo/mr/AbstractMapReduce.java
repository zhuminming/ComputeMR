package com.HadoopDemo.mr;

import com.HadoopDemo.common.TrackerConfig;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.*;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author: zhuminming
 * @create: 2017/12/16 18:40
 * @GitHubAddress: https://github.com/zhuminming
 */
public abstract class AbstractMapReduce {
    public static final String MAPREDUCE_PARM = "hbase.mapreduce.parm";
    protected String                                  jarname;                    // 名称
    protected Class<?>                                jarByClass;                 // mapreduce所在类名
    protected Class<? extends Mapper<?, ?, ?, ?>>     mapperClass;                // map类名
    protected Class<? extends WritableComparable<?>>  outputKeyClass;             // reduce输出key类型
    protected Class<?>                                outputValueClass;           // reduce输出value类型
    protected TrackerConfig trackerConfig;              // 自定义配置文件
    protected Configuration config;                     // mapreduce job 配置对象
    protected String                                  outputPath;                 // 输出路径
    protected Class<? extends Partitioner<?, ?>>      partitionerClass;           // partition类名
    protected Class<? extends Reducer<?, ?, ?, ?>>    combinerClass;              // combine类名
    protected Class<? extends Reducer<?, ?, ?, ?>>    reducerClass;               // reduce类名
    protected Integer                                 numReduceTasks;             // reduce任务数
    protected String                                  inputPath;                  // 输入路径
    protected Class<? extends InputFormat>            inputFormatClass;           // InputFormatClass
    protected Class<? extends WritableComparator>     groupingComparatorClass;    // secondary
    protected Class<? extends WritableComparable<?>>  mapOutputKeyClass;          // map输出key类型
    protected Class<?>                                mapOutputValueClass;        // map输出key类型
    protected Class<? extends OutputFormat>           outputFormatClass;          // outputFormatClass


    /**
     * waitForCompletion
     * 启动mapreduce
     * @return
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws InterruptedException
     */
    public abstract boolean waitForCompletion() throws ClassNotFoundException, IOException, InterruptedException;
    /**
     * buildJob
     * 生成job对象
     * @return
     * @throws IOException
     */
    protected abstract Job buildJob() throws IOException;
}
