package com.common.model;
public class FSNamesystemState {
	protected enum element{
	    NumLiveDataNodes,
	    NumDeadDataNodes,
	    NumDecomLiveDataNodes,
	    NumDecomDeadDataNodes,
	    NumDecommissioningDataNodes,
	    NumStaleDataNodes;
	}
	private String NumLiveDataNodes;             //正常节点个数
	private String NumDeadDataNodes;             //崩溃节点个数
	private String NumDecomLiveDataNodes;
	private String NumDecomDeadDataNodes;
	private String NumDecommissioningDataNodes;
	private String NumStaleDataNodes;
}
