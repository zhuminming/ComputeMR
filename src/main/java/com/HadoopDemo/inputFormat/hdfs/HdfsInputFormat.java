package com.HadoopDemo.inputFormat.hdfs;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.CombineFileRecordReader;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;

import java.io.IOException;

/**
 * ComputeMR
 * ${DESCRIPTION}
 *
 * @Author: zhuminming
 * @create: 2017-12-2017/12/16 11:48
 * @GitHubAddress: https://github.com/zhuminming
 */
public class HdfsInputFormat extends CombineFileInputFormat<Object,Text> {
    @Override
    public RecordReader<Object, Text> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException {
        return new CombineFileRecordReader<Object, Text>((CombineFileSplit) split ,context,HdfsRecordReader.class);
    }
}
