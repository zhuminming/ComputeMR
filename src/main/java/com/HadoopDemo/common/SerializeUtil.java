package com.HadoopDemo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
/**
 * 文件名：SerializeUtil
 * 功能：序列化工具
 * 创建人：zhuminming
 * 创建日期：2017-02-09
 */
public class SerializeUtil {
    private static final Logger LOG = LoggerFactory.getLogger(SerializeUtil.class);


    public static String serialize(Object obj) {
        String serString =null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            //初始化流
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            //写入
            objectOutputStream.writeObject(obj);
            //编码转化
            serString = URLEncoder.encode(byteArrayOutputStream.toString("ISO-8859-1"),"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error("序列化对象失败！", e.getMessage());
        }finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                LOG.error("关闭输出流失败！", e);
            }
        }
        return serString;
    }

    public static Object deSerialize(String serString){

        Object obj =null;
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;
        try{
            //解码字符串并放入到流
            byteArrayInputStream= new ByteArrayInputStream(URLDecoder.decode(serString, "UTF-8").getBytes("ISO-8859-1"));
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            //写入
            obj=objectInputStream.readObject();

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("反序列化对象失败！", e.getMessage());
        } finally{
            try {
                if (byteArrayInputStream != null) {
                    byteArrayInputStream.close();
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                LOG.error("关闭输入流失败！", e);
            }
        }
        return obj;
    }
}
