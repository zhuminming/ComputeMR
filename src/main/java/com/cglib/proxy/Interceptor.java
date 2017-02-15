package com.cglib.proxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.cglib.annotation.SliceAllMethod;
import com.cglib.annotation.SliceTheMethod;
import com.cglib.aspect.Aspect;
import com.cglib.aspect.AspectInterface;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class Interceptor implements MethodInterceptor {
	private static Map<Class<Aspect>, AspectInterface> aspectCache = new HashMap<Class<Aspect>, AspectInterface>();
	private Map<Method, AspectInterface> methodsAspect;

	public Interceptor(Class<?> clazz) {
//		this.methodsAspect = new HashMap<Method, AspectInterface>();
//		this.scanAnno(clazz);
	}

	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
//		AspectInterface aspect = methodsAspect.get(method);
		Object result = null;
//		if (aspect != null) {
//			result = aspect.aspectRun(obj, method, args, proxy);
//		} else {
//			result = proxy.invokeSuper(obj, args);
//		}
		result = proxy.invokeSuper(obj, args);

		return result;
	}


	@SuppressWarnings("unchecked")
	private void scanAnno(Class<?> clazz) {
		Method[] allMethod = clazz.getDeclaredMethods();
		SliceAllMethod targetClassAnno = clazz.getAnnotation(SliceAllMethod.class);
		if (targetClassAnno != null) {
			AspectInterface aspect = checkCacheAndGetAspect((Class<Aspect>) targetClassAnno.targetAspectClass());
			for (Method aMethod : allMethod) {
				methodsAspect.put(aMethod, aspect);
			}
		} else {
			for (Method aMethod : allMethod) {
				SliceTheMethod targetMethodAnno = aMethod
						.getAnnotation(SliceTheMethod.class);
				if (targetMethodAnno != null) {
					AspectInterface aspect = checkCacheAndGetAspect((Class<Aspect>) targetMethodAnno
							.targetAspectClass());
					methodsAspect.put(aMethod, aspect);
				}
			}
		}
	}

	private AspectInterface checkCacheAndGetAspect(Class<Aspect> aspectClass) {
		AspectInterface result = aspectCache.get(aspectClass);
		if (result == null) {
			try {
				result = aspectClass.newInstance();
				aspectCache.put(aspectClass, result);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return result;

	}
}
