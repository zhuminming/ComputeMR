package com.common.model;

public class NameNodeInfo {
	private int CreateFileOps;	                           //Total number of files created
	private String FilesCreated;	                       //Total number of files and directories created by create or mkdir operations
	private String FilesAppended;	                       //Total number of files appended
	private String GetBlockLocations;	                   //Total number of getBlockLocations operations
	private String FilesRenamed;	                       //Total number of rename operations (NOT number of files/dirs renamed)
	private String GetListingOps;	                       //Total number of directory listing operations
	private String DeleteFileOps;	                       //Total number of delete operations
	private String FilesDeleted;	                       //Total number of files and directories deleted by delete or rename operations
	private String FileInfoOps;	                           //Total number of getFileInfo and getLinkFileInfo operations
	private String AddBlockOps;	                           //Total number of addBlock operations succeeded
	private String GetAdditionalDatanodeOps;	           //Total number of getAdditionalDatanode operations
	private String CreateSymlinkOps;	                   //Total number of createSymlink operations
	private String GetLinkTargetOps;	                   //Total number of getLinkTarget operations
	private String FilesInGetListingOps;	               //Total number of files and directories listed by directory listing operations
	private String AllowSnapshotOps;	                   //Total number of allowSnapshot operations
	private String DisallowSnapshotOps;	                   //Total number of disallowSnapshot operations
	private String CreateSnapshotOps;	                   //Total number of createSnapshot operations
	private String DeleteSnapshotOps;	                   //Total number of deleteSnapshot operations
	private String RenameSnapshotOps;	                   //Total number of renameSnapshot operations
	private String ListSnapshottableDirOps;	               //Total number of snapshottableDirectoryStatus operations
	private String SnapshotDiffReportOps;	               //Total number of getSnapshotDiffReport operations
	private String TransactionsNumOps;	                   //Total number of Journal transactions
	private String TransactionsAvgTime;	                   //Average time of Journal transactions in milliseconds
	private String SyncsNumOps;	                           //Total number of Journal syncs
	private String SyncsAvgTime;	                       //Average time of Journal syncs in milliseconds
	private String TransactionsBatchedInSync;	           //Total number of Journal transactions batched in sync
	private String BlockReportNumOps;	                   //Total number of processing block reports from DataNode
	private String BlockReportAvgTime;	                   //Average time of processing block reports in milliseconds
	private String CacheReportNumOps;	                   //Total number of processing cache reports from DataNode
	private String CacheReportAvgTime;	                   //Average time of processing cache reports in milliseconds
	private String SafeModeTime;	                       //The interval between FSNameSystem starts and the last time safemode leaves in milliseconds.
	private String FsImageLoadTime;	                       //Time loading FS Image at startup in milliseconds
	private String GetEditNumOps;	                       //Total number of edits downloads from SecondaryNameNode
	private String GetEditAvgTime;	                       //Average edits download time in milliseconds
	private String GetImageNumOps;	                       //Total number of fsimage downloads from SecondaryNameNode
	private String GetImageAvgTime;	                       //Average fsimage download time in milliseconds
	private String PutImageNumOps;	                       //Total number of fsimage uploads to SecondaryNameNode
	private long PutImageAvgTime;	                       //Average fsimage upload time in milliseconds
	

}
