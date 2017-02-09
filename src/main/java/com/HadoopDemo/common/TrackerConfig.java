package com.HadoopDemo.common;


import java.io.*;
import java.util.Properties;

/**
 * Created by minming.zhu on 2017/1/5.
 */
public class TrackerConfig  implements Serializable {

    /**
     * mapreduce����
     */
    private long  mrInputSplitSize;     //mapreduce��map�����Ƭ������С(��λ��byte)
    private int   mrMaxMapNum;          //mapreduce��map��������
    private float mrMapReduceNumRate;   //mapreduce��map������reduce�����ı�ֵ


    /**
     * hbase����
     */
    private  int hbaseHConnectionNum;  //hbase hconnection������
    private  String zookeeper;         //zookeeper��ַ
    private  int poolSize;             //hbase�̳߳ش�С


    public TrackerConfig(String filepath){
        InputStream input = null;
        try{
            DefProperties properties = new DefProperties();
            input = new FileInputStream(filepath);
            properties.load(input);  //���������ж�ȡ�����б��� �� ֵ��

            this.mrInputSplitSize = Long.parseLong(properties.getString("mapreduce.input.split.size", "2000000000"));     //Ĭ��2G
            this.mrMaxMapNum = properties.getInt("mapreduce.map.tasks.maxnum", 8);	               						 //Ĭ��8��map
            this.mrMapReduceNumRate = Float.parseFloat(properties.getString("mapreduce.mapandreduce.tasks.rate", "0.3")); //Ĭ��0.3

            this.zookeeper = properties.getString("hbase.zookeeper.quorum");
            this.poolSize = properties.getInt("hbase.pool.size", 15);	//hbase�̳߳ش�СĬ��Ϊ15
            this.hbaseHConnectionNum = properties.getInt("hbase.hconnection.num", 1);
        }catch(Exception e){
            e.printStackTrace();
        }



    }

    /**
     * ���ܣ���ʼ��TrackerConfig
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
     * ���ܣ��Զ�����������ļ�����
     * @extends Properties    ʵ�ֳ־û����Լ�����
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
