package com.test.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by zmm on 2017-07-30.
 */
public class MyClient {
    static InetSocketAddress addr = new InetSocketAddress(MyServer.IPADDRESS,MyServer.PORT);
    public static void main(String[] args) throws IOException {
        System.out.println("client start running......");
        IProxyProtocol proxy =  RPC.getProxy(IProxyProtocol.class, IProxyProtocol.versionID, addr, new Configuration());
        proxy.add(35,35);
        System.out.println("35+35");

        RPC.stopProxy(proxy);
    }
}
