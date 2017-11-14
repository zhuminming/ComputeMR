package com.HadoopDemo.file;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.io.hfile.AbstractHFileReader;
import org.apache.hadoop.hbase.io.hfile.CacheConfig;
import org.apache.hadoop.hbase.io.hfile.HFile;
import org.apache.hadoop.hbase.io.hfile.HFileScanner;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**读取HFile文件
 * Created by zmm on 2017/11/14.
 */
public class HFileRecordReader extends RecordReader<ImmutableBytesWritable,KeyValue> {

    private HFile.Reader reader;
    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {

        Configuration job = context.getConfiguration();
        FileSplit filesplit = (FileSplit) split;
        Path path = filesplit.getPath();
        FileSystem fs = path.getFileSystem(job);
        CacheConfig cacheConf = new CacheConfig(job);
        reader = HFile.createReader(fs,path,cacheConf,job);

    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        return false;
    }

    @Override
    public ImmutableBytesWritable getCurrentKey() throws IOException, InterruptedException {
        return null;
    }

    @Override
    public KeyValue getCurrentValue() throws IOException, InterruptedException {
        HFileScanner scanner = reader.getScanner(false,false);
        return null;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return 0;
    }

    @Override
    public void close() throws IOException {

    }
}
