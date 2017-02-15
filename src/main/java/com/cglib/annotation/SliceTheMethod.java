package com.cglib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cglib.aspect.Aspect;


/**
 * 对这个方法展开切面
 * 
 * @author suntao
 *
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SliceTheMethod {
	/**
	 * 指定的切面
	 * 
	 * @return
	 */
	Class<? extends Aspect> targetAspectClass();
}
