package com.lti.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.lti.dao.CustomerDaoImpl;

@Entity
@Table(name = "table_account")

public class Account {

	@Id
	@SequenceGenerator(name = "acc_seq", initialValue = 100001, allocationSize = 1)
	@GeneratedValue(generator = "acc_seq", strategy = GenerationType.SEQUENCE)
	@Column(name = "account_number")
	private int accountNumber;

//	private String transactionPassword;

	@Enumerated(EnumType.STRING)
	@Column(name = "account_type")
	AccountType accountType;
	
	@Column(name="account_status")
	private AccountStatus accountStatus;

	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@OneToMany(mappedBy = "account")
	List<Transaction> transactions;

	@OneToMany(mappedBy = "beneficiaryAccount")
	List<Beneficiary> beneficiaries;

	@Column(name = "balance")
	private double balance;

//	public String getTransactionPassword() {
//		return transactionPassword;
//	}
//
//	public void setTransactionPassword(String transactionPassword) {
//		this.transactionPassword = transactionPassword;
//	}
	
	

	public List<Beneficiary> getBeneficiaries() {
		return beneficiaries;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public void setBeneficiaries(List<Beneficiary> beneficiaries) {
		this.beneficiaries = beneficiaries;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public AccountType getType() {
		return accountType;
	}

	public void setType(AccountType accountType) {
		this.accountType = accountType;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}