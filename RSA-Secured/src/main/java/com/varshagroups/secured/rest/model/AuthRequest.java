package com.varshagroups.secured.rest.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@XmlRootElement(name = "AuthRequest")
@XmlType(propOrder = {"login", "password", "_2facode"})
@JsonPropertyOrder({"login", "password", "_2facode"})
public class AuthRequest {

	private String login;
	private String password;
	private String _2facode;
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String get_2facode() {
		return _2facode;
	}
	public void set_2facode(String _2facode) {
		this._2facode = _2facode;
	}
}
