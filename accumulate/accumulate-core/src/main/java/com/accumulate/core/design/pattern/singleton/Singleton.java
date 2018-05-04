package com.accumulate.core.design.pattern.singleton;

/**
 * 单例模式（Singleton Pattern） Ensure a class has only one instance, and provide a
 * global point of access to it.（确保某一个类 只有一个实例，而且自行实例化并向整个系统提供这个实例。）
 */
public class Singleton {
	private static final Singleton singleton = new Singleton();

	private Singleton() {
	}

	// 通过该方法获得实例对象
	public static Singleton getSingleton() {
		return singleton;
	}

	// 类中其他方法，尽量是static
	public static void doSomething() {
	}

}
