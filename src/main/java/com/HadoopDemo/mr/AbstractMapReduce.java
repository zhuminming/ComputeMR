package com.HadoopDemo.mr;

import com.HadoopDemo.common.TrackerConfig;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author: zhuminming
 * @create: 2017/12/16 18:40
 * @GitHubAddress: https://github.com/zhuminming
 */
public abstract class AbstractMapReduce {
    protected Configuration config;
    protected TrackerConfig trackerConfig;             //自定义配置文件
    public abstract boolean waitForCompletion() throws IOException, ClassNotFoundException, InterruptedException; //启动job任务
    protected abstract Job buildJob() throws IOException;
    public <T> AbstractMapReduce buildMapReduce(Class<T> clazz,Object... objects) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?>[] argTypes = new Class<?>[objects.length];
        for(int i=0;i<objects.length;i++){
            argTypes[i]=objects[i].getClass();
        }
        Constructor<?> cons=clazz.getConstructor(argTypes);
        return (AbstractMapReduce) cons.newInstance(objects);
    }
}
