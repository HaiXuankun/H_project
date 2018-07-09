package com.myspring.utils;

import java.lang.reflect.Method;

public class BeanUtils {
	public static Method getSetterMethod(Object obj,String name) {
		Method method = null;
		//构造set方法名
		name = "set" + name.substring(0,1).toUpperCase()+name.substring(1);
//		System.out.println("Object:" + obj.toString());
//		System.out.println("name:" + name);
		
		Method[] methods = obj.getClass().getMethods();
		for(int i = 0;i < methods.length;i++) {
			Method m= methods[i];
//			System.out.println("m.getName:" + m.getName());
			if(m.getName().equals(name)) {
				try {
					method = obj.getClass().getMethod(name, m.getParameterTypes());
//					System.out.println("method:" + method);
					break;
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
		}
		return method;
	}
}
