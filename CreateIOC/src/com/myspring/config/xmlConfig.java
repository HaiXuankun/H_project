package com.myspring.config;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 读取xml文件的类
 * @author HunagXiaoKun
 * */

public class xmlConfig{
	//读取xml文件
	public static Map<String, Bean> getConfig(String xmlPath){
		System.out.println("xmlpath:" + xmlPath);
		
		Map<String, Bean> configMap = new HashMap<>();
		Document doc = null;
		SAXReader reader = new SAXReader(); 
		InputStream in = xmlConfig.class.getResourceAsStream(xmlPath);
		
		System.out.println("xml输入流存在-->in:" + in);
		
		try {
			doc = reader.read(in);
			System.out.println("doc: \n" + doc.asXML());
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		//定义xpath，取出所有的bean
		String xpath = "//bean";
		//对bean进行遍历
		@SuppressWarnings("unchecked")
		List<Element> eleList = doc.selectNodes(xpath);
//		System.out.println("eleList" + eleList);
		if(eleList != null) {
			for(Element beanEle : eleList) {
				Bean bean = new Bean();
				//Bean节点的id
				String id = beanEle.attributeValue("id");
//				System.out.println("bean-->id:" + id);
				//Bean节点的class属性
				String className = beanEle.attributeValue("class");
//				System.out.println("bean-->classNme:" + className);
				
				bean.setId(id);
				bean.setClassName(className);
				
				//获取bean节点下的所有property节点
				@SuppressWarnings("unchecked")
				List<Element> proList = beanEle.elements("property");
				if(proList != null) {
					for(Element pro : proList) {
						Property property = new Property();
						
						String proName = pro.attributeValue("name");
//						System.out.println("bean-->property-->name:" + proName);
						
						String proValue = pro.attributeValue("value");
//						System.out.println("bean-->property-->value:" + proValue);
						
						String proRef = pro.attributeValue("ref");
//						System.out.println("bean-->property-->ref:" + proRef);
						
						//封装到Property属性中
						property.setName(proName);
						property.setValue(proValue);
						property.setRef(proRef);
						
						bean.getProperties().add(property);
					}
				}
				
				//id应该是不重复的
				if(configMap.containsKey(id)) {
					throw new RuntimeException("bean的ID重复" + id);
				}
				//将bean封装到map中
				configMap.put(id, bean);
			}
		}
		
		return configMap;
	}
}
