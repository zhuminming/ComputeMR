package com.HadoopDemo.common;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by minming.zhu on 2017/1/5.
 */
public class TrackerConfig  implements Serializable{
    public TrackerConfig(String path){

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

}
