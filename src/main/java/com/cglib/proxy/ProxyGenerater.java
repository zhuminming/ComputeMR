package com.cglib.proxy;


import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.proxy.Enhancer;

public class ProxyGenerater implements ProxyGeneraterInterface {
	private static Map<Class<?>, Interceptor> interceptorCache = new HashMap<Class<?>, Interceptor>();

	public ProxyGenerater() {
	}

	private Interceptor getInterceptorCache(Class<?> clazz) {
		Interceptor result = null;
		result = interceptorCache.get(clazz);
		if (result == null) {
			result = new Interceptor(clazz);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected <T> T getNewProxy(Class<T> clazz) {
		T result = null;
		result = (T) Enhancer.create(clazz, getInterceptorCache(clazz));
		return result;

	}

	public <T> T getProxy(Class<T> clazz) {
		T result = null;
		result = getNewProxy(clazz);
		return result;
	}

}
