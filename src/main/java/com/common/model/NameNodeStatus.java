package com.common.model;

public class NameNodeStatus {
	public enum element{
		State,
	    NNRole,
	    HostAndPort,
	    SecurityEnabled;
	}
	private String state;
	private String NNRole;
	private String hostAndPort;
	private boolean securityEnabled;
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	
	public String getNNRole() {
		return NNRole;
	}
	public void setNNRole(String nNRole) {
		NNRole = nNRole;
	}
	public String getHostAndPort() {
		return hostAndPort;
	}
	public void setHostAndPort(String hostAndPort) {
		this.hostAndPort = hostAndPort;
	}
	public boolean isSecurityEnabled() {
		return securityEnabled;
	}
	public void setSecurityEnabled(boolean securityEnabled) {
		this.securityEnabled = securityEnabled;
	}

	
}
