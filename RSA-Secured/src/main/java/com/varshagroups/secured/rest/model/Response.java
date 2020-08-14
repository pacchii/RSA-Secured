package com.varshagroups.secured.rest.model;

public class Response {
	private String responseCode;
	private String responseMessage;
	private String _2fakey;
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public String get_2fakey() {
		return _2fakey;
	}
	public void set_2fakey(String _2fakey) {
		this._2fakey = _2fakey;
	}
	
	

}
