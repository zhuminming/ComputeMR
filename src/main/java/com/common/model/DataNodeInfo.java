package com.common.model;

public class DataNodeInfo {
	public enum element{
		BytesWritten,
		BytesRead,
		BlocksWritten,
	    BlocksRead,
		BlocksReplicated,
		BlocksRemoved,
		BlocksVerified,
		BlockVerificationFailures,
		BlocksCached,
		BlocksUncached,
		ReadsFromLocalClient,
		ReadsFromRemoteClient,
		WritesFromLocalClient,
		WritesFromRemoteClient,
		BlocksGetLocalPathInfo,
		FsyncCount,
		VolumeFailures,
		ReadBlockOpNumOps,
		ReadBlockOpAvgTime,
		WriteBlockOpNumOps,
		WriteBlockOpAvgTime,
		BlockChecksumOpAvgTime,
		CopyBlockOpNumOps,
		CopyBlockOpAvgTime,
		ReplaceBlockOpNumOps,
		ReplaceBlockOpAvgTime,
		HeartbeatsNumOps,
		HeartbeatsAvgTime,
		BlockReportsNumOps,
		BlockReportsAvgTime,
		CacheReportsNumOps,
		CacheReportsAvgTime,
		PacketAckRoundTripTimeNanosNumOps,
		PacketAckRoundTripTimeNanosAvgTime,
		FlushNanosNumOps,
		FlushNanosAvgTime,
		FsyncNanosNumOps,
		FsyncNanosAvgTime,
		SendDataPacketBlockedOnNetworkNanosNumOps,
		SendDataPacketBlockedOnNetworkNanosAvgTime,
		SendDataPacketTransferNanosNumOps,
		SendDataPacketTransferNanosAvgTime;
		
	}
	private long BytesWritten;                                  //写入DataNode的字节总数
	private long BytesRead;                                     //读取DataNode的字节总数
	private long BlocksWritten;                                 //块写入DataNode的总数
	private long BlocksRead;                                    //块读取DataNode的总数
	private int BlocksReplicated;                               //复制块的总数
	private int BlocksRemoved;                                  //移除块的总数
	private int BlocksVerified;                                 //验证块的总数
	private int BlockVerificationFailures;                      //验证失败块的总数
	private int BlocksCached;                                   //缓存块的总数
	private int BlocksUncached;                                 //没有缓存块的总数
	private int ReadsFromLocalClient;                           //从本地客户端读取操作的总数
	private int ReadsFromRemoteClient;                          //从远程客户端读取操作的总数
	private int WritesFromLocalClient;                          //写入本地客户端操作的总数
	private int WritesFromRemoteClient;                         //写入远程客户端操作的总数
	private int BlocksGetLocalPathInfo;                         //从本地获取块的总数
	private int FsyncCount;                                     //fsync总数
	private int VolumeFailures;                                 //出现故障总数
	private int ReadBlockOpNumOps;                              //读操作的总数
	private float ReadBlockOpAvgTime;                           //读取操作的平均时间（毫秒）
	private int WriteBlockOpNumOps;                             //写入操作总数
	private float WriteBlockOpAvgTime;                          //写入操作的平均时间（毫秒）
	private float BlockChecksumOpAvgTime;                       //块校验操作的平均时间（毫秒）
	private int CopyBlockOpNumOps;                              //块拷贝操作总数
	private float CopyBlockOpAvgTime;                           //块拷贝操作的平均时间（毫秒）
	private int ReplaceBlockOpNumOps;                           //块替换操作总数
	private float ReplaceBlockOpAvgTime;                        //块替换操作的平均时间（毫秒）
	private int HeartbeatsNumOps;                               //发送心跳总数
	private float HeartbeatsAvgTime;                            //平均心跳数（毫秒）
	private int BlockReportsNumOps;                             //块报告操作总数
	private float BlockReportsAvgTime;                          //块报告操作的平均时间（毫秒）
	private int CacheReportsNumOps;                             //块缓存替换操作总数
	private float CacheReportsAvgTime;                          //块缓存替换操作的平均时间（毫秒）
	private int PacketAckRoundTripTimeNanosNumOps;              //ack往返的总数
	private float PacketAckRoundTripTimeNanosAvgTime;           //从发送到接收消ack的平均时间（纳秒）
	private int FlushNanosNumOps;                               //flush总数
	private float FlushNanosAvgTime;                            //平均flush时间（纳秒）
	private int FsyncNanosNumOps;                               //fsync总数
	private float FsyncNanosAvgTime;                            //平均fsync时间（纳秒）
	private int SendDataPacketBlockedOnNetworkNanosNumOps;      //发送数据包的总数
	private float SendDataPacketBlockedOnNetworkNanosAvgTime;   //发送数据包平均等待时间（纳秒）
	private int SendDataPacketTransferNanosNumOps;              //发送数据包的总数
	private float SendDataPacketTransferNanosAvgTime;           //发送数据包平均传输时间（纳秒）
	public long getBytesWritten() {
		return BytesWritten;
	}
	public void setBytesWritten(long bytesWritten) {
		BytesWritten = bytesWritten;
	}
	public long getBytesRead() {
		return BytesRead;
	}
	public void setBytesRead(long bytesRead) {
		BytesRead = bytesRead;
	}
	public long getBlocksWritten() {
		return BlocksWritten;
	}
	public void setBlocksWritten(long blocksWritten) {
		BlocksWritten = blocksWritten;
	}
	public long getBlocksRead() {
		return BlocksRead;
	}
	public void setBlocksRead(long blocksRead) {
		BlocksRead = blocksRead;
	}
	public int getBlocksReplicated() {
		return BlocksReplicated;
	}
	public void setBlocksReplicated(int blocksReplicated) {
		BlocksReplicated = blocksReplicated;
	}
	public int getBlocksRemoved() {
		return BlocksRemoved;
	}
	public void setBlocksRemoved(int blocksRemoved) {
		BlocksRemoved = blocksRemoved;
	}
	public int getBlocksVerified() {
		return BlocksVerified;
	}
	public void setBlocksVerified(int blocksVerified) {
		BlocksVerified = blocksVerified;
	}
	public int getBlockVerificationFailures() {
		return BlockVerificationFailures;
	}
	public void setBlockVerificationFailures(int blockVerificationFailures) {
		BlockVerificationFailures = blockVerificationFailures;
	}
	public int getBlocksCached() {
		return BlocksCached;
	}
	public void setBlocksCached(int blocksCached) {
		BlocksCached = blocksCached;
	}
	public int getBlocksUncached() {
		return BlocksUncached;
	}
	public void setBlocksUncached(int blocksUncached) {
		BlocksUncached = blocksUncached;
	}
	public int getReadsFromLocalClient() {
		return ReadsFromLocalClient;
	}
	public void setReadsFromLocalClient(int readsFromLocalClient) {
		ReadsFromLocalClient = readsFromLocalClient;
	}
	public int getReadsFromRemoteClient() {
		return ReadsFromRemoteClient;
	}
	public void setReadsFromRemoteClient(int readsFromRemoteClient) {
		ReadsFromRemoteClient = readsFromRemoteClient;
	}
	public int getWritesFromLocalClient() {
		return WritesFromLocalClient;
	}
	public void setWritesFromLocalClient(int writesFromLocalClient) {
		WritesFromLocalClient = writesFromLocalClient;
	}
	public int getWritesFromRemoteClient() {
		return WritesFromRemoteClient;
	}
	public void setWritesFromRemoteClient(int writesFromRemoteClient) {
		WritesFromRemoteClient = writesFromRemoteClient;
	}
	public int getBlocksGetLocalPathInfo() {
		return BlocksGetLocalPathInfo;
	}
	public void setBlocksGetLocalPathInfo(int blocksGetLocalPathInfo) {
		BlocksGetLocalPathInfo = blocksGetLocalPathInfo;
	}
	public int getFsyncCount() {
		return FsyncCount;
	}
	public void setFsyncCount(int fsyncCount) {
		FsyncCount = fsyncCount;
	}
	public int getVolumeFailures() {
		return VolumeFailures;
	}
	public void setVolumeFailures(int volumeFailures) {
		VolumeFailures = volumeFailures;
	}
	public int getReadBlockOpNumOps() {
		return ReadBlockOpNumOps;
	}
	public void setReadBlockOpNumOps(int readBlockOpNumOps) {
		ReadBlockOpNumOps = readBlockOpNumOps;
	}
	public float getReadBlockOpAvgTime() {
		return ReadBlockOpAvgTime;
	}
	public void setReadBlockOpAvgTime(float readBlockOpAvgTime) {
		ReadBlockOpAvgTime = readBlockOpAvgTime;
	}
	public int getWriteBlockOpNumOps() {
		return WriteBlockOpNumOps;
	}
	public void setWriteBlockOpNumOps(int writeBlockOpNumOps) {
		WriteBlockOpNumOps = writeBlockOpNumOps;
	}
	public float getWriteBlockOpAvgTime() {
		return WriteBlockOpAvgTime;
	}
	public void setWriteBlockOpAvgTime(float writeBlockOpAvgTime) {
		WriteBlockOpAvgTime = writeBlockOpAvgTime;
	}
	public float getBlockChecksumOpAvgTime() {
		return BlockChecksumOpAvgTime;
	}
	public void setBlockChecksumOpAvgTime(float blockChecksumOpAvgTime) {
		BlockChecksumOpAvgTime = blockChecksumOpAvgTime;
	}
	public int getCopyBlockOpNumOps() {
		return CopyBlockOpNumOps;
	}
	public void setCopyBlockOpNumOps(int copyBlockOpNumOps) {
		CopyBlockOpNumOps = copyBlockOpNumOps;
	}
	public float getCopyBlockOpAvgTime() {
		return CopyBlockOpAvgTime;
	}
	public void setCopyBlockOpAvgTime(float copyBlockOpAvgTime) {
		CopyBlockOpAvgTime = copyBlockOpAvgTime;
	}
	public int getReplaceBlockOpNumOps() {
		return ReplaceBlockOpNumOps;
	}
	public void setReplaceBlockOpNumOps(int replaceBlockOpNumOps) {
		ReplaceBlockOpNumOps = replaceBlockOpNumOps;
	}
	public float getReplaceBlockOpAvgTime() {
		return ReplaceBlockOpAvgTime;
	}
	public void setReplaceBlockOpAvgTime(float replaceBlockOpAvgTime) {
		ReplaceBlockOpAvgTime = replaceBlockOpAvgTime;
	}
	public int getHeartbeatsNumOps() {
		return HeartbeatsNumOps;
	}
	public void setHeartbeatsNumOps(int heartbeatsNumOps) {
		HeartbeatsNumOps = heartbeatsNumOps;
	}
	public float getHeartbeatsAvgTime() {
		return HeartbeatsAvgTime;
	}
	public void setHeartbeatsAvgTime(float heartbeatsAvgTime) {
		HeartbeatsAvgTime = heartbeatsAvgTime;
	}
	public int getBlockReportsNumOps() {
		return BlockReportsNumOps;
	}
	public void setBlockReportsNumOps(int blockReportsNumOps) {
		BlockReportsNumOps = blockReportsNumOps;
	}
	public float getBlockReportsAvgTime() {
		return BlockReportsAvgTime;
	}
	public void setBlockReportsAvgTime(float blockReportsAvgTime) {
		BlockReportsAvgTime = blockReportsAvgTime;
	}
	public int getCacheReportsNumOps() {
		return CacheReportsNumOps;
	}
	public void setCacheReportsNumOps(int cacheReportsNumOps) {
		CacheReportsNumOps = cacheReportsNumOps;
	}
	public float getCacheReportsAvgTime() {
		return CacheReportsAvgTime;
	}
	public void setCacheReportsAvgTime(float cacheReportsAvgTime) {
		CacheReportsAvgTime = cacheReportsAvgTime;
	}
	public int getPacketAckRoundTripTimeNanosNumOps() {
		return PacketAckRoundTripTimeNanosNumOps;
	}
	public void setPacketAckRoundTripTimeNanosNumOps(
			int packetAckRoundTripTimeNanosNumOps) {
		PacketAckRoundTripTimeNanosNumOps = packetAckRoundTripTimeNanosNumOps;
	}
	public float getPacketAckRoundTripTimeNanosAvgTime() {
		return PacketAckRoundTripTimeNanosAvgTime;
	}
	public void setPacketAckRoundTripTimeNanosAvgTime(
			float packetAckRoundTripTimeNanosAvgTime) {
		PacketAckRoundTripTimeNanosAvgTime = packetAckRoundTripTimeNanosAvgTime;
	}
	public int getFlushNanosNumOps() {
		return FlushNanosNumOps;
	}
	public void setFlushNanosNumOps(int flushNanosNumOps) {
		FlushNanosNumOps = flushNanosNumOps;
	}
	public float getFlushNanosAvgTime() {
		return FlushNanosAvgTime;
	}
	public void setFlushNanosAvgTime(float flushNanosAvgTime) {
		FlushNanosAvgTime = flushNanosAvgTime;
	}
	public int getFsyncNanosNumOps() {
		return FsyncNanosNumOps;
	}
	public void setFsyncNanosNumOps(int fsyncNanosNumOps) {
		FsyncNanosNumOps = fsyncNanosNumOps;
	}
	public float getFsyncNanosAvgTime() {
		return FsyncNanosAvgTime;
	}
	public void setFsyncNanosAvgTime(float fsyncNanosAvgTime) {
		FsyncNanosAvgTime = fsyncNanosAvgTime;
	}
	public int getSendDataPacketBlockedOnNetworkNanosNumOps() {
		return SendDataPacketBlockedOnNetworkNanosNumOps;
	}
	public void setSendDataPacketBlockedOnNetworkNanosNumOps(
			int sendDataPacketBlockedOnNetworkNanosNumOps) {
		SendDataPacketBlockedOnNetworkNanosNumOps = sendDataPacketBlockedOnNetworkNanosNumOps;
	}
	public float getSendDataPacketBlockedOnNetworkNanosAvgTime() {
		return SendDataPacketBlockedOnNetworkNanosAvgTime;
	}
	public void setSendDataPacketBlockedOnNetworkNanosAvgTime(
			float sendDataPacketBlockedOnNetworkNanosAvgTime) {
		SendDataPacketBlockedOnNetworkNanosAvgTime = sendDataPacketBlockedOnNetworkNanosAvgTime;
	}
	public int getSendDataPacketTransferNanosNumOps() {
		return SendDataPacketTransferNanosNumOps;
	}
	public void setSendDataPacketTransferNanosNumOps(
			int sendDataPacketTransferNanosNumOps) {
		SendDataPacketTransferNanosNumOps = sendDataPacketTransferNanosNumOps;
	}
	public float getSendDataPacketTransferNanosAvgTime() {
		return SendDataPacketTransferNanosAvgTime;
	}
	public void setSendDataPacketTransferNanosAvgTime(
			float sendDataPacketTransferNanosAvgTime) {
		SendDataPacketTransferNanosAvgTime = sendDataPacketTransferNanosAvgTime;
	}
}
