package com.coolage.usagetest.job;

import com.coolage.usagetest.TestInfo;

public abstract class Job {
	protected int jobIdx;
	protected TestInfo info;

	protected String message;
	public abstract int execute();

	
	public int getJobIdx() {
		return jobIdx;
	}
	public void setJobIdx(int jobIdx) {
		this.jobIdx = jobIdx;
	}
	public TestInfo getInfo() {
		return info;
	}
	public void setInfo(TestInfo info) {
		this.info = info;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
