package com.HadoopDemo.file;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.CombineFileRecordReader;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;


/**
 * 文件名：CombineSmallFileInputFormat
 * 功能：自定义小文件合并后再根据配置文件设置的maxsplit大小进行切割
 * 创建人：minming.zhu
 * 创建日期：2016-12-21
 */
public class CombineSmallFileInputFormat extends CombineFileInputFormat<Object, Text>{

	//读取文件
	@Override
	public RecordReader<Object, Text> createRecordReader(InputSplit split,TaskAttemptContext context) throws IOException {
		// TODO Auto-generated method stub
		return new CombineFileRecordReader<Object, Text>((CombineFileSplit)split, context, CombineSmallFileRecordReader.class);
	}

	//进行小文件的切割
	@Override
	public List<InputSplit> getSplits(JobContext job) throws IOException {
		return super.getSplits(job);
	}

}
