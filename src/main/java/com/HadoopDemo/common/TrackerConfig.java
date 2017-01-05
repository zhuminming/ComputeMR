package com.HadoopDemo.common;


import java.io.*;
import java.util.Properties;

/**
 * Created by minming.zhu on 2017/1/5.
 */
public class TrackerConfig  implements Serializable {
    public TrackerConfig(String filepath){
        InputStream input = null;
        try{
            DefProperties properties = new DefProperties();
            input = new FileInputStream(filepath);
            properties.load(input);  //从输入流中读取属性列表（键 和 值）
        }catch(Exception e){
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
            return new TrackerConfig("");
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

}
