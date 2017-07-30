package com.test.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Builder;

import java.io.IOException;


/**
 * Created by zmm on 2017-07-30.
 */
public class MyServer {
    public static int PORT= 6125;
    public static String IPADDRESS = "127.0.0.1";
    public static void main(String[] args) throws IOException {
        System.out.println("start server running......");
        Builder builder = new Builder(new Configuration());
        RPC.Server server = builder.setBindAddress(IPADDRESS).setPort(PORT).setProtocol(IProxyProtocol.class).setInstance(new MyProxy()).build();
        System.out.println("Server start to listen on " + PORT);
        server.start();

    }
}
