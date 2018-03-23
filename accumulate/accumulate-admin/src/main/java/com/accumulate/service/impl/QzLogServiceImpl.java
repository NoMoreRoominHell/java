package com.accumulate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accumulate.core.dao.IQzLogDao;
import com.accumulate.core.entity.QzLog;
import com.accumulate.service.IQzLogService;

@Service
public class QzLogServiceImpl implements IQzLogService {

	@Autowired
	private IQzLogDao iQzLogDao;

	@Override
	public List<QzLog> queryQzlog() {
		return iQzLogDao.queryQzLog(null);
	}
}