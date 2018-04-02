package com.accumulate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.accumulate.core.entity.QzLog;
//import com.accumulate.service.IQzLogService;
import com.github.pagehelper.PageHelper;

@Controller
public class TestController {

//	@Autowired
//	private IQzLogService IQzLogService;

	@RequestMapping(value = "/test", produces = "application/json;charset=UTF-8")
	public String test() {
//		PageHelper.startPage(1, 10);
//		List<QzLog> list = IQzLogService.queryQzlog();
//		for (QzLog qzLog : list) {
//			System.out.println(qzLog.getTaskName());
//		}
		return "test";
	}

}