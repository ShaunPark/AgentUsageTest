package com.coolage.usagetest.job;

public abstract class Job {
	protected int jobIdx;
	protected String url;
	protected String message;
	public abstract int execute();

	
	public int getJobIdx() {
		return jobIdx;
	}
	public void setJobIdx(int jobIdx) {
		this.jobIdx = jobIdx;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
