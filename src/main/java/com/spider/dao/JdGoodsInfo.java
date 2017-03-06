package com.spider.dao;

public class JdGoodsInfo {
	private int    good_id;         //商品Id
	private String good_name;       //商品名字
	private float  good_price;      //商品价格
	private int    good_buy;        //商品购买数量
	private String good_provider;   //商品供应商
	private String good_type;       //商品类别
	private boolean IS_JD;          //是否是京东自营
	public int getGood_id() {
		return good_id;
	}
	public void setGood_id(int good_id) {
		this.good_id = good_id;
	}
	public String getGood_name() {
		return good_name;
	}
	public void setGood_name(String good_name) {
		this.good_name = good_name;
	}
	public float getGood_price() {
		return good_price;
	}
	public void setGood_price(float good_price) {
		this.good_price = good_price;
	}
	public int getGood_buy() {
		return good_buy;
	}
	public void setGood_buy(int good_buy) {
		this.good_buy = good_buy;
	}
	public String getGood_provider() {
		return good_provider;
	}
	public void setGood_provider(String good_provider) {
		this.good_provider = good_provider;
	}
	public String getGood_type() {
		return good_type;
	}
	public void setGood_type(String good_type) {
		this.good_type = good_type;
	}
	public boolean isIS_JD() {
		return IS_JD;
	}
	public void setIS_JD(boolean iS_JD) {
		IS_JD = iS_JD;
	}

}
