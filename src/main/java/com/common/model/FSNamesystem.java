package com.common.model;

public class FSNamesystem {
	public enum element{
		HAState,
		Hostname,
		MissingBlocks,
	    ExpiredHeartbeats,
	    TransactionsSinceLastCheckpoint,
	    TransactionsSinceLastLogRoll,
	    LastWrittenTransactionId,
	    LastCheckpointTime,
	    CapacityTotal,
	    CapacityTotalGB,
	    CapacityUsed,
	    CapacityUsedGB,
	    CapacityRemaining,
	    CapacityRemainingGB,
	    CapacityUsedNonDFS,
	    TotalLoad,
	    SnapshottableDirectories,
	    Snapshots,
	    BlocksTotal,
	    FilesTotal,
	    PendingReplicationBlocks,
	    UnderReplicatedBlocks,
	    CorruptBlocks,
	    ScheduledReplicationBlocks,
	    PendingDeletionBlocks,
	    ExcessBlocks,
	    PostponedMisreplicatedBlocks,
	    PendingDataNodeMessageCount,
	    MillisSinceLastLoadedEdits,
	    BlockCapacity,
	    StaleDataNodes,
	    TotalFiles;
	}
	
	private String HAState;                             //NameNode状态
	private String Hostname;                            //NameNode名称
	private int MissingBlocks;                          //当前缺失的块
	private int ExpiredHeartbeats;                      //过期的心跳的总数
	private int TransactionsSinceLastCheckpoint;        //截止最后一次汇报的个数
	private int TransactionsSinceLastLogRoll;           //截止最后一次汇报的日志个数
	private String LastWrittenTransactionId;            //最后一次写入汇报的ID
	private long LastCheckpointTime;                    //最后一次检查心跳时间
    private long CapacityTotal;                         //DFS磁盘总共大小(B)
    private float CapacityTotalGB;                      //DFS磁盘总共大小(GB)
    private long CapacityUsed;                          //DFS磁盘使用大小(B)
    private float CapacityUsedGB;                       //DFS磁盘使用大小(GB)
    private long CapacityRemaining;                     //DFS磁盘剩余大小(B)
    private float CapacityRemainingGB;                  //DFS磁盘剩余大小(GB)
    private long CapacityUsedNonDFS;                    //非DFS磁盘使用大小(B)
    private int TotalLoad;                              //目前总共连接次数
    private int SnapshottableDirectories;               //目前快照表目录的数量
    private int Snapshots;                              //目前的快照数量
    private int BlocksTotal;                            //目前系统中分配的块的数量
    private int FilesTotal;                             //目前的文件和目录总数
    private int PendingReplicationBlocks;               //等待复制块
    private int UnderReplicatedBlocks;                  //正在复制块
    private int CorruptBlocks;                          //损坏的块
    private int ScheduledReplicationBlocks;             //计划复制的块
    private int PendingDeletionBlocks;                  //正在删除的块
    private int ExcessBlocks;                           //剩余块的数量
    private int PostponedMisreplicatedBlocks;           //(HA-only)推迟复制当前的块
    private int PendingDataNodeMessageCount;            //(HA-only)目前待定的供以后处理块数据的备用NameNode数量
    private long MillisSinceLastLoadedEdits;            //(HA-only)自最后一次备份NameNode加载编辑日志的时间（毫秒）
    private long BlockCapacity;                         //每个块的大小(B)
    private int StaleDataNodes;                         //当前datanode由于延迟的心跳而被标记的
    private long TotalFiles;	                        //Current number of files and directories (same as FilesTotal)
	
	
}
