package com.hadoop.test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.HBaseDemo.common.HbaseClient;
import com.HadoopDemo.common.Constant;
import com.HadoopDemo.common.HConnectionPool;
import com.HadoopDemo.common.MapReduce;
import com.HadoopDemo.common.SerializeUtil;
import com.HadoopDemo.common.TrackerConfig;

public class TestMapreduce {
	private static final Logger LOG =LoggerFactory.getLogger(TestMapreduce.class);
	public static class TestMapper extends Mapper<Object, Text, Text, Text>{
		private AtomicInteger count =new AtomicInteger(0);
		@Override
		public void setup(Context context) throws IOException, InterruptedException{
			super.setup(context);
		}
		
		@Override
		public void map(Object key,Text value,Context context) throws IOException, InterruptedException{
			String[] tmp = value.toString().split("\t");
			for(String str : tmp){
				context.write(new Text(str), new Text(Long.toString(1L)));
				count.incrementAndGet();
				if(count.get()%1000==0) LOG.info("count "+count.get());
			}
		}
		
		@Override
		public void cleanup(Context context) throws IOException, InterruptedException{
			LOG.info("count total "+count.get());
			super.cleanup(context);
		}
	}
	
	public static class TestReducer extends Reducer<Text, Text, Text, Text>{
		private HbaseClient client ;
		@Override
		public void setup(Context context) throws IOException, InterruptedException{
			super.setup(context);
			Configuration configuration = context.getConfiguration();
			TrackerConfig config =(TrackerConfig) SerializeUtil.deSerialize(configuration.get(Constant.TRACKER_CONFIG));
			HConnectionPool.initHConnectionPool(config);
			client =new HbaseClient("ubas:stats_web_page");
		}
		
		@Override
		public void reduce(Text key,Iterable<Text> values,Context context) throws IOException, InterruptedException{
			Long num=0L;
			for(Text value : values){
				num+=Long.parseLong(value.toString());
			}
			client.putPuts(key.toString(), "stats", "stats", Bytes.toBytes(num));
		}
		
		@Override
		public void cleanup(Context context) throws IOException, InterruptedException{
			HConnectionPool.close();
			client.flush();
			super.cleanup(context);
		}
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
		String inputPath = args[0];
		String outputPath =args[1];
		MapReduce mr = new MapReduce(true, "TestMapreduce", TestMapreduce.class, TestMapper.class, Text.class, Text.class, null, null, TestReducer.class, null, inputPath, outputPath+"/01");
		mr.waitForCompletion();
	}

}
