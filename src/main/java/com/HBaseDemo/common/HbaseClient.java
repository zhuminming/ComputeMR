package com.HBaseDemo.common;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import com.HadoopDemo.common.HConnectionPool;

public class HbaseClient {
	private HTableInterface hTableInterface=null;
	private String tablename;
	public HbaseClient(String tablename){
		this.tablename = tablename;
	}
	
	public void putPuts(String rowkey, String family, String qualifier, byte[] value) throws IOException{
		Put put=new Put(Bytes.toBytes(rowkey));
		put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier), value);
		put(put);
	}
	
	
	public void putPuts(List<Put> puts) throws IOException{
		HTableInterface hTable= getHTableInterface();
		hTable.put(puts);
	}
	
	
	public void put(Put put) throws IOException{
		HTableInterface hTable = getHTableInterface();
		hTable.put(put);
	}
	
	public HTableInterface getHTableInterface() throws IOException{
		if(hTableInterface==null){
			hTableInterface=HConnectionPool.getHConnection().getTable(tablename);
		}
		return hTableInterface;
	}
	
	
	public void flush(){
		try {
			synchronized(HbaseClient.class){
				hTableInterface.flushCommits();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
