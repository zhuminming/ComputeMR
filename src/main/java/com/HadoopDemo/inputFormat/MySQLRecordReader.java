package com.HadoopDemo.inputFormat;

import com.HadoopDemo.common.DBConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: zhuminming
 * @create: 2017/12/17 11:11
 * @GitHubAddress: https://github.com/zhuminming
 */
public class MySQLRecordReader<T extends DBWritable> extends DBRecordReader<T> {

    public MySQLRecordReader(DBInputFormat.DBInputSplit split,
                               Class<T> inputClass, Configuration conf, Connection conn, DBConfiguration dbConfig,
                               String cond, String [] fields, String table) throws SQLException {
        super(split, inputClass, conf, conn, dbConfig, cond, fields, table);
    }

    // Execute statements for mysql in unbuffered mode.
    protected ResultSet executeQuery(String query) throws SQLException {
        statement = getConnection().prepareStatement(query,
                ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        statement.setFetchSize(Integer.MIN_VALUE); // MySQL: read row-at-a-time.
        return statement.executeQuery();
    }
}
