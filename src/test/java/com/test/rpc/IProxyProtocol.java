package com.test.rpc;

import org.apache.hadoop.ipc.VersionedProtocol;

/**
 * Created by zmm on 2017-07-30.
 */
public interface IProxyProtocol extends VersionedProtocol {
    public static final long versionID=1L;
    public int add(int number1,int number2);
}
