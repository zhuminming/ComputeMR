package com.HadoopDemo.inputFormat.hfile;

import com.HadoopDemo.common.Constant;
import com.HadoopDemo.common.SerializeUtil;
import com.HadoopDemo.common.TrackerConfig;
import com.google.common.collect.Lists;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.CombineFileRecordReader;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * 功能：Hbase中的HFile文件合并后再根据配置文件设置的maxsplit大小进行切割和读取
 * @Author: zhuminming
 * @create: 2017/12/16 12:52
 * @GitHubAddress: https://github.com/zhuminming
 */
public class HFileInputFormat extends CombineFileInputFormat<ImmutableBytesWritable,KeyValue>{
    //读取Hfile文件
    @Override
    public RecordReader<ImmutableBytesWritable, KeyValue> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException {
        return new CombineFileRecordReader<ImmutableBytesWritable, KeyValue>((CombineFileSplit)split,context, HFileRecordReader.class);
    }

    //切割文件
    @Override
    public List<InputSplit> getSplits(JobContext job) throws IOException {
        Configuration conf = job.getConfiguration();
        // 获取输入目录下所有文件
        Path[] path = getInputPaths(job);
        List<InputSplit> newInputSplits = Lists.newArrayList();
        TrackerConfig config = (TrackerConfig) SerializeUtil.deSerialize(conf.get(Constant.TRACKER_CONFIG));
        int mapNum = config.getMrMaxMapNum();
        long splitSize = config.getMrInputSplitSize();
        long totalHfileSize = 0;
        for (int i = 0; i < path.length; i++) {
            FileSystem fs = path[i].getFileSystem(conf);
            totalHfileSize += fs.getFileStatus(path[i]).getLen();
        }
        // 设置map个数
        int tmpMapNum = (int) Math.min(mapNum, totalHfileSize / splitSize);
        if (tmpMapNum == 0) tmpMapNum = 1;

        splitSize = totalHfileSize / tmpMapNum;
        if (splitSize == 0) splitSize = Integer.MAX_VALUE;

        int index = 0;
        //合并HFile文件，构造CombineFileSplit对象
        while (index < path.length) {
            FileSystem fs = null;
            int indexFile = 0;
            FileStatus status =null;
            Path[] files = new Path[path.length];
            long[] lengths = new long[path.length];
            long totalSize = 0l;
            for (; index < path.length; index++, indexFile++) {
                if (totalSize < splitSize) {
                    files[indexFile] = path[index];
                    fs = path[index].getFileSystem(conf);
                    status = fs.getFileStatus(path[index]);
                    lengths[indexFile] = status.getLen();
                    totalSize += status.getLen();
                } else {
                    break;
                }
            }
            CombineFileSplit combineFileSplit = new CombineFileSplit(Arrays.copyOfRange(files, 0, indexFile), Arrays.copyOfRange(lengths, 0,indexFile));
            newInputSplits.add(combineFileSplit);
        }
        return newInputSplits;
    }
}
