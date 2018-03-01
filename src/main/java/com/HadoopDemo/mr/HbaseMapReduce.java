package com.HadoopDemo.mr;

import com.HadoopDemo.common.*;
import com.HadoopDemo.inputFormat.htable.HBaseTableInputFormat;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @Author: zhuminming
 * @create: 2017/12/16 18:36
 * @GitHubAddress: https://github.com/zhuminming
 */
public class HbaseMapReduce extends AbstractMapReduce {
    private Class<? extends TableMapper<?, ?>>    tableMapperClass;        // map类
    private Scan                                  scan;                    // 过滤条件集合

    public HbaseMapReduce(Class<?> jarByClass,
                          Class<? extends TableMapper<?, ?>> tableMapperClass,
                          Class<? extends WritableComparable<?>> outputKeyClass,
                          Class<?> outputValueClass,
                          TrackerConfig trackerconfig,
                          String outputPath) throws IOException {
        this.jarByClass = jarByClass;
        this.tableMapperClass = tableMapperClass;
        this.outputKeyClass = outputKeyClass;
        this.outputValueClass = outputValueClass;
        this.inputFormatClass = (Class<? extends InputFormat>) TableInputFormat.class;
        this.outputPath = outputPath;
        this.trackerConfig = trackerconfig;
        this.config = com.HadoopDemo.common.MRConfig.createConfiguration(false, this.trackerConfig, null);

    }

    @Override
    public boolean waitForCompletion() throws ClassNotFoundException,
            IOException, InterruptedException {
        // TODO Auto-generated method stub
        return buildJob().waitForCompletion(true);
    }

    /**
     * 功能：Hbase mapreduce操作数据 既有map操作又有reduce操作
     *
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected Job buildJob() throws IOException {
        Job job = null;
        if(this.jarname !=null){
            job=Job.getInstance(this.config, this.jarname);
        }else {
            job=Job.getInstance(this.config, this.jarByClass.getSimpleName());
        }
        job.setJarByClass(this.jarByClass);

        // map
        job.setMapperClass(this.tableMapperClass);
        job.setOutputKeyClass(this.outputKeyClass);
        job.setOutputValueClass(this.outputValueClass);
        // partition
        if (this.partitionerClass != null) {
            job.setPartitionerClass(this.partitionerClass);
        }
        // combine
        if (this.combinerClass != null) {
            job.setCombinerClass(this.combinerClass);
        }
        // reduce
        if (this.reducerClass != null) {
            job.setReducerClass(this.reducerClass);
            // reduce任务数
            if (this.numReduceTasks != null) {
                job.setNumReduceTasks(this.numReduceTasks);
            }
        }
        // groupingComparator
        if (this.groupingComparatorClass != null) {
            job.setGroupingComparatorClass(this.groupingComparatorClass);
        }

        // mapOutputKeyClass
        if (this.mapOutputKeyClass != null) {
            job.setMapOutputKeyClass(this.mapOutputKeyClass);
        }

        // mapOutputValueClass
        if (this.mapOutputValueClass != null) {
            job.setMapOutputValueClass(this.mapOutputValueClass);
        }


        // 输出
        FileOutputFormat.setOutputPath(job, new Path(this.outputPath));

        // OutputFormatClass
        if (this.outputFormatClass != null) {
            job.setOutputFormatClass(this.outputFormatClass);
        }

        // 输入
        TableMapReduceUtil.initTableMapperJob(Bytes.toString(scan
                        .getAttribute(Scan.SCAN_ATTRIBUTES_TABLE_NAME)), scan,
                this.tableMapperClass, this.outputKeyClass,
                this.outputValueClass, job, true, HBaseTableInputFormat.class);
        return job;
    }

    public HbaseMapReduce setScan(Scan scan) {
        this.scan = scan;
        return this;
    }

    public HbaseMapReduce setPartitionerClass(
            Class<? extends Partitioner<?, ?>> partitionerClass) {
        this.partitionerClass = partitionerClass;
        return this;
    }

    public HbaseMapReduce setCombinerClass(Class<? extends Reducer<?, ?, ?, ?>> combinerClass) {
        this.combinerClass = combinerClass;
        return this;
    }

    public HbaseMapReduce setReducerClass(Class<? extends Reducer<?, ?, ?, ?>> reducerClass) {
        this.reducerClass = reducerClass;
        return this;
    }

    public HbaseMapReduce setNumReduceTasks(Integer numReduceTasks) {
        this.numReduceTasks = numReduceTasks;
        return this;
    }

    public HbaseMapReduce setInputFormatClass(
            Class<? extends InputFormat<?, ?>> inputFormatClass) {
        this.inputFormatClass = inputFormatClass;
        return this;
    }

    public HbaseMapReduce setGroupingComparatorClass(
            Class<? extends WritableComparator> groupingComparatorClass) {
        this.groupingComparatorClass = groupingComparatorClass;
        return this;
    }

    public HbaseMapReduce setMapOutputKeyClass(Class<? extends WritableComparable<?>> mapOutputKeyClass) {
        this.mapOutputKeyClass = mapOutputKeyClass;
        return this;
    }

    public HbaseMapReduce setMapOutputValueClass(Class<?> mapOutputValueClass) {
        this.mapOutputValueClass = mapOutputValueClass;
        return this;
    }

    public HbaseMapReduce setIsCompressedOutput(boolean compressed) {
        if (!compressed) {
            this.config.setBoolean(
                    "mapreduce.output.fileoutputformat.compress", false);
        }
        return this;
    }

    public HbaseMapReduce setParameter(String name, String value) {
        this.config.set(name, value);
        return this;
    }

    public HbaseMapReduce setJarName(String jarName){
        this.jarname = jarName;
        return this;
    }

    public HbaseMapReduce setOutputFormatClass(Class<? extends OutputFormat> outputFormatClass){
        this.outputFormatClass = outputFormatClass;
        return this;
    }
}
