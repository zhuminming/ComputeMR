package com.spider.parse;

import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.spider.dao.JdGoodsInfo;

public interface HtmlParseInterface {
	public List<JdGoodsInfo> ParseHtml (HtmlPage page);
	

}
