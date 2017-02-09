package com.HadoopDemo.common;


import java.io.*;
import java.util.Properties;

/**
 * Created by minming.zhu on 2017/1/5.
 */
public class TrackerConfig  implements Serializable {

    /**
     * mapreduce配置
     */
    private long  mrInputSplitSize;     //mapreduce中map输入分片的最大大小(单位：byte)
    private int   mrMaxMapNum;          //mapreduce中map的最大个数
    private float mrMapReduceNumRate;   //mapreduce中map个数与reduce个数的比值


    /**
     * hbase配置
     */
    private  int hbaseHConnectionNum;  //hbase hconnection连接数
    private  String zookeeper;         //zookeeper地址
    private  int poolSize;             //hbase线程池大小


    public TrackerConfig(String filepath){
        InputStream input = null;
        try{
            DefProperties properties = new DefProperties();
            input = new FileInputStream(filepath);
            properties.load(input);  //从输入流中读取属性列表（键 和 值）

            this.mrInputSplitSize = Long.parseLong(properties.getString("mapreduce.input.split.size", "2000000000"));     //默认2G
            this.mrMaxMapNum = properties.getInt("mapreduce.map.tasks.maxnum", 8);	               						 //默认8个map
            this.mrMapReduceNumRate = Float.parseFloat(properties.getString("mapreduce.mapandreduce.tasks.rate", "0.3")); //默认0.3

            this.zookeeper = properties.getString("hbase.zookeeper.quorum");
            this.poolSize = properties.getInt("hbase.pool.size", 15);	//hbase线程池大小默认为15
            this.hbaseHConnectionNum = properties.getInt("hbase.hconnection.num", 1);
        }catch(Exception e){
            e.printStackTrace();
        }



    }

    /**
     * 功能：初始化TrackerConfig
     * @throws IOException
     */
    public static TrackerConfig getInstance() throws IOException {
        File directory = new File(".");
        String thisDirPath = directory.getCanonicalPath();
        if(thisDirPath.startsWith(StringUtil.PATH_SPLIT)){
            int index = thisDirPath.indexOf(StringUtil.SIGN_CONFIG);
            return new TrackerConfig(thisDirPath.substring(0,index+StringUtil.SIGN_CONFIG.length())+"/conf/config.properties");
        }else{
            return new TrackerConfig("../ComputeMR/src/main/java/com/conf/config.properties");
        }
    }


    /**
     * 功能：自定义加载配置文件对象
     * @extends Properties    实现持久化属性集的类
     */
    private static  class DefProperties extends Properties{

        public Integer getInt(String name){
            return getInt(name, null);
        }

        public Integer getInt(String name , Integer defvalue){
            String value = super.getProperty(name);
            if(value == null){
                return defvalue;
            }else{
                return Integer.parseInt(value);
            }

        }

        public Long getLong(String name){
            return getLong(name,null);
        }

        public Long getLong(String name , Long defvalue){
            String value = super.getProperty(name);
            if(value == null){
                return defvalue;
            }else{
                return Long.parseLong(value);
            }
        }

        public Double getDouble(String name){
            return getDouble(name, null);
        }

        public Double getDouble(String name ,Double defvalue){
            String value = super.getProperty(name);
            if(value == null){
                return defvalue;
            }else{
                return Double.parseDouble(value);
            }
        }

        public String getString(String name){
            return getString(name, null);
        }

        public String getString(String name, String defaultValue){
            String value = super.getProperty(name);
            if(value == null){
                return defaultValue;
            } else {
                return value.trim();
            }
        }

    }

    public long getMrInputSplitSize() {
        return mrInputSplitSize;
    }

    public void setMrInputSplitSize(long mrInputSplitSize) {
        this.mrInputSplitSize = mrInputSplitSize;
    }

    public int getMrMaxMapNum() {
        return mrMaxMapNum;
    }

    public void setMrMaxMapNum(int mrMaxMapNum) {
        this.mrMaxMapNum = mrMaxMapNum;
    }

    public float getMrMapReduceNumRate() {
        return mrMapReduceNumRate;
    }

    public void setMrMapReduceNumRate(float mrMapReduceNumRate) {
        this.mrMapReduceNumRate = mrMapReduceNumRate;
    }

    public  int getHbaseHConnectionNum() {
        return hbaseHConnectionNum;
    }

    public  void setHbaseHConnectionNum(int hbaseHConnectionNum) {
        this.hbaseHConnectionNum = hbaseHConnectionNum;
    }

    public  String getZookeeper() {
        return zookeeper;
    }

    public  void setZookeeper(String zookeeper) {
        this.zookeeper = zookeeper;
    }

    public  int getPoolSize() {
        return poolSize;
    }

    public  void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

}
