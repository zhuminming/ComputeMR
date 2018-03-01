package com.HadoopDemo.common;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.JobContext;
import java.io.IOException;


/**
 * Created by zmm on 2017/11/15.
 */
public class MRConfig {
    public static Configuration createConfiguration(boolean isLocal,TrackerConfig trackerConfig ,String inputPath) throws IOException {
        Configuration configuration = new Configuration();
        configuration.set("hbase.zookeeper.quorum", "10.100.2.92,10.100.2.93,10.100.2.94");
        configuration.set(Constant.TRACKER_CONFIG, SerializeUtil.serialize(trackerConfig));
        //copy阶段内存使用的最大值
        configuration.setFloat(JobContext.SHUFFLE_INPUT_BUFFER_PERCENT, 0.1f);
        //每次merge的文件个数，关系到需要merge次数的参数
        configuration.setInt(JobContext.IO_SORT_FACTOR, 100);
        //task超时时间(不检查超时时间)
        configuration.setInt(JobContext.TASK_TIMEOUT, 0);
        //同时创建的fetch线程个数
        configuration.setInt(JobContext.SHUFFLE_PARALLEL_COPIES, 8);
        //每个fetch取到的输出的大小能够占的内存比的大小
        configuration.setFloat(JobContext.SHUFFLE_MEMORY_LIMIT_PERCENT, 0.25f);
        if(isLocal){
            configuration.setInt(JobContext.NUM_MAPS, 1);
            configuration.setInt(JobContext.NUM_REDUCES, 1);
        }else if(inputPath!=null){
            setValueConfig(inputPath,configuration,trackerConfig);
        }

        return configuration;
    }

    public static void setValueConfig(String inputPath ,Configuration configuration,TrackerConfig config) throws IOException {
        Long totalFileSize = getTotalFileSize(inputPath,configuration,config);
        Long splitFileSize = config.getMrInputSplitSize();
        int mapNum = (int) (totalFileSize/splitFileSize)+1;
        if(mapNum>config.getMrMaxMapNum()){
            splitFileSize = totalFileSize/config.getMrMaxMapNum()+1;  //设置FileSplit文件的大小
            mapNum = (int) (totalFileSize/splitFileSize) +1;          //设置map的个数
        }

        int numReduceTasks = (int) (mapNum * config.getMrMapReduceNumRate())+1;  //设置reduce的个数
        configuration.setFloat("mapreduce.input.fileinputformat.split.maxsize",splitFileSize);
        configuration.setFloat("mapreduce.input.fileinputformat.split.minsize.per.node",splitFileSize);
        configuration.setFloat("mapreduce.input.fileinputformat.split.minsize.per.rack",splitFileSize);

        configuration.setInt(JobContext.NUM_REDUCES, numReduceTasks);
    }

    public static Long getTotalFileSize(String inputPath ,Configuration configuration,TrackerConfig config) throws IOException {

        Long totalSize =0L;
        FileSystem fs = FileSystem.get(configuration);
        FileStatus[] stats = fs.listStatus(new Path(inputPath));
        for(FileStatus file : stats){
            if(file.isDirectory()&&!file.getPath().getName().startsWith(".")){
                for(FileStatus fileStatus : stats){
                    totalSize+=fileStatus.getLen();
                }
            }
        }
        return totalSize;
    }

    //设置连接mysql数据库的属性配置，用于MR以Mysql为数据源
    public static Configuration getConfigValueByBD(boolean isLocal,TrackerConfig trackerConfig, String inputPath) throws IOException {

        Configuration config = createConfiguration(isLocal, trackerConfig, inputPath);
        String driverClass = "com.mysql.jdbc.Driver";
        String dbUrl = "jdbc:mysql://" + trackerConfig.getHost() + "/"+ trackerConfig.getDatabase();
        DBConfiguration.configureDB(config, driverClass, dbUrl,
                trackerConfig.getUsername(), trackerConfig.getPassword());
        //只设置一个map和reduce
        config.setInt(JobContext.NUM_MAPS, 1);
        config.setInt(JobContext.NUM_REDUCES, 1);
        return config;

    }
}
