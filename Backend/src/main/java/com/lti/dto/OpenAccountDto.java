package com.lti.dto;

import com.lti.entity.Customer;

public class OpenAccountDto extends Status {

	private int custId;
	

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

}
