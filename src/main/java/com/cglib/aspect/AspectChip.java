package com.cglib.aspect;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

public abstract class AspectChip {

	Integer order;

	/**
	 * AspectChip means it is a chip of an aspect<br>
	 * When you construct a instance of this class you have to appoint a order
	 * number<br>
	 * And Aspect will exec all chips from small to large
	 * <p>
	 * AspectChip 意味着它是一个切面的小片<br>
	 * 在构造这个类的一个新的实例(或者创建一个匿名类)的时候,你必须为它指定一个顺序的数值<br>
	 * 一个切面会按照这个顺序值,从小到大的运行所有的chips
	 * 
	 * @param order
	 *            顺序值
	 */
	public AspectChip(Integer order) {
		this.order = order;
	}

	/**
	 * This is the runable method<br>
	 * The only thing you have to know is <br>
	 * although this method has an object return value <br>
	 * but you dont need it in most time
	 * <p>
	 * 这就是会被执行的方法<br>
	 * 你需要了解的是<br>
	 * 虽然这个方法有着一个Obejct返回值<br>
	 * 但是大多数时候它并不会(应该)被使用
	 * 
	 * @param obj
	 * @param method
	 * @param args
	 * @param proxy
	 * @param message
	 * @return
	 */
	abstract Object run(Object obj, Method method, Object[] args,
			MethodProxy proxy);

}
