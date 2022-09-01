package com.lti.dto;

import com.lti.entity.Address;
import com.lti.entity.Customer;
import com.lti.entity.Income;

public class CustomerDetails extends Status {
	private CustomerDto customer;
//	private Income income;
//	private Address address;
	public CustomerDto getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerDto customer) {
		this.customer = customer;
	}
//	public Income getIncome() {
//		return income;
//	}
//	public void setIncome(Income income) {
//		this.income = income;
//	}
//	public Address getAddress() {
//		return address;
//	}
//	public void setAddress(Address address) {
//		this.address = address;
//	}
}
