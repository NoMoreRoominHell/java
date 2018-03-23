package com.accumulate.core.entity;

import java.util.Date;

/**
 * 定时任务日志表
 */
public class QzLog {

	private Long id;
	private String taskName;// 任务名称
	private Date startTime;// 开始时间
	private Date endTime;// 结束时间
	private Integer status;// 状态
	private String remark;// 备注
	private String logicDescribe;// 逻辑描述
	private String request;// 请求
	private String response;// 响应
	private Date createTime;// 创建时间
	private Long consume;// 耗时

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getConsume() {
		return consume;
	}

	public void setConsume(Long consume) {
		this.consume = consume;
	}

	public String getLogicDescribe() {
		return logicDescribe;
	}

	public void setLogicDescribe(String logicDescribe) {
		this.logicDescribe = logicDescribe;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
}