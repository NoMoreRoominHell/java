package com.accumulate.core.design.patterns.decorator;

/**
 * MySubject类就是我们的主对象，Observer1和Observer2是依赖于MySubject的对象，当
 * MySubject变化时，Observer1和Observer2必然变化。AbstractSubject类中定义着需要监控的对象列表，可以对其进
 * 行修改：增加或删除被监控对象，且当MySubject变化时，负责通知在列表内存在的对象。
 */
public class MySubject extends AbstractSubject {

	@Override
	public void operation() {
		System.out.println("update self!");
		notifyObservers();
	}

}
