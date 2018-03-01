package com.HadoopDemo.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HdfsProxy {
	private static Logger LOG = LoggerFactory.getLogger(HdfsProxy.class);
	private FileSystem fileSystem;
	private String nameservices;    //hdfs集群名
	private String hdfsAddr;        //namenode的地址(ip:port)
	
	
	/**
	 * 功能：初始化hdfs操作类
	 * @param nameservices	hdfs集群名
	 * @param hdfsAddr		hdfsAddr ip:port
	 */
	public HdfsProxy(String nameservices,String hdfsAddr){
		this.nameservices = nameservices;
		this.hdfsAddr = hdfsAddr;
	}
	
	public Configuration getConfiguration(){
		Configuration configuration = new Configuration();
		//mycluster是hadoop集群名
		configuration.set("fs.defaultFS", "hdfs://" + nameservices);
		configuration.set("dfs.nameservices", nameservices);
		configuration.set("dfs.ha.namenodes." + nameservices, "nn1,nn2");
		configuration.set("dfs.namenode.rpc-address." + nameservices + ".nn1", hdfsAddr.split(",")[0].trim());
		configuration.set("dfs.namenode.rpc-address." + nameservices + ".nn2", hdfsAddr.split(",")[1].trim());
		configuration.set("dfs.client.failover.proxy.provider." + nameservices, "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
		configuration.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
		configuration.set("dfs.replication", "2");
		try {
			fileSystem = FileSystem.newInstance(configuration);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return configuration;
	}
	
	
	public FileSystem getFileSystem(){
		FileSystem fs = null;
		try {
			Configuration conf = getConfiguration();
			fs =FileSystem.newInstance(conf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fs;
	}
	
	/**
	 * 功能：读取指定文件内容
	 * @param path	hdfs中文件路径
	 * @return
	 */
	public byte[] readFileFromHdfs(String path){
		byte[] buffer = null;
		FSDataInputStream fsInput = null;
		FileSystem fs = null;

		try {
			fs = getFileSystem();
			fsInput = fs.open(new Path(path));
			if(fsInput!=null){
				buffer = new byte[fsInput.available()];
				int off = 0;
				int len = 0;
				int length = buffer.length;
				for(;;){
					len = fsInput.read(buffer);
					length -= len ;
					off += len;
					if(off<=0||length<=0){
						 break;
					 }
				}
				}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				fsInput.close();
				fs.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return buffer;
	}
	
	public void putData(String srcPath ,String destPath){
		FileSystem fs =null;
		try {
			fs = getFileSystem();
			fs.copyFromLocalFile(false, true, new Path(srcPath), new Path(destPath));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				fs.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean isFileExist(Path path){
		boolean result = false;
		FileSystem fs =null;
		try {
			fs = getFileSystem();
			result = fs.exists(path);
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }finally{
			try {
				fs.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		return result;
	}

	/**
	 * 功能：获取指定路径下所有文件
	 *
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public List<Path> getFilePaths(Path path) throws IOException {
		FileStatus[] status = fileSystem.listStatus(path);
		List<Path> paths = new ArrayList<Path>();
		if(status.length==0) return paths;
		for (FileStatus file : status) {
			if (file.isDirectory() && !file.getPath().getName().startsWith(".")) {
				paths.addAll(getFilePaths(file.getPath()));
			}
			if (file.isFile() && !file.getPath().getName().startsWith(".")) {
				paths.add(file.getPath());
			}
		}

		return paths;
	}
	/**
	 * 功能：关闭文件系统
	 */
	public void close() {
		if (fileSystem != null) {
			try {
				fileSystem.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
