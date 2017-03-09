
package com.monitor;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FsStatus;
import org.apache.hadoop.hbase.ClusterStatus;
import org.apache.hadoop.hbase.RegionLoad;
import org.apache.hadoop.hbase.ServerLoad;
import org.apache.hadoop.hbase.ServerName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;

import com.HadoopDemo.common.TrackerConfig;

public class DataMonitor {
    
    public static void printHbaseData() throws IOException {
    	TrackerConfig config = TrackerConfig.getInstance();
        HBaseAdmin admin = HbaseUtil.getHBaseAdmin(config.getZookeeper());
        ClusterStatus status = admin.getClusterStatus();
        
        System.out.println("===============live server=====================");
        for (ServerName ls : status.getServers()) {
            System.out.println(ls.getServerName());
        }
        System.out.println("===============dead server=====================");
        for (ServerName ds : status.getDeadServerNames()) {
        	System.out.println(ds.getServerName());
        }
        
        // each Hserver information
        Collection<ServerName> serverInfos = status.getServers();
        System.out.println("===============region server=====================");
        for (ServerName si : serverInfos) {
            ServerLoad sload = status.getLoad(si);

            System.out.println(si.getServerName() + "=>" + sload.getUsedHeapMB() + "=>" + sload.getMaxHeapMB() + "=>" + sload.getMemstoreSizeInMB() + "=>" + 
                    sload.getNumberOfRegions() + "=>" + sload.getNumberOfRequests() + "=>" +  sload.getStorefileIndexSizeInMB() + "=>" + 
                    sload.getStorefileSizeInMB() + "=>" + sload.getStorefiles());
        }
        
        System.out.println("===============table=====================");
        for (ServerName si : serverInfos) {
            ServerLoad sload = status.getLoad(si);
            // each Region information of that server
            Map<byte[], RegionLoad> regionLoads = sload.getRegionsLoad();
            for (byte[] regionnm : regionLoads.keySet()) {
                // set region server name
                RegionLoad rl = regionLoads.get(regionnm);
                String rname = rl.getNameAsString();
                System.out.println(si.getServerName() + "=>" +
                		rname + "=>" +
                        rl.getMemStoreSizeMB() + "=>" + rl.getStorefileSizeMB() + "=>" +
                        rl.getStorefiles() + "=>" +rl.getStorefileIndexSizeMB());
            }
        }
    }
    
    public static void printHdfsData() throws IOException {
    	TrackerConfig config = TrackerConfig.getInstance();
    	HdfsProxy proxy = new HdfsProxy(config.getHdfsClusterName(), config.getHdfsAddr());
    	Configuration conf = proxy.getConfiguration();
//    	conf.set("mapreduce.framework.name", "local");
//    	JobClient jobClient = new JobClient(conf);
    	
    	//namenode
    	FileSystem fs = proxy.getFileSystem();
    	
        System.out.println("===============namenode=====================");
		FsStatus fsStatus = fs.getStatus();
		float capacity = (float)fsStatus.getCapacity()/1024/1024/1024;
		float dfsUsed = (float)fsStatus.getUsed()/1024/1024/1024;
		float nonDfsUsed = (float)(fsStatus.getCapacity() - fsStatus.getUsed())/1024/1024/1024;
		float remaining = (float)fsStatus.getRemaining()/1024/1024/1024;
		float remain = (float)fsStatus.getRemaining() / (float)fsStatus.getCapacity();
		DecimalFormat df = new DecimalFormat("#.00");  
		float remainPercent = Float.parseFloat(df.format(remain*100)); 
		System.out.println(capacity + "G => " + dfsUsed + "G => " + nonDfsUsed + "G => " + remaining + "G => " + remainPercent + "%");
		
        System.out.println("===============datanode=====================");
		DistributedFileSystem hdfs = (DistributedFileSystem) fs;
		DatanodeInfo[] dataNodeStats = hdfs.getDataNodeStats();
		for(DatanodeInfo dataNodeInfo : dataNodeStats) {
			System.out.println(dataNodeInfo.getDatanodeReport());
		}
    }
    
    public static void main(String[] args) throws IOException {
        System.out.println("**************hbase*********************");
    	printHbaseData();
    	
    	 System.out.println("**************hdfs*********************");
    	 printHdfsData();
	}
}
