package com.HadoopDemo.mr;

import com.HadoopDemo.common.*;
import com.HadoopDemo.common.MRConfig;
import com.HadoopDemo.inputFormat.db.DBInputFormat;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;
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
    private String                                       inputCountQuery;
    private String                                       inputQuery;
    private Class<? extends DBWritable>                  inputClass;

    public MySqlMapReduce(Class<?> jarByClass,
                          Class<? extends Mapper<?, ?, ?, ?>> mapperClass,
                          Class<? extends DBWritable> inputClass,
                          Class<? extends WritableComparable<?>> outputKeyClass,
                          Class<?> outputValueClass, TrackerConfig trackerconfig, String outputPath, String inputQuery,
                          String inputCountQuery) throws IOException {
        this.jarByClass = jarByClass;
        this.mapperClass = mapperClass;
        this.inputClass = inputClass;
        this.outputKeyClass = outputKeyClass;
        this.outputValueClass = outputValueClass;
        this.inputFormatClass = (Class<? extends InputFormat>) DBInputFormat.class;
        this.outputPath = outputPath;
        this.trackerConfig = trackerconfig;
        this.config = MRConfig.getConfigValueByBD(false, this.trackerConfig, null);
        this.inputQuery = inputQuery;
        this.inputCountQuery = inputCountQuery;
    }

    @Override
    public boolean waitForCompletion() throws ClassNotFoundException,
            IOException, InterruptedException {
        // TODO Auto-generated method stub
        return buildJob().waitForCompletion(true);
    }

    @Override
    protected Job buildJob() throws IOException{
        Job job = null;
        if(this.jarname !=null){
            job=Job.getInstance(this.config, this.jarname);
        }else {
            job=Job.getInstance(this.config, this.jarByClass.getSimpleName());
        }
        job.setJarByClass(this.jarByClass);

        DBInputFormat.setInput(job, this.inputClass, inputQuery, inputCountQuery);
        //map
        job.setMapperClass(this.mapperClass);
        job.setOutputKeyClass(this.outputKeyClass);
        job.setOutputValueClass(this.outputValueClass);
        //partition
        if (this.partitionerClass != null) {
            job.setPartitionerClass(this.partitionerClass);
        }
        //combine
        if (this.combinerClass != null) {
            job.setCombinerClass(this.combinerClass);
        }
        //reduce
        if (this.reducerClass != null) {
            job.setReducerClass(this.reducerClass);
            //reduce任务数
            if (this.numReduceTasks != null) {
                job.setNumReduceTasks(this.numReduceTasks);
            }
        }
        //groupingComparator
        if (this.groupingComparatorClass != null) {
            job.setGroupingComparatorClass(this.groupingComparatorClass);
        }

        //mapOutputKeyClass
        if (this.mapOutputKeyClass != null) {
            job.setMapOutputKeyClass(this.mapOutputKeyClass);
        }

        //mapOutputValueClass
        if (this.mapOutputValueClass != null) {
            job.setMapOutputValueClass(this.mapOutputValueClass);
        }

        //输入
        job.setInputFormatClass(this.inputFormatClass);
        //输出
        FileOutputFormat.setOutputPath(job, new Path(this.outputPath));

        return job;
    }

    public MySqlMapReduce setPartitionerClass(
            Class<? extends Partitioner<?, ?>> partitionerClass) {
        this.partitionerClass = partitionerClass;
        return this;
    }

    public MySqlMapReduce setCombinerClass(Class<? extends Reducer<?, ?, ?, ?>> combinerClass) {
        this.combinerClass = combinerClass;
        return this;
    }

    public MySqlMapReduce setReducerClass(Class<? extends Reducer<?, ?, ?, ?>> reducerClass) {
        this.reducerClass = reducerClass;
        return this;
    }

    public MySqlMapReduce setNumReduceTasks(Integer numReduceTasks) {
        this.numReduceTasks = numReduceTasks;
        return this;
    }

    public MySqlMapReduce setInputFormatClass(
            Class<? extends InputFormat<?, ?>> inputFormatClass) {
        this.inputFormatClass = inputFormatClass;
        return this;
    }

    public MySqlMapReduce setGroupingComparatorClass(
            Class<? extends WritableComparator> groupingComparatorClass) {
        this.groupingComparatorClass = groupingComparatorClass;
        return this;
    }

    public MySqlMapReduce setMapOutputKeyClass(Class<? extends WritableComparable<?>> mapOutputKeyClass) {
        this.mapOutputKeyClass = mapOutputKeyClass;
        return this;
    }

    public MySqlMapReduce setMapOutputValueClass(Class<?> mapOutputValueClass) {
        this.mapOutputValueClass = mapOutputValueClass;
        return this;
    }

    public MySqlMapReduce setJarName(String jarName){
        this.jarname = jarName;
        return this;
    }
}
