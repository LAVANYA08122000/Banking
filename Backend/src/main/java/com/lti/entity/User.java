package com.lti.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "table_register")
public class User {

	@Id
	@SequenceGenerator(name = "user_seq", initialValue = 701, allocationSize = 1)
	@GeneratedValue(generator = "user_seq", strategy = GenerationType.SEQUENCE)
	@Column(name = "user_id")
	private int userId;

	@Column(name = "account_number")
	private int accountNumber;

	@Column(name = "login_password")
	private String loginPassword;

	@Column(name = "transaction_password")
	private String transactionPassword;

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getTransactionPassword() {
		return transactionPassword;
	}

	public void setTransactionPassword(String transactionPassword) {
		this.transactionPassword = transactionPassword;
	}

}
