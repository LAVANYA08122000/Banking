package com.lti.dto;

import com.lti.entity.AccountStatus;

public class UpdatePendingRequestDto {

	private int custId;
	private AccountStatus accountStatus;

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

}
