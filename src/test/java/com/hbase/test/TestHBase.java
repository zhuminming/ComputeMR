package com.hbase.test;

import com.HBaseDemo.common.HbaseUtil;
import com.google.common.collect.Lists;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
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
     */
    public static void main(String[] args) {
        config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "192.103.101.104");
        HbaseUtil.createTable(config, tableName, columnFamily);
        try {
            table = new HTable(config, Bytes.toBytes(tableName));
            for (int k = 0; k < 1; k++) {
                Thread t = new Thread() {
                    public void run() {
                        for (int i = 0; i < 100000; i++) {
                            List<Put>  list = Lists.newArrayList();
                            list.add(HbaseUtil.createPut("stats"));
                            HbaseUtil.inputData(table,list);
                            Calendar c = Calendar.getInstance();
                            String dateTime = c.get(Calendar.YEAR) + "-"
                                    + c.get(Calendar.MONTH) + "-"
                                    + c.get(Calendar.DATE) + "T"
                                    + c.get(Calendar.HOUR) + ":"
                                    + c.get(Calendar.MINUTE) + ":"
                                    + c.get(Calendar.SECOND) + ":"
                                    + c.get(Calendar.MILLISECOND) + "Z 写入: "
                                    + i * 1000;
                            System.out.println(dateTime);
                        }
                    }
                };
                t.start();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
