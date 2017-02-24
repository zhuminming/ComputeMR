package com.spider;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;

public class DownLoadHtml {
	 /* 下载 url 指向的网页 */  
    public String downloadFile(String url) {  
    	String entity = null;  
        /* 1.生成 HttpClinet 对象并设置参数 */  
        HttpClient httpClient = HttpClients.createDefault();  
  
        /* 2.生成 HttpGet 对象并设置参数 */  
        HttpGet getMethod = new HttpGet(url);  
  
        /* 3.执行 HTTP GET 请求 */  
        try { 
            HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1,HttpStatus.SC_OK, "OK");
            response = httpClient.execute(getMethod);  
            // 判断访问的状态码  
            if ( response.getStatusLine().getStatusCode()!= HttpStatus.SC_OK) {  
                System.err.println("Method failed: " + response.getStatusLine()); 
                return null;
            }  
            entity = EntityUtils.toString (response.getEntity(),"utf-8");
        }catch(Exception e){
        	e.printStackTrace();
        }
		return entity;
    }

}
