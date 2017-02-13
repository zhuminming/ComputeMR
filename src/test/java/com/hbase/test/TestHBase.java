package com.hbase.test;

import com.HBaseDemo.common.HbaseUtil;
import com.HadoopDemo.common.TrackerConfig;
import com.google.common.collect.Lists;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by zmm on 2017-01-15.
 */
public class TestHBase {
    private static Configuration config;
    private static String tableName = "angelHbase";
    private static HTable table = null;
    private static final String columnFamily = "wanganqi";

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args){
        config = HBaseConfiguration.create();
        TrackerConfig trackerConfig;
        HTableInterface table = null; 
		try {
			trackerConfig = TrackerConfig.getInstance();
			config.set("hbase.zookeeper.quorum",trackerConfig.getZookeeper() );
			table = new HTable(config, "ubas:stats_web_page");
			table.setAutoFlush(false);
	        for(int i=0;i<10;i++){
	        	Put put = new Put(Bytes.toBytes("test"+i));
	        	put.add(Bytes.toBytes("stats"), Bytes.toBytes("column"), Bytes.toBytes("value"+i));
	        	table.put(put);
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				table.flushCommits();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
       
    }
}
