package com.spider.parse;

import java.util.List;

import com.spider.dao.JdGoodsInfo;

public interface HtmlParseInterface {
	public List<JdGoodsInfo> ParseHtml (String html);

}
