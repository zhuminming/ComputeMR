package com.HadoopDemo.file;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.io.hfile.CacheConfig;
import org.apache.hadoop.hbase.io.hfile.HFile;
import org.apache.hadoop.hbase.io.hfile.HFileScanner;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;

import java.io.IOException;

/**
 * 文件名：HFileRecordReader
 * 功能：读取HFile文件
 * 创建人：minming.zhu
 * 创建日期：2017-11-14.
 */
public class HFileRecordReader extends RecordReader<ImmutableBytesWritable,KeyValue> {

    private HFile.Reader reader;
    private int index;
    private TaskAttemptContext context;
    private CombineFileSplit fileSplit;
    private HFileScanner scanner;
    private int entryCount=0;

    public HFileRecordReader(CombineFileSplit split ,TaskAttemptContext context,int index){

        this.context=context;
        this.fileSplit=split;
        this.index=index;

    }
    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {

        Configuration job = this.context.getConfiguration();
        Path path = this.fileSplit.getPath(index);
        FileSystem fs = path.getFileSystem(job);
        CacheConfig cacheConf = new CacheConfig(job);
        reader = HFile.createReader(fs,path,cacheConf,job);
        scanner = reader.getScanner(false,false);
        scanner.seekTo();

    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        entryCount++;
        if (entryCount == 1) return true;
        return scanner.next();
    }

    @Override
    public ImmutableBytesWritable getCurrentKey() throws IOException, InterruptedException {
        return new ImmutableBytesWritable(CellUtil.cloneRow(scanner.getKeyValue()));
    }

    @Override
    public KeyValue getCurrentValue() throws IOException, InterruptedException {
        return scanner.getKeyValue();
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        if(reader!=null){
            return entryCount/reader.getEntries();
        }
        return 0;
    }

    @Override
    public void close() throws IOException {
        if(reader!=null){
            reader.close();
        }
    }
}
