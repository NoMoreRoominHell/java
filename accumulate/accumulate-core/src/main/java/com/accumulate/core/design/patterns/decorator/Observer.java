package com.accumulate.core.design.patterns.decorator;

/**
 * 观察者模式
 * 
 * 当一个对象变化时，其它依赖该对象的对象都会收到通知，并且随着变化！ 对象之间是一种一对多的关系
 */
public interface Observer {
	public void update();
}
