package HadoopDemo.mr.file;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat;


/**
 * 文件名：CombineSmallFileInputFormat
 * 功能：自定义小文件合并后再切割类
 * 创建人：minming.zhu
 * 创建日期：2016-12-21
 */
public class CombineSmallFileInputFormat extends CombineFileInputFormat<Object, Text>{
	@Override
	public RecordReader<Object, Text> createRecordReader(InputSplit split,TaskAttemptContext context) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
