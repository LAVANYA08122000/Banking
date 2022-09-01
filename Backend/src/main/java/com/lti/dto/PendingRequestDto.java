package com.lti.dto;

import java.util.List;

import com.lti.entity.Customer;

public class PendingRequestDto extends Status{
	private List<CustomerDto> customers;

	public List<CustomerDto> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomerDto> customers) {
		this.customers = customers;
	}
	
	
}
