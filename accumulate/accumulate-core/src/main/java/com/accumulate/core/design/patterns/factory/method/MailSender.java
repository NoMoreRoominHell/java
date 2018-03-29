package com.accumulate.core.design.patterns.factory.method;

public class MailSender implements Sender {

	@Override
	public void Send() {
		System.out.println("this is mailsender!");
	}
}