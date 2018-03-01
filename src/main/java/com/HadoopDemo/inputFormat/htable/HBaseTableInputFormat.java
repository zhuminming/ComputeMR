package com.HadoopDemo.inputFormat.htable;

import com.HadoopDemo.common.Constant;
import com.HadoopDemo.common.SerializeUtil;
import com.HadoopDemo.common.TrackerConfig;
import com.google.common.collect.Lists;
import com.sun.demo.jvmti.hprof.Tracker;
import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.mapreduce.TableSplit;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat;

import java.io.IOException;
import java.util.List;

/**
 * @Author: zhuminming
 * 功能：合并region，减少map数
 * @create: 2017/12/16 16:27
 * @GitHubAddress: https://github.com/zhuminming
 */
public class HBaseTableInputFormat extends TableInputFormat {
    @Override
    public List<InputSplit> getSplits(JobContext context) throws IOException{

        //获取切割列表
        Configuration configuration = context.getConfiguration();
        List<InputSplit> splitLists = super.getSplits(context);
        List<InputSplit> newsplitLists = Lists.newArrayList();
        TrackerConfig config = (TrackerConfig) SerializeUtil.deSerialize(configuration.get(Constant.TRACKER_CONFIG));
        int maps = config.getMrMaxMapNum();
        long splits = config.getMrInputSplitSize();
        long totalRegionSize = 0L;
        for(InputSplit split : splitLists){
            TableSplit tableSplit = (TableSplit) split;
            totalRegionSize +=tableSplit.getLength();
        }

        int tmpmaps = (int) (totalRegionSize/splits) + 1;
        if(tmpmaps>maps){
            splits= totalRegionSize/maps;
        }

        //从新分割region
        int index=0;
        TableName tableName = super.getHTable().getName();
        while(index < splitLists.size()){
            TableSplit tableSplit =(TableSplit) splitLists.get(index++);
            long totalSize=tableSplit.getLength();
            byte[] startkey = tableSplit.getStartRow();
            byte[] endkey = tableSplit.getEndRow();
            for(;index<splitLists.size();index++){
                TableSplit nextTableSplit =(TableSplit) splitLists.get(index);
                if(totalSize<splits){
                    totalSize+=nextTableSplit.getLength();
                    endkey = nextTableSplit.getEndRow();
                }else{
                    continue;
                }
            }
            TableSplit newTableSplit = new TableSplit(tableName,startkey,endkey,tableSplit.getRegionLocation(),totalSize);
            newsplitLists.add(newTableSplit);
        }
        return newsplitLists;
    }

}
