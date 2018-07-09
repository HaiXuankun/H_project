package com.myspring.config;
/**
 * 封装配置文件中的property节点
 * @author HuangXiaoKun
 * */

public class Property {
	/**
	 * name属性 
	 * */
	private String name;
	private String value;
	private String ref;
	
	public Property() {
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
}
