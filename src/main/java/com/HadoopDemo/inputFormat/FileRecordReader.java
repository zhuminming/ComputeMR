package com.HadoopDemo.inputFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.IOException;

/**
 * ComputeMR
 * ${DESCRIPTION}
 *
 * @Author: zhuminming
 * @create: 2017/12/16 11:51
 * @GitHubAddress: https://github.com/zhuminming
 */
public class FileRecordReader extends RecordReader<Object,Text> {
    private CombineFileSplit split;
    private TaskAttemptContext context;
    private Integer currentindex;
    private LineRecordReader reader;

    public FileRecordReader(CombineFileSplit split,TaskAttemptContext context,Integer index){
        this.split=split;
        this.context= context;
        this.currentindex =index;
    }
    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {

        Configuration configuration = context.getConfiguration();
        Path path = this.split.getPath(currentindex);
        FileSplit fileSplit = new FileSplit(path,this.split.getOffset(currentindex),this.split.getLength(currentindex),this.split.getLocations());
        this.reader = ReflectionUtils.newInstance(LineRecordReader.class,configuration);
        this.reader.initialize(fileSplit,context);
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        return this.reader.nextKeyValue();
    }

    @Override
    public Object getCurrentKey() throws IOException, InterruptedException {
        return this.reader.getCurrentKey();
    }

    @Override
    public Text getCurrentValue() throws IOException, InterruptedException {
        return this.reader.getCurrentValue();
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return this.reader.getProgress();
    }

    @Override
    public void close() throws IOException {
        this.reader.close();

    }

}
