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
 * 描述：与HBase建立连接操作
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
}