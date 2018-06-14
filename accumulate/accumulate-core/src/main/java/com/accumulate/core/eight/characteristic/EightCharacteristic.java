package com.accumulate.core.eight.characteristic;

import java.util.Date;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 函数式接口
 */
public class EightCharacteristic {

	public static void main(String[] args) {
		// Consumer<T>:T作为输入，执行某种动作但没有返回值
		Consumer<String> con = (str) -> System.out.println(str);
		con.accept("我是消费型接口!");

		// Supplier<T>:没有任何输入，返回T
		Supplier<Date> supp = () -> new Date();
		Date date = supp.get();
		System.out.println("当前时间:" + date);

		// Function<T, R>:T作为输入，返回的R作为输出
		Function<String, String> fun = (str) -> "hello " + str;
		String str = fun.apply("jobs");
		System.out.println(str);

		// Predicate<T>:T作为输入，返回的boolean值作为输出
		Predicate<Integer> pre = (num) -> num > 0;
		boolean flag = pre.test(10);
		System.out.println(flag);

		// BinaryOperator<T> -两个T作为输入，返回一个T作为输出，对于“reduce”操作很有用
		BinaryOperator<String> bina = (x, y) -> {
			System.out.print(x + " " + y);
			return "BinaryOperator";
		};
		System.out.println("  " + bina.apply("hello ", "world"));
	}
}
