package com.spider.parse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.collect.Lists;
import com.spider.DownLoadHtml;
import com.spider.dao.JdGoodsInfo;

public class ParseJdHtml implements HtmlParseInterface{

	@Override
	public List<JdGoodsInfo> ParseHtml(HtmlPage page) {
		List<String> hrefList=getHrefAddress(page);
		for(String href : hrefList){
			if(href.contains("item.jd.com")){
				getItemInfo(href);
			}else if(href.contains("list.jd.com")){
				handleNextPage(href);
			}
		}
        return null;
	}
	
	public void getItemInfo(String href){
		HtmlPage page=DownLoadHtml.downloadFile(href);
		Document doc = Jsoup.parse(page.asXml());
		Elements elements1 = doc.select("div[class=crumb-wrap]");
		JdGoodsInfo info =new JdGoodsInfo();
		for(Element element : elements1){
			String good_provider=element.select("div[class=name]").select("a").attr("title");
			info.setGood_provider(good_provider);
			if(element.select("em[class=u-jd]").select("spad").text().equals("JD")){
				info.setIS_JD(true);
			}else{
				info.setIS_JD(false);
			}
		}
		Elements elements2 = doc.select("div[class=product-intro clearfix]");
		for(Element element : elements2){
			String good_id = element.select("div[class=left-btns]").select("a").attr("data-id");
			String good_name= element.select("div[class=sku-name]").text();
			info.setGood_id(Integer.parseInt(good_id));
			info.setGood_name(good_name);
		}
	}
	
	public void handleNextPage(String href){
		ParseHtml(DownLoadHtml.downloadFile(href));
	}
	
	public List<String> getHrefAddress(HtmlPage page){
		List<String> lists = Lists.newArrayList();
		List<HtmlAnchor> achList=page.getAnchors();
		for(HtmlAnchor anchor : achList){
			lists.add(anchor.getHrefAttribute());
		}
		return lists;
	}
	

}
