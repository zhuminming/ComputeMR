package com.aop.test;

import java.util.Date;

import com.cglib.ProxyFactory;
import com.cglib.annotation.SliceTheMethod;
import com.cglib.aspect.Aspect;


public class aoptest {
	public static void main(String[] args) {
		HelloKitty h = ProxyFactory.getProxy(HelloKitty.class);
		h.sayHello("it can get param");
		System.out.println(h.getTime());
		HelloKitty h2 = ProxyFactory.getProxy(HelloKitty.class);
		h2.sayHello("it can create new instance");
		System.out.println(h2.getTime());

	}
}

class HelloKitty {

	int number = 0;

//	@SliceTheMethod(targetAspectClass = Aspect.class)
	public void sayHello(String str) {
		System.out.println(str);
		System.out.println(number);
		number++;
		return;
	}

//	@SliceTheMethod(targetAspectClass = Aspect.class)
	public String getTime() {
		System.out.println("no thing happend");
		String date = new Date().toGMTString() + "\t it can return obejct";
		System.out.println(number);
		return date;
	}
}