package com.lti.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "table_transaction")
public class Transaction {
	@Id
	@SequenceGenerator(name = "trans_seq", initialValue = 201, allocationSize = 1)
	@GeneratedValue(generator = "trans_seq", strategy = GenerationType.SEQUENCE)
	@Column(name = "transaction_Id")
	private int transactionId;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "account_number")
	private Account account;

	@Column(name = "tx_amount")
	private double amount;

	@Column(name = "transaction_mode")
	private String mode;

	@Column(name = "tx_type")
	TransactionType transactionType;

	@Column(name = "tx_date")
	private LocalDate transactionDate;
	

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public double getAmount() {
		return amount;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

}