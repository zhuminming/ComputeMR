package com.spider;

import java.util.List;

import com.spider.dao.JdGoodsInfo;
import com.spider.parse.ParseJdHtml;

public class Test {
	
    // main 方法入口  
    public static void main(String[] args) {
    	String url = "http://www.jd.com/allSort.aspx";
    	ParseJdHtml jdHtml = new ParseJdHtml();
    	List<JdGoodsInfo> lists=jdHtml.ParseHtml(DownLoadHtml.downloadFile(url));
    	
    }  
}
