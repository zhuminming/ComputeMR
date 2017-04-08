package com.spider;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DownLoadHtml {
	 /* 下载 url 指向的网页 */  
    public static HtmlPage downloadFile(String url) {
    	HtmlPage page =null;
        try { 
        	if(!url.contains("http://")){
        		url="http:"+url;
        	}
        	System.out.println(url);
	         //创建一个webclient
	   	     WebClient webClient = new WebClient(BrowserVersion.CHROME);
	   	     //htmlunit 对css和javascript的支持不好，所以请关闭之4
	   	     webClient.getOptions().setJavaScriptEnabled(false);
	   	     webClient.getOptions().setCssEnabled(false);
	   	     //获取页面
	   	     page = webClient.getPage(url);
        }catch(Exception e){
        	e.printStackTrace();
        }
		return page;
    }

}
