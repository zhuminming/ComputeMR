package HadoopDemo.mr.file;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;


/**
 * 文件名：CombineSmallFileRecordReader
 * 功能：自定义小文件读取类
 * 创建人：minming.zhu
 * 创建日期：2016-12-21
 */
public class CombineSmallFileRecordReader extends RecordReader<Object, Text>{
	private CombineFileSplit combineFileSplit;
	private int currentIndex;
	public  CombineSmallFileRecordReader(CombineFileSplit combineFileSplit,int index) {
		// TODO Auto-generated constructor stub
		super();
		this.combineFileSplit=combineFileSplit;
		this.currentIndex = index;//当前小文件Block在CombineFileSplit中索引。
		
	}
	
	
	@Override
	public void initialize(InputSplit split, TaskAttemptContext context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		this.combineFileSplit = (CombineFileSplit) split;
		
		//
	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getCurrentKey() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Text getCurrentValue() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
