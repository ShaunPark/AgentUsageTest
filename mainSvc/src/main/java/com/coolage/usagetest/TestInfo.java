package com.coolage.usagetest;

public class TestInfo {
	public int subcall;
	public int sqls;
	public int logMessages;
	public String sqlType;
	public boolean errInSubcall;
	public String targetHost;
//	public int targetPort;
//	public String targetUrl;
	
	public int getSubcall() {
		return subcall;
	}
	public void setSubcall(int subcall) {
		this.subcall = subcall;
	}
	public int getSqls() {
		return sqls;
	}
	public void setSqls(int sqls) {
		this.sqls = sqls;
	}
	public int getLogMessages() {
		return logMessages;
	}
	public void setLogMessages(int logMessages) {
		this.logMessages = logMessages;
	}
	public String getSqlType() {
		return sqlType;
	}
	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}
	public boolean isErrInSubcall() {
		return errInSubcall;
	}
	public void setErrInSubcall(boolean errInSubcall) {
		this.errInSubcall = errInSubcall;
	}
	public String getTargetHost() {
		return targetHost;
	}
	public void setTargetHost(String targetHost) {
		this.targetHost = targetHost;
	}
//	public int getTargetPort() {
//		return targetPort;
//	}
//	public void setTargetPort(int targetPort) {
//		this.targetPort = targetPort;
//	}
//	public String getTargetUrl() {
//		return targetUrl;
//	}
//	public void setTargetUrl(String targetUrl) {
//		this.targetUrl = targetUrl;
//	}
}
