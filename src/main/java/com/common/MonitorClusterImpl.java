package com.common;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class MonitorClusterImpl implements MonitorCluster{
	   protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
//	    private static final String server = "Hadoop:service=HBase,name=Master,sub=Server";
	    private static final String server = "Hadoop:*";

//	    private static final String assignment = "Hadoop:service=HBase,name=Master,sub=AssignmentManger";
	    
	    public String extractMonitorData() {
	        //TODO 通过调用API获得IP以及参数
	        HMasterRoleInfo monitorDataPoint = new HMasterRoleInfo();
	        String URL = "http://192.168.21.128:7103/jmx";
	        JSONObject serverJson = qryJSonObjectFromJMX(URL, server);
//	        JSONObject assignJson = qryJSonObjectFromJMX(URL, assignment);
	        try {
	            monitorDataPoint.setNumRegionServers(serverJson.getLong("numRegionServers"));
	            monitorDataPoint.setNumDeadRegionServers(serverJson.getLong("numDeadRegionServers"));
	            monitorDataPoint.setClusterRequests(serverJson.getLong("clusterRequests"));
//	            monitorDataPoint.setRitCount(assignJson.getLong("ritCount"));
//	            monitorDataPoint.setRitCountOverThreshold(assignJson.getLong("ritCountOverThreshold"));
//	            monitorDataPoint.setRitOldestAge(assignJson.getLong("ritOldestAge"));
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        return monitorDataPoint.toString();
	    }
	  
	    /**
	    * 通过jmx获取监控数据
	    *
	    * @param URL
	    * @param objectName
	    * @return
	    */
	    public static JSONObject qryJSonObjectFromJMX(String URL, String objectName) {
	        JSONObject jsonObject = null;
	        try {
	            StringBuilder sb = new StringBuilder(URL);
	            sb.append("?qry=");
	            sb.append(objectName);
	            HttpClient httpClient = new HttpClient();
	            GetMethod getMethod = new GetMethod(sb.toString());
	            int statusCode = httpClient.executeMethod(getMethod);
	            String jsonStr = new String(getMethod.getResponseBody());
	            System.out.println(jsonStr);
	            jsonObject = JSONObject.parseObject(jsonStr);
	        } catch (JSONException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return jsonObject;
	    }
	    
	    private class HMasterRoleInfo{
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
	    
	    public static void main(String[] args){
	        System.out.println(new MonitorClusterImpl().extractMonitorData());
	    }
}
