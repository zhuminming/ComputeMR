package com.common.model;

public class OperatingSystem {
	public enum element{
		MaxFileDescriptorCount,
	    OpenFileDescriptorCount,
	    FreePhysicalMemorySize,
	    CommittedVirtualMemorySize,
	    FreeSwapSpaceSize,
	    ProcessCpuLoad,
	    ProcessCpuTime,
	    SystemCpuLoad,
	    TotalPhysicalMemorySize,
	    TotalSwapSpaceSize,
	    Name,
	    Version,
	    AvailableProcessors,
	    Arch,
	    SystemLoadAverage;
	}
	private int MaxFileDescriptorCount;        //最大文件数量
	private int OpenFileDescriptorCount;       //打开文件数量
	private long FreePhysicalMemorySize;       //服务器空闲物理内存大小
	private long CommittedVirtualMemorySize;   //服务器虚拟内存大小
	private long FreeSwapSpaceSize;            //服务器空闲Swap内存大小
	private float ProcessCpuLoad;              //进程cpu负载
	private long ProcessCpuTime;               //进程cpu使用时间
	private float SystemCpuLoad;               //系统cpu负载
	private long TotalPhysicalMemorySize;      //服务器物理内存大小
	private long TotalSwapSpaceSize;           //服务器Swap内存大小
	private String Name;                       //服务器系统名称
	private String Version;                    //服务器系统版本
	private int AvailableProcessors;           //服务器cpu核数
	private String Arch;                       //服务器处理器架构
	private float SystemLoadAverage;           //系统平均负载
	
	

	public int getMaxFileDescriptorCount() {
		return MaxFileDescriptorCount;
	}
	public void setMaxFileDescriptorCount(int maxFileDescriptorCount) {
		MaxFileDescriptorCount = maxFileDescriptorCount;
	}
	public int getOpenFileDescriptorCount() {
		return OpenFileDescriptorCount;
	}
	public void setOpenFileDescriptorCount(int openFileDescriptorCount) {
		OpenFileDescriptorCount = openFileDescriptorCount;
	}
	public long getFreePhysicalMemorySize() {
		return FreePhysicalMemorySize;
	}
	public void setFreePhysicalMemorySize(long freePhysicalMemorySize) {
		FreePhysicalMemorySize = freePhysicalMemorySize;
	}
	public long getCommittedVirtualMemorySize() {
		return CommittedVirtualMemorySize;
	}
	public void setCommittedVirtualMemorySize(long committedVirtualMemorySize) {
		CommittedVirtualMemorySize = committedVirtualMemorySize;
	}
	public long getFreeSwapSpaceSize() {
		return FreeSwapSpaceSize;
	}
	public void setFreeSwapSpaceSize(long freeSwapSpaceSize) {
		FreeSwapSpaceSize = freeSwapSpaceSize;
	}
	public float getProcessCpuLoad() {
		return ProcessCpuLoad;
	}
	public void setProcessCpuLoad(float processCpuLoad) {
		ProcessCpuLoad = processCpuLoad;
	}
	public long getProcessCpuTime() {
		return ProcessCpuTime;
	}
	public void setProcessCpuTime(long processCpuTime) {
		ProcessCpuTime = processCpuTime;
	}
	public float getSystemCpuLoad() {
		return SystemCpuLoad;
	}
	public void setSystemCpuLoad(float systemCpuLoad) {
		SystemCpuLoad = systemCpuLoad;
	}
	public long getTotalPhysicalMemorySize() {
		return TotalPhysicalMemorySize;
	}
	public void setTotalPhysicalMemorySize(long totalPhysicalMemorySize) {
		TotalPhysicalMemorySize = totalPhysicalMemorySize;
	}
	public long getTotalSwapSpaceSize() {
		return TotalSwapSpaceSize;
	}
	public void setTotalSwapSpaceSize(long totalSwapSpaceSize) {
		TotalSwapSpaceSize = totalSwapSpaceSize;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}
	public int getAvailableProcessors() {
		return AvailableProcessors;
	}
	public void setAvailableProcessors(int availableProcessors) {
		AvailableProcessors = availableProcessors;
	}
	public String getArch() {
		return Arch;
	}
	public void setArch(String arch) {
		Arch = arch;
	}
	public float getSystemLoadAverage() {
		return SystemLoadAverage;
	}
	public void setSystemLoadAverage(float systemLoadAverage) {
		SystemLoadAverage = systemLoadAverage;
	}
}
