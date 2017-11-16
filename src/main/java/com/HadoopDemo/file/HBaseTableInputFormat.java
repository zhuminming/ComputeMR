package com.HadoopDemo.file;

import com.HadoopDemo.common.Constant;
import com.HadoopDemo.common.SerializeUtil;
import com.HadoopDemo.common.TrackerConfig;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.mapreduce.TableSplit;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by minming.zhu on 2017/1/23.
 * 功能：合并region，减少map数
 */
public class HBaseTableInputFormat extends TableInputFormat {
    @Override
    public List<InputSplit> getSplits(JobContext context) throws IOException {
        //获取切割列表
        List<InputSplit> inputSplits = super.getSplits(context);
        List<InputSplit> newInputSplits = new ArrayList<InputSplit>();
        TableName tName = super.getHTable().getName();

        TrackerConfig config = (TrackerConfig) SerializeUtil.deSerialize(context.getConfiguration().get(Constant.TRACKER_CONFIG));
        int mapNum = config.getMrMaxMapNum();
        long splitSize = config.getMrInputSplitSize();

        long totalRegionSize = 0;
        for (int i = 0; i < inputSplits.size(); i++) {
            TableSplit ts = (TableSplit) inputSplits.get(i);
            totalRegionSize += ts.getLength();
        }
        int tmpMapNum = (int) Math.min(mapNum, totalRegionSize / splitSize);
        if(tmpMapNum == 0) tmpMapNum = 1;
        splitSize = totalRegionSize / tmpMapNum;
        if(splitSize == 0) splitSize = Integer.MAX_VALUE;

        int index = 0;
        while (index < inputSplits.size()) {
            TableSplit ts = (TableSplit) inputSplits.get(index);
            long totalSize = ts.getLength();
            byte[] splitStartKey = ts.getStartRow();
            byte[] splitEndKey = ts.getEndRow();
            index++;
            for (; index < inputSplits.size(); index++) {
                TableSplit nextRegion = (TableSplit) inputSplits.get(index);
                long nextRegionSize = nextRegion.getLength();
                if (totalSize + nextRegionSize < splitSize) {
                    totalSize = totalSize + nextRegionSize;
                    splitEndKey = nextRegion.getEndRow();
                } else {
                    break;
                }
            }
            TableSplit tsNew = new TableSplit(tName, splitStartKey,
                    splitEndKey, ts.getRegionLocation(), totalSize);
            newInputSplits.add(tsNew);
        }
        return newInputSplits;
    }
}
