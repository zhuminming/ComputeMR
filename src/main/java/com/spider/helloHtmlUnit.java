package com.spider;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class helloHtmlUnit {
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		String str;
	     //创建一个webclient
	     WebClient webClient = new WebClient();
	     //htmlunit 对css和javascript的支持不好，所以请关闭之
	     webClient.getOptions().setJavaScriptEnabled(false);
	     webClient.getOptions().setCssEnabled(false);
	     //获取页面
	     HtmlPage page = webClient.getPage("http://www.jd.com/allSort.aspx");
	     //获取页面的TITLE
	     str = page.getTitleText();
	     System.out.println(str);
	     //获取页面的XML代码
	     str = page.asXml();
	     System.out.println(str);
	     //获取页面的文本
	     str = page.asText();
	     System.out.println(str);
	     //关闭webclient
	     webClient.closeAllWindows();
	}
}
