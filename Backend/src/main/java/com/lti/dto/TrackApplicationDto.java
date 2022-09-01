package com.lti.dto;

import com.lti.entity.AccountStatus;

public class TrackApplicationDto extends Status {

	private AccountStatus accountStatus;

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

}
