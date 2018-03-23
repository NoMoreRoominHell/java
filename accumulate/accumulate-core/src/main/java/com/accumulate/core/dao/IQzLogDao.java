package com.accumulate.core.dao;

import java.util.List;

import com.accumulate.core.entity.QzLog;

public interface IQzLogDao {

	int insertQzLog(QzLog qzLog);

	List<QzLog> queryQzLog(QzLog qzLog);
}