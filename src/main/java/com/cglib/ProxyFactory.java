package com.cglib;

import com.cglib.proxy.*;

/**
 * EasyAOP
 * 
 * @author suntao
 *
 */
public class ProxyFactory {
	private static ProxyGeneraterInterface proxyGenerater = new ProxyGenerater();

	/**
	 * 获取类代理
	 * <p>
	 * 如果方法/类有相应注解<br>
	 * 该方法/类的所有方法<br>
	 * 被调用时将会被切面处理
	 * 
	 * @param clazz
	 * @return class 对应的新的实例
	 */
	public static <T> T getProxy(Class<T> clazz) {
		return proxyGenerater.getProxy(clazz);
	}

}
