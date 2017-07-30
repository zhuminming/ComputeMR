package com.test.rpc;

import org.apache.hadoop.ipc.ProtocolSignature;

import java.io.IOException;

/**
 * Created by zmm on 2017-07-30.
 */
public class MyProxy implements  IProxyProtocol
{
    public int add(int number1,int number2){
        System.out.println("我被调用了！");
        return number1+number2;
    }

    public long getProtocolVersion(String protocol,long clientVersion) throws IOException{
        System.out.println("MyProxy.ProtocolVersion=" + IProxyProtocol.VERSION);
        return IProxyProtocol.VERSION;
    }

    public ProtocolSignature getProtocolSignature(String protocol,
                                                  long clientVersion,
                                                  int clientMethodsHash) throws IOException{
        return null;
    }
}
