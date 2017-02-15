package com.cglib.proxy;


public interface ProxyGeneraterInterface {
	/**
	 * 获取代理
	 * 
	 * @param clazz
	 * @return
	 */
	<T> T getProxy(Class<T> clazz);

}
