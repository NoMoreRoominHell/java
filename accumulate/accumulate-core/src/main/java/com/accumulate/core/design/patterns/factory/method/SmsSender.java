package com.accumulate.core.design.patterns.factory.method;

public class SmsSender implements Sender {

	@Override
	public void Send() {
		System.out.println("this is sms sender!");
	}

}