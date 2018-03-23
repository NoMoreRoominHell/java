package com.accumulate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

	@RequestMapping(value = "/test", produces = "application/json;charset=UTF-8")
	public String test() {
		return "test";
	}

}