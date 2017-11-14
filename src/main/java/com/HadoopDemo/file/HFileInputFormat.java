package com.HadoopDemo.file;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.CombineFileRecordReader;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;

import java.io.IOException;
import java.util.List;

/**
 * 文件名：HFileInputFormat
 * 功能：Hbase中的HFile文件合并后再根据配置文件设置的maxsplit大小进行切割和读取
 * 创建人：minming.zhu
 * 创建日期：2017-11-14.
 */
public class HFileInputFormat extends CombineFileInputFormat<ImmutableBytesWritable,KeyValue>{
    //读取Hfile文件
    @Override
    public RecordReader<ImmutableBytesWritable, KeyValue> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException {
        return new CombineFileRecordReader<ImmutableBytesWritable, KeyValue>((CombineFileSplit)split,context,HFileRecordReader.class);
    }

    //切割文件
    @Override
    public List<InputSplit> getSplits(JobContext job) throws IOException {
        return super.getSplits(job);
    }
}
