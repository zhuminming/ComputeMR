package com.HadoopDemo.common;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.hadoop.hbase.client.HConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.HBaseDemo.common.HbaseUtil;
import com.google.common.collect.Lists;

public class HConnectionPool {
	private final static Logger LOG =LoggerFactory.getLogger(HConnectionPool.class);

	private static List<HConnection> connections=Lists.newArrayList();  //容器，空闲连接
	private static List<ExecutorService> executorList=Lists.newArrayList();
	private static AtomicInteger num = new AtomicInteger(0);
	private static int hbaseHConnectionNum;
	private static String zookeeper;
	private static int poolSize;


	/**
	 * 功能：初始化hbase连接池
	 * @param trackerConfig
	 */
	public static void initHConnectionPool(TrackerConfig trackerConfig){
		initHConnectionPool(trackerConfig.getHbaseHConnectionNum(),trackerConfig.getZookeeper(),trackerConfig.getPoolSize());
	}


	/**
	 * 功能：初始化连接池
	 * @param hbaseHConnectionNum	需要创建的HConnection个数
	 * @param zookeeper			zookeeper地址
	 * @param poolSize				每个HConnection并发数
	 */
	public static void initHConnectionPool(int hbaseHConnectionNum, String zookeeper, int poolSize){
		if(connections==null||connections.size()==0){
			synchronized (HConnection.class) {
				if(connections==null||connections.size()==0){
					for(int num=0;num<hbaseHConnectionNum;num++){
						ExecutorService pool = Executors.newFixedThreadPool(poolSize);
						executorList.add(pool);
						connections.add(HbaseUtil.createHConnection(zookeeper, pool));
					}
					HConnectionPool.hbaseHConnectionNum = hbaseHConnectionNum;
					HConnectionPool.zookeeper = zookeeper;
					HConnectionPool.poolSize = poolSize;
				}
			}
		}
	}


	/**
	 * 从连接池里得到连接
	 * @return
	 */
	public static HConnection getHConnection(){
		if(connections.size()==0){
			initHConnectionPool(hbaseHConnectionNum, zookeeper, poolSize);
		}
		int index = num.getAndIncrement()% hbaseHConnectionNum;
		return connections.get(index);
	}

	/**
	 * 关闭连接
	 * @return
	 */
	public static void close(){
		for(int i=0;i<connections.size();i++){
			try {
				connections.get(i).close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LOG.error("error to close hbase connection"+e.getMessage());
			}
		}
		for(int i = 0; i < executorList.size(); i++){
			executorList.get(i).shutdown();
		}
		connections.clear();
		executorList.clear();
	}

}
