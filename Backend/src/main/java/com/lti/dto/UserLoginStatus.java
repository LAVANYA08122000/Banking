package com.lti.dto;

public class UserLoginStatus extends Status{
	
	private int userId;
	private String accountNumber;
	private  int custId;
	
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String userName) {
		this.accountNumber = userName;
	}
	
	

}