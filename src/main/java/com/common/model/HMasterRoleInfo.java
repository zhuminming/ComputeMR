package com.common.model;

public class HMasterRoleInfo {
	private Long numRegionServers;
	private Long numDeadRegionServers;
	private Long clusterRequests;
	private Long ritCount;
	private Long ritCountOverThreshold;
	private Long ritOldestAge;
	
	public Long getNumRegionServers() {
		return numRegionServers;
	}
	public void setNumRegionServers(Long numRegionServers) {
		this.numRegionServers = numRegionServers;
	}
	public Long getNumDeadRegionServers() {
		return numDeadRegionServers;
	}
	public void setNumDeadRegionServers(Long numDeadRegionServers) {
		this.numDeadRegionServers = numDeadRegionServers;
	}
	public Long getClusterRequests() {
		return clusterRequests;
	}
	public void setClusterRequests(Long clusterRequests) {
		this.clusterRequests = clusterRequests;
	}
	public Long getRitCount() {
		return ritCount;
	}
	public void setRitCount(Long ritCount) {
		this.ritCount = ritCount;
	}
	public Long getRitCountOverThreshold() {
		return ritCountOverThreshold;
	}
	public void setRitCountOverThreshold(Long ritCountOverThreshold) {
		this.ritCountOverThreshold = ritCountOverThreshold;
	}
	public Long getRitOldestAge() {
		return ritOldestAge;
	}
	public void setRitOldestAge(Long ritOldestAge) {
		this.ritOldestAge = ritOldestAge;
	}
}
