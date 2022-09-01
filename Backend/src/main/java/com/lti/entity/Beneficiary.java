package com.lti.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "table_beneficiary")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "beneficiaryAccount"})
public class Beneficiary {

	@Id
	@SequenceGenerator(name = "ben_seq", initialValue = 301, allocationSize = 1)
	@GeneratedValue(generator = "ben_seq", strategy = GenerationType.SEQUENCE)
	@Column(name = "beneficiary_id")
	private int beneficiaryId;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "beneficiary_account_number")
	private Account beneficiaryAccount;

//	@OneToOne(cascade = CascadeType.ALL)
	@Column(name = "customer_account_number")
	private int customerAccountNumber;

	@Column(name = "beneficiary_name")
	private String beneficiaryName;

	public int getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(int beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public Account getBeneficiaryAccount() {
		return beneficiaryAccount;
	}

	public void setBeneficiaryAccount(Account beneficiaryAccount) {
		this.beneficiaryAccount = beneficiaryAccount;
	}

	public int getCustomerAccountNumber() {
		return customerAccountNumber;
	}

	public void setCustomerAccountNumber(int customerAccountNumber) {
		this.customerAccountNumber = customerAccountNumber;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

}