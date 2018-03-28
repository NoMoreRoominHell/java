package com.accumulate.core.design.patterns;

/**
 * 核心思想就是：当对象的状态改变时，同时改变其行为
 * 
 * 状态类的核心类
 */
public class State {

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void method1() {
		System.out.println("execute the first opt!");
	}

	public void method2() {
		System.out.println("execute the second opt!");
	}
}
