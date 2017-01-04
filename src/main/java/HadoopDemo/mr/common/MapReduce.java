package HadoopDemo.mr.common;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 文件名：MapReduce
 * 功能：生成MapReude的job对象
 * 创建人：minming.zhu
 * 创建日期：2016-12-21
 */
public class MapReduce {
	private boolean                              isLocal;              //是否是本地执行
	private String                               jarname;              //设置jar名称
	private Class<?>                             jarByClass;           //设置类名
	private Class<? extends Mapper<?, ?, ?, ?>>  mapperClass;          //设置map类名
	private Class<?>                             outputKeyClass;       //设置map操作key输出类型
	private Class<?>                             outputValuesClass;    //设置map操作value输出类型
	private Class<? extends Partitioner<?, ?>>   partitionerClass;     //partitioner类名
	private Class<? extends Reducer<?, ?, ?, ?>> combinerClass;
	private Class<? extends Reducer<?, ?, ?, ?>> reducerClass;
	private Integer                              numReduceTasks;
	private String                               inputPath;
	private String                               outputPath;
	private Class<? extends InputFormat<?, ?>>   inputFormatClass;
	private Configuration                        config;

	public MapReduce(){
		
	}
	
	public boolean waitForCompletion(boolean verbose) throws ClassNotFoundException, IOException, InterruptedException{
		return getJob().waitForCompletion(verbose);
	}
	
	public Job getJob() throws IOException{
			Job job = Job.getInstance();
			//设置MR名称
			job.setJar(this.jarname);
			//设置mapreduce所在的类名
			job.setJarByClass(this.jarByClass);
			//map类型
			job.setMapperClass(this.mapperClass);
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
			
			//mapreduce输入
			job.setInputFormatClass(this.inputFormatClass);
			//mapreduce输出					
			FileOutputFormat.setOutputPath(job, new Path(this.outputPath));
			
	        return job;			
	}
	
	public void setInputPath(Job job,String pathString){
		boolean hasInput = false;
		if(this.isLocal){
			for (String pathStr:this.inputPath.split(CommonUtil.M_DOUHAO_SPLIT)){
				if(new File(pathStr).exists()){
					Path path = new Path(pathStr);
//					FileInputFormat.addInputPath(job,path);
//					hasInput = true;

				}

			}

		}
	}
}
