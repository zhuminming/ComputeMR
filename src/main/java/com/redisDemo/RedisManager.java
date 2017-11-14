package com.redisDemo;

import com.HadoopDemo.common.TrackerConfig;
import redis.clients.jedis.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @Auhtor：zhuminming
 * @Date:2017-10-31 22:09
 */
public class RedisManager {

    private JedisCluster jedisCluster;
    private Jedis jedis;                        //非切片额客户端连接
    private JedisPool jedisPool;                //非切片连接池
    private ShardedJedis shardedJedis;          //切片额客户端连接
    private ShardedJedisPool shardedJedisPool;  //切片连接池
    private Pipeline pipeline;

    private static Set<HostAndPort> clusterNOdes;

    public RedisManager(){
        initPool();
    }

    public void initPool(){
        try {
            List<String> lists = TrackerConfig.getInstance().getRedisCluster();
            for(String node:lists){
                HostAndPort hostAndPort = new HostAndPort(node.split(":")[0],Integer.parseInt(node.split(":")[1]));
                clusterNOdes.add(hostAndPort);
            }
            jedisCluster = new JedisCluster(clusterNOdes);
            pipeline = new Pipeline();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void PipeLineData(){
    }

    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }

    public static void main(String[] args){

        RedisManager manager = new RedisManager();
        manager.initPool();
        JedisCluster cluster = manager.getJedisCluster();

        System.out.println("连接成功");
        //设置 redis 字符串数据
        manager.jedis.set("runoobkey", "www.runoob.com");
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: "+ manager.jedis.get("runoobkey"));
//        //查看服务是否运行
//        System.out.println("服务正在运行: "+manager.jedis.ping());

    }
}
