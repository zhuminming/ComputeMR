package com.HadoopDemo.mr;

import com.HadoopDemo.inputFormat.RegionInputFormat;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Author: zhuminming
 * @create: 2017/12/16 18:36
 * @GitHubAddress: https://github.com/zhuminming
 */
public class HbaseMapReduce extends AbstractMapReduce {
    private static final Logger LOG = LoggerFactory.getLogger(HbaseMapReduce.class);
    private String                               jarname;                   //设置jar名称
    private Class<?>                             jarByClass;                //设置mapreduce所在的类名
    private Class<? extends TableMapper<?,?>>    tableMapperClass;          //设置map类名
    private Class<?>                             outputKeyClass;            //设置map输出key值类型
    private Class<?>                             outputValuesClass;         //设置map输出value值类型
    private Class<? extends Partitioner<?, ?>>   partitionerClass;          //设置partitioner类名
    private Class<? extends Reducer<?, ?, ?, ?>> combinerClass;             //设置combiner类名
    private Class<? extends Reducer<?, ?, ?, ?>> reducerClass;              //设置redicer类名
    private Integer                              numReduceTasks;            //设置reduce个数
    private String                               inputPath;                 //设置Mapreduce输入路径
    private String                               outputPath;                //设置Mapreduce输出路径
    private Class<RegionInputFormat>             inputFormatClass;          //设置Mapreduce输入文件的切分规则
    private static Scan                          scan;                      //设置scan
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
        job.setMapperClass(this.tableMapperClass);
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

        //mapreduce输出
        FileOutputFormat.setOutputPath(job, new Path(this.outputPath));

        TableMapReduceUtil.initTableMapperJob(
                Bytes.toString(scan.getAttribute(Scan.SCAN_ATTRIBUTES_TABLE_NAME)),
                scan,
                this.tableMapperClass,
                this.outputKeyClass,
                this.outputValuesClass,
                job,
                true,
                RegionInputFormat.class);

        return job;
    }
}
