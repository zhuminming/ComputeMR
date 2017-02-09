package com.HBaseDemo.common;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by zmm on 2017-01-15.
 * 描述：与HBase相关操作，建表与插入数据
 */
public class HbaseUtil {
    private static final Logger LOG = LoggerFactory.getLogger(HbaseUtil.class);

    public static HConnection createHConnection(String hbaseZookeeperQuorum ,ExecutorService pool){
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", hbaseZookeeperQuorum);
        HConnection connection = null;

        try {
            connection =HConnectionManager.createConnection(conf, pool);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            LOG.error("can not create hbase connection", e);
        }
        return connection;
    }


    public static HConnection createHConnection(String hbaseZookeeperQuorum){
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", hbaseZookeeperQuorum);
        HConnection connection = null;

        try {
            connection =HConnectionManager.createConnection(conf);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            LOG.error("can not create hbase connection", e);
        }
        return connection;
    }


    public static void createTable(Configuration config, String tableName,String columnFamily) {
        HBaseAdmin hBaseAdmin;
        try {
            hBaseAdmin = new HBaseAdmin(config);
            if (hBaseAdmin.tableExists(tableName)) {
                return;
            }
            HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
            tableDescriptor.addFamily(new HColumnDescriptor(columnFamily));
            hBaseAdmin.createTable(tableDescriptor);
            hBaseAdmin.close();
        } catch (MasterNotRunningException e) {
            e.printStackTrace();
        } catch (ZooKeeperConnectionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void inputData(HTable table, List<Put> puts) {
        try {
            table.put(puts);
            table.flushCommits();
            puts.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Put createPut(String columnFamily) {
        String str = "hadoop spark hbase" ;
        byte[] family = Bytes.toBytes(columnFamily);
        byte[] rowKey = Bytes.toBytes(System.currentTimeMillis());
        Put put = new Put(rowKey);
        put.add(family, Bytes.toBytes("stats"), Bytes.toBytes(str));

        return put;
    }
}
