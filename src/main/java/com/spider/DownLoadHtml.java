package com.spider;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DownLoadHtml {
	 /* 下载 url 指向的网页 */  
    public static String downloadFile(String url) {
    	String entity =null;
        try { 
	         //创建一个webclient
	   	     WebClient webClient = new WebClient();
	   	     //htmlunit 对css和javascript的支持不好，所以请关闭之
	   	     webClient.getOptions().setJavaScriptEnabled(false);
	   	     webClient.getOptions().setCssEnabled(false);
	   	     //获取页面
	   	     HtmlPage page = webClient.getPage(url);
	   	     entity=page.asXml();
        }catch(Exception e){
        	e.printStackTrace();
        }
		return entity;
    }

}
