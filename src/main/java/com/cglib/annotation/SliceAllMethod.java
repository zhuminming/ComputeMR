package com.cglib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cglib.aspect.Aspect;


/**
 * 对所有方法插入切面
 * 
 * @author suntao
 *
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SliceAllMethod {
	/**
	 * 指定的切面
	 * 
	 * @return
	 * 
	 * @return
	 */
	Class<? extends Aspect> targetAspectClass();
}
