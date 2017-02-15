package com.cglib.aspect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

/**
 * 切面
 * 
 * @author suntao
 *
 */
public class Aspect implements AspectInterface {
	/**
	 * 被代理 方法 的序号
	 */
	public static int ORIGINALORDER = 0;
	/**
	 * 切面的Chip
	 */
	private List<AspectChip> chips;

	public Aspect() {
		chips = new ArrayList<AspectChip>();
		generateDefaultMethodChip();
	}
	
	
	public Object aspectRun(Object obj, Method method, Object[] args,
			MethodProxy proxy){
		Object result = null;

		for (AspectChip chip : chips) {
			try {
				if (chip.order == 0) {
					result = chip.run(obj, method, args, proxy);
				} else {
					chip.run(obj, method, args, proxy);
				}
			} catch (Exception exception) {
				exceptionProcess(obj, method, args, proxy, exception);
			}
		}
		return result;
	}
	
	public Object doSlicedMethod(Object obj, Method method, Object[] args,
			MethodProxy proxy){
		Object result = null;
		try {
			result = proxy.invokeSuper(obj, args);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void exceptionProcess(Object obj, Method method, Object[] args,
			MethodProxy proxy, Exception exception){
		exception.printStackTrace();
	}
	
	/**
	 * Use to generate <br>
	 * before() doSlicedMethod() after() Three chips<br>
	 * Will be giving order number is -10 0 10
	 * <p>
	 * 将默认的三个方法转化为AspectChip<br>
	 * before doslicedmethod 和 after方法<br>
	 * 将分别给与 -10 0 10的执行顺序
	 * 
	 * @return
	 */
	private void generateDefaultMethodChip() {
		
		addChip(new AspectChip(ORIGINALORDER) {

			@Override
			Object run(Object obj, Method method, Object[] args,
					MethodProxy proxy) {
				return doSlicedMethod(obj, method, args, proxy);
			}
		});
	
		return;

	}
	
	public Boolean addChip(AspectChip chip) {

		Boolean result = false;
		try {
			checkOrderExist(chip);
			chips.add(chip);
			result = true;
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}
	
	private Boolean checkOrderExist(AspectChip chip) throws Exception {
		Boolean result = true;
		for (AspectChip c : chips) {
			if (c.order == chip.order) {
				result = false;
				break;
			}
		}
		return result;
	}
}
