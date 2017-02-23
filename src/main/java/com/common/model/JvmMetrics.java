package com.common.model;

public class JvmMetrics {
	protected enum element{
		ProcessName,
	    SessionId,
	    Hostname,
	    MemNonHeapUsedM,
	    MemNonHeapCommittedM,
	    MemNonHeapMaxM,
	    MemHeapUsedM,
	    MemHeapCommittedM,
	    MemHeapMaxM,
	    MemMaxM,
	    GcCount,
	    GcTimeMillis,
	    ThreadsNew,
	    ThreadsRunnable,
	    ThreadsBlocked,
	    ThreadsWaiting,
	    ThreadsTimedWaiting,
	    ThreadsTerminated,
	    LogFatal,
	    LogError,
	    LogWarn,
	    LogInfo;
	}
	private String ProcessName;              //进程名
	private String SessionId;                //
	private String Hostname;                 //主机号
	private String MemNonHeapUsedM;          //目前非堆内存中使用大小（MB）
	private String MemNonHeapCommittedM;     //目前非堆内存中提交大小（MB）
	private String MemNonHeapMaxM;           //最大非堆内存的大小（MB）
	private String MemHeapUsedM;             //目前堆内存中使用大小（MB）
	private String MemHeapCommittedM;        //目前堆内存中提交大小（MB）
	private String MemHeapMaxM;              //最大堆内存的大小（MB）
	private String MemMaxM;                  //最大内存大小
	private String GcCount;                  //GC总数
	private String GcTimeMillis;             //GC时间（毫秒）
	private String ThreadsNew;               //目前新建线程数
	private String ThreadsRunnable;          //目前运行线程数
	private String ThreadsBlocked;           //目前阻塞线程的数
	private String ThreadsWaiting;           //目前等待线程数
	private String ThreadsTimedWaiting;      //目前定时等待线程数
	private String ThreadsTerminated;        //目前结束线程数
	private String LogFatal;                 //目前致命的日志的总数
	private String LogError;                 //目前出错的日志的总数
	private String LogWarn;                  //目前警告的日志的总数
	private String LogInfo;                  //目前输出的日志的总数
}
