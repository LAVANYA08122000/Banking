package com.lti.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.lti.entity.Gender;

@Entity
@Table(name = "table_customer")

public class Customer {
	@Id
	@SequenceGenerator(name = "cust_seq", initialValue = 401, allocationSize = 1)
	@GeneratedValue(generator = "cust_seq", strategy = GenerationType.SEQUENCE)
	@Column(name = "customer_id")
	private int custId;

//	@Column(name = "login_password")
//	private String customerPassword;

	private Gender gender;

	@Column(name = "name")
	private String name;

	@Column(name = "mobile_no")
	private String mobileNo;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "aadhaar_card_no")
	private String aadhaarNo;

	@Column(name = "aadhar_file")
	private String aadhar;

	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	@Column(name = "pan_card_no")
	private String panCardNo;

	@Column(name = "pan_file")
	private String pan;

//	@Column(name = "account_status")
//	AccountStatus accountStatus;

	@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
	private Address address;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Account> account;

	@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
	private Income income;

	public String getAadhar() {
		return aadhar;
	}

	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public List<Account> getAccount() {
		return account;
	}

	public void setAccount(List<Account> account) {
		this.account = account;
	}

	public Income getIncome() {
		return income;
	}

	public void setIncome(Income income) {
		this.income = income;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getAadhaarNo() {
		return aadhaarNo;
	}

	public void setAadhaarNo(String aadhaarNo) {
		this.aadhaarNo = aadhaarNo;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPanCardNo() {
		return panCardNo;
	}

	public void setPanCardNo(String panCardNo) {
		this.panCardNo = panCardNo;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}