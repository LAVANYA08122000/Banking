package com.lti.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "table_income")
public class Income {

	@Id
	@SequenceGenerator(name = "income_seq", initialValue = 901, allocationSize = 1)
	@GeneratedValue(generator = "income_seq", strategy = GenerationType.SEQUENCE)
	@Column(name="incomeid")
	private int incomeId;

	@Column(name="occupation_type")
	private String occupationType;
	@Column(name="income_source")
	private String incomeSource;
	@Column(name="gross_income")
	private double grossIncome;

	@OneToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	public int getIncomeId() {
		return incomeId;
	}

	public void setIncomeId(int incomeId) {
		this.incomeId = incomeId;
	}

	public String getOccupationType() {
		return occupationType;
	}

	public void setOccupationType(String occupationType) {
		this.occupationType = occupationType;
	}

	public String getIncomeSource() {
		return incomeSource;
	}

	public void setIncomeSource(String incomeSource) {
		this.incomeSource = incomeSource;
	}

	public double getGrossIncome() {
		return grossIncome;
	}

	public void setGrossIncome(double grossIncome) {
		this.grossIncome = grossIncome;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}