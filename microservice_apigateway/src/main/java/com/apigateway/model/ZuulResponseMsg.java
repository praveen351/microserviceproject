package com.apigateway.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ZuulResponseMsg {
	private int statusCode;
	private String message;
	private String currentTime;
	
	public ZuulResponseMsg() {
		super();
	}
	
	public ZuulResponseMsg(int statusCode, String message) {
		super();
		this.currentTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
		this.statusCode = statusCode;
		this.message = message;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

	@Override
	public String toString() {
		return "ZuulResponseMsg [statusCode=" + statusCode + ", message=" + message + ", currentTime=" + currentTime
				+ "]";
	}
}
