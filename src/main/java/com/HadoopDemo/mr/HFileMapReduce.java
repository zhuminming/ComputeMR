package com.HadoopDemo.mr;

import com.HadoopDemo.common.*;
import com.HadoopDemo.inputFormat.hfile.HFileInputFormat;
import com.google.common.collect.Lists;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.*;
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
    private final Logger LOG = LoggerFactory.getLogger(HFileMapReduce.class);
    private  Boolean           isLocal; // 是否本地运行

    public HFileMapReduce(Boolean isLocal, Class<?> jarByClass,
                          Class<? extends Mapper<?, ?, ?, ?>> mapperClass,
                          Class<? extends WritableComparable<?>> outputKeyClass,
                          Class<?> outputValueClass,
                          TrackerConfig trackerconfig, String inputPath, String outputPath)
            throws IOException {
        this.isLocal = isLocal;
        this.jarByClass = jarByClass;
        this.mapperClass = mapperClass;
        this.outputKeyClass = outputKeyClass;
        this.outputValueClass = outputValueClass;
        this.inputPath = inputPath;
        this.inputFormatClass = (Class<? extends InputFormat>) HFileInputFormat.class;
        this.outputPath = outputPath;
        this.trackerConfig = trackerconfig;
        this.config = com.HadoopDemo.common.MRConfig.createConfiguration(isLocal, this.trackerConfig, inputPath);
    }

    @Override
    public boolean waitForCompletion() throws ClassNotFoundException,
            IOException, InterruptedException {
        return buildJob().waitForCompletion(true);
    }


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
        job.setMapperClass(this.mapperClass);

        // reduce
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

        // OutputFormatClass
        if (this.outputFormatClass != null) {
            job.setOutputFormatClass(this.outputFormatClass);
        }

        // 输入
        job.setInputFormatClass(this.inputFormatClass);
        setInputPath(job, this.inputPath);
        // 输出
        FileOutputFormat.setOutputPath(job, new Path(this.outputPath));

        return job;
    }

    private void setInputPath(Job job, String inputPath) throws IOException {
        boolean hasInput = false;
        if (this.isLocal) {
            for (String pathStr : inputPath.split(",")) {
                if (new File(pathStr).exists()) {
                    List<Path> ps = scanDir(pathStr);
                    for (Path p : ps) {
                        FileInputFormat.addInputPath(job, p);
                        hasInput = true;
                    }
                } else {
                    LOG.warn("Path " + pathStr + " is not exist !");
                }
            }
        } else {
            HdfsProxy hdfs = null;
            try {
                hdfs = new HdfsProxy(this.trackerConfig.getHdfsClusterName(),this.trackerConfig.getHdfsAddr());
                for (String pathStr : inputPath.split(",")) {
                    Path path = new Path(pathStr);
                    if (hdfs.isFileExist(path)) {
                        List<Path> ps = hdfs.getFilePaths(path);
                        for (Path p : ps) {
                            FileInputFormat.addInputPath(job, p);
                            hasInput = true;
                        }
                    } else {
                        LOG.warn("Path " + pathStr + " is not exist !");
                    }
                }
            } finally {
                if (hdfs != null) {
                    hdfs.close();
                }
            }
        }
        if (!hasInput) {
            throw new IOException("Set input path failed !");
        }
    }
    /**
     * 获取本地指定目录下的所有文件
     * @param path
     * @return
     */
    private static List<Path> scanDir(String path) {
        File filePath = new File(path);
        File[] files = filePath.listFiles();
        List<Path> pathsList = Lists.newArrayList();
        for (File file : files) {
            if (file.isDirectory() && !file.getPath().contains(".")) {
                pathsList.addAll(scanDir(file.getAbsolutePath()));
            } else if (file.isFile() && !file.getPath().contains(".")) {
                pathsList.add(new Path(file.getAbsolutePath()));
            }
        }

        return pathsList;
    }

    public HFileMapReduce setIsCompressedOutput(boolean compressed) {
        if (!compressed) {
            this.config.setBoolean(
                    "mapreduce.output.fileoutputformat.compress", false);
        }
        return this;
    }

    public HFileMapReduce setParameter(String name, String value) {
        this.config.set(name, value);
        return this;
    }

    public HFileMapReduce setPartitionerClass(
            Class<? extends Partitioner<?, ?>> partitionerClass) {
        this.partitionerClass = partitionerClass;
        return this;
    }

    public HFileMapReduce setCombinerClass(
            Class<? extends Reducer<?, ?, ?, ?>> combinerClass) {
        this.combinerClass = combinerClass;
        return this;
    }

    public HFileMapReduce setReducerClass(
            Class<? extends Reducer<?, ?, ?, ?>> reducerClass) {
        this.reducerClass = reducerClass;
        return this;
    }

    public HFileMapReduce setNumReduceTasks(Integer numReduceTasks) {
        this.numReduceTasks = numReduceTasks;
        return this;
    }

    public HFileMapReduce setInputFormatClass(
            Class<? extends InputFormat> inputFormatClass) {
        this.inputFormatClass = inputFormatClass;
        return this;
    }

    public HFileMapReduce setGroupingComparatorClass(
            Class<? extends WritableComparator> groupingComparatorClass) {
        this.groupingComparatorClass = groupingComparatorClass;
        return this;
    }

    public HFileMapReduce setMapOutputKeyClass(
            Class<? extends WritableComparable<?>> mapOutputKeyClass) {
        this.mapOutputKeyClass = mapOutputKeyClass;
        return this;
    }

    public HFileMapReduce setMapOutputValueClass(
            Class<?> mapOutputValueClass) {
        this.mapOutputValueClass = mapOutputValueClass;
        return this;
    }

    public HFileMapReduce setJarName(String jarName){
        this.jarname = jarName;
        return this;
    }

    public HFileMapReduce setOutputFormatClass(Class<? extends OutputFormat> outputFormatClass){
        this.outputFormatClass = outputFormatClass;
        return this;
    }
}
