package com.HadoopDemo.common;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.HadoopDemo.file.CombineSmallFileInputFormat;
import com.HadoopDemo.file.HBaseTableInputFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件名：MapReduce
 * 功能：生成MapReude的job对象
 * 创建人：minming.zhu
 * 创建日期：2016-12-21
 */
	public class MapReduce {
	private static final Logger LOG = LoggerFactory.getLogger(MapReduce.class);
	private boolean                              isLocal;                   //是否是本地
	private String                               jarname;                   //设置jar名称
	private Class<?>                             jarByClass;                //设置mapreduce所在的类名
	private Class<? extends Mapper<?, ?, ?, ?>>  mapperClass;               //设置map类名
	private Class<? extends TableMapper<?,?>>    tableMapperClass;           //设置map类名
	private Class<?>                             outputKeyClass;            //设置map输出key值类型
	private Class<?>                             outputValuesClass;         //设置map输出value值类型
	private Class<? extends Partitioner<?, ?>>   partitionerClass;          //设置partitioner类名
	private Class<? extends Reducer<?, ?, ?, ?>> combinerClass;             //设置combiner类名
	private Class<? extends Reducer<?, ?, ?, ?>> reducerClass;              //设置redicer类名
	private Integer                              numReduceTasks;            //设置reduce个数
	private String                               inputPath;                 //设置Mapreduce输入路径
	private String                               outputPath;                //设置Mapreduce输出路径
	private Class<? extends InputFormat<?, ?>>   inputFormatClass;          //设置Mapreduce输入文件的切分规则
	private Configuration                        config;
	private TrackerConfig                        trackerConfig;             //自定义配置文件
	private static Scan                          scan;                      //设置scan


	/**
	 * 功能MapReduce构造函数
	 * @throws IOException
	 */
	public MapReduce(boolean isLocal,String jarname,
					 Class<?> jarByClass,
					 Class<? extends Mapper<?, ?, ?, ?>> mapperClass,
					 Class<?> outputKeyClass,
					 Class<?> outputValuesClass,
					 Class<? extends Partitioner<?, ?>> partitionerClass,
					 Class<? extends Reducer<?, ?, ?, ?>> combinerClass,
					 Class<? extends Reducer<?, ?, ?, ?>> reducerClass,
					 Integer numReduceTasks,
					 String inputPath,
					 String outputPath) throws IOException {
		this.isLocal = isLocal;
		this.jarname = jarname;
		this.jarByClass = jarByClass;
		this.mapperClass= mapperClass;
		this.outputKeyClass=outputKeyClass;
		this.outputValuesClass=outputValuesClass;
		this.partitionerClass=partitionerClass;
		this.combinerClass=combinerClass;
		this.reducerClass=reducerClass;
		this.numReduceTasks=numReduceTasks;
		this.inputPath=inputPath;
		this.outputPath=outputPath;
//		this.inputFormatClass=CombineSmallFileInputFormat.class;
		this.trackerConfig = TrackerConfig.getInstance();
		this.config = new Configuration();
		this.config.set("hbase.zookeeper.quorum", "10.100.2.92,10.100.2.93,10.100.2.94");
		this.config.set(Constant.TRACKER_CONFIG, SerializeUtil.serialize(this.trackerConfig));
		//copy阶段内存使用的最大值
		this.config.setFloat(JobContext.SHUFFLE_INPUT_BUFFER_PERCENT, 0.1f);
		//每次merge的文件个数，关系到需要merge次数的参数
		this.config.setInt(JobContext.IO_SORT_FACTOR, 100);
		//task超时时间(不检查超时时间)
		this.config.setInt(JobContext.TASK_TIMEOUT, 0);
		//同时创建的fetch线程个数
		this.config.setInt(JobContext.SHUFFLE_PARALLEL_COPIES, 8);
		//每个fetch取到的输出的大小能够占的内存比的大小
		this.config.setFloat(JobContext.SHUFFLE_MEMORY_LIMIT_PERCENT, 0.25f);
		this.config.setInt(JobContext.NUM_MAPS, 1);
		this.config.setInt(JobContext.NUM_REDUCES, 1);		
	}

	/**
	 * 功能MapReduce构造函数
	 * @throws IOException
	 */
	private MapReduce(boolean isLocal,String jarname,
					 Class<?> jarByClass,
					 Class<? extends TableMapper<?, ?>> tableMapperClass,
					 Class<?> outputKeyClass,
					 Class<?> outputValuesClass,
					 Class<? extends Partitioner<?, ?>> partitionerClass,
					 Class<? extends Reducer<?, ?, ?, ?>> combinerClass,
					 Class<? extends Reducer<?, ?, ?, ?>> reducerClass,
					 Integer numReduceTasks,
					 String outputPath,
					  Scan scan) throws IOException {
		this.isLocal = isLocal;
		this.jarname = jarname;
		this.jarByClass = jarByClass;
		this.tableMapperClass= tableMapperClass;
		this.outputKeyClass=outputKeyClass;
		this.outputValuesClass=outputValuesClass;
		this.partitionerClass=partitionerClass;
		this.combinerClass=combinerClass;
		this.reducerClass=reducerClass;
		this.numReduceTasks=numReduceTasks;
		this.outputPath=outputPath;
		this.inputFormatClass=CombineSmallFileInputFormat.class;
		this.trackerConfig = TrackerConfig.getInstance();
		this.config = new Configuration();
		this.config.set("hbase.zookeeper.quorum", "10.100.2.92,10.100.2.93,10.100.2.94");
		this.config.set(Constant.TRACKER_CONFIG, SerializeUtil.serialize(this.trackerConfig));
		this.config.setInt(JobContext.NUM_MAPS, 1);
		this.config.setInt(JobContext.NUM_REDUCES, 1);
		this.scan = scan;

	}
	/**
	 * 功能:获取读取hbase的Mapreduce
	 * @throws IOException
	 */
	public static MapReduce getMapReduce(boolean isLocal,String jarname,
								  Class<?> jarByClass,
								  Class<? extends TableMapper<?, ?>> tableMapperClass,
								  Class<?> outputKeyClass,
								  Class<?> outputValuesClass,
								  Class<? extends Partitioner<?, ?>> partitionerClass,
								  Class<? extends Reducer<?, ?, ?, ?>> combinerClass,
								  Class<? extends Reducer<?, ?, ?, ?>> reducerClass,
								  Integer numReduceTasks,
								  String outputPath,
	                              Scan scan)throws IOException{

		return new MapReduce(isLocal,jarname,jarByClass,tableMapperClass,outputKeyClass,outputValuesClass,partitionerClass,combinerClass,reducerClass,numReduceTasks,outputPath,scan);
	}

	/**
	 * 功能：启动job对象
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public boolean waitForCompletion() throws ClassNotFoundException, IOException, InterruptedException{
		if(scan!=null){
			return buildMapReduceJob().waitForCompletion(true);
		}
		return getJob().waitForCompletion(true);
	}


	/**
	 * 功能：获取JOB对象
	 * @throws IOException
	 */
	public Job getJob() throws IOException{
			Job job = Job.getInstance(this.config,this.jarname);
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
//			job.setInputFormatClass(this.inputFormatClass);
		    setInputPath(job,this.inputPath);
			//mapreduce输出					
			FileOutputFormat.setOutputPath(job, new Path(this.outputPath));
			
	        return job;			
	}

	/**
	 * 功能：获取处理Hbase的JOB对象
	 * @throws IOException
	 */
	public Job buildMapReduceJob() throws IOException{
		Job job = Job.getInstance(this.config,this.jarname);
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
				HBaseTableInputFormat.class);

		return job;
	}

	/**
	 * 功能：设置mapreduce输入路径
	 * @throws IOException
	 */
	public void setInputPath(Job job,String pathString) throws IOException{
		boolean hasInput = false;
		if(isLocal){

			for(String pathStr : pathString.split(StringUtil.VAL_SPLIT)){
				if(new File(pathStr).exists()){
					Path path = new Path(pathStr);
					FileInputFormat.addInputPath(job,path);
					hasInput = true;
				}else{
					LOG.warn("path " + pathStr + "is not exit !");
				}
			}
		}else{
			Hdfs hdfs = null;
			try{
				hdfs = new Hdfs(this.trackerConfig);
				for(String pathStr : pathString.split(StringUtil.VAL_SPLIT)){
					Path path = new Path(pathStr);
					if(hdfs.isFileExist(path)){
						List<Path> list = hdfs.getFileList(path);
						for(Path pt : list){
							FileInputFormat.addInputPath(job,pt);
							hasInput = true;
						}
					}else{
						LOG.warn("path " + pathStr + "is not exit !");
					}
				}
			}finally {
				if(hdfs!=null){
					hdfs.close();
				}
			}
			if (!hasInput) {
				throw new IOException("Set input path failed !");
			}
		}
	}
}
