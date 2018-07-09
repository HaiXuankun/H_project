package com.myspring.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.myspring.config.Bean;
import com.myspring.config.Property;
import com.myspring.config.xmlConfig;
import com.myspring.utils.BeanUtils;

public class ClassPathXmlApplicationContext implements BeanFactory {
	private Map<String,Object> ioc;
	private Map<String,Bean> config;
	
	//构造函数，初始化IOC容器，加载配置文件，生成bean对象放入IOC容器
	public ClassPathXmlApplicationContext(String xmlPath) {
		ioc = new HashMap<String,Object>();
		config = xmlConfig.getConfig(xmlPath);
		
		if(config != null) {
			for(Entry<String, Bean> entry : config.entrySet()) {
				String beanId = entry.getKey();
//				System.out.println("beanId:" + beanId);
				Bean bean = entry.getValue();
//				System.out.println("bean:" + bean.getProperties());
				
				//根据Bean生成相应的对象
				Object obj = createBean(bean);
				ioc.put(beanId, obj);
//				System.out.println("Size:" + ioc.size());
			}
		}
	}
	
	
	private Object createBean(Bean bean) {
		@SuppressWarnings("unused")
		String beanId = bean.getId();
		String className = bean.getClassName();
		
		@SuppressWarnings("rawtypes")
		Class c = null;
		Object object = null;
		
		try {
			c = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("配置文件中class属性不正确:" + className);
		}
		
		try {
			object = c.newInstance();
//			System.out.println("=====" + object.toString());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException("该类缺少一个无参构造方法:" + className);
		}
		
		if(bean.getProperties() != null) {
			for(Property pro : bean.getProperties()) {
				//情况1:配置文件中使用value属性注入
				if(pro.getValue() != null) {
					//获取属性对象的set方法
					Method getMethod = BeanUtils.getSetterMethod(object, pro.getName());
					try {
						//调用setter方法注入
						getMethod.invoke(object, pro.getValue());
					} catch (Exception e) {
						throw new RuntimeException("属性名称不合法或者没有相应的getter方法:" + pro.getName());
					}
				}
				//情况二:配置文件中使用的是ref属性注入
				if(pro.getRef() != null) {
					//获取属性对应的setter方法
					Method getMethod = BeanUtils.getSetterMethod(object, pro.getName());
					//从容器中找到依赖对象
					Object obj = ioc.get(pro.getRef());
					if(obj == null) {
						throw new RuntimeException("没有找到依赖对象:" + pro.getRef());
					} else {
						//调用setter方法注入
						try {
							getMethod.invoke(object,obj);
						} catch (Exception e) {
							throw new RuntimeException("属性名称不合法或者没有相应的getter方法:" + pro.getName());
						}
					}
				}
			}
		}
		return object;
	}
	
	@Override
	public Object getBean(String beanName) {
		return ioc.get(beanName);
	}

}
