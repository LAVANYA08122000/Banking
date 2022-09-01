package com.lti.dto;

import java.util.List;

public class AccountStatementStatus extends Status{
	private List<TopFiveTransactionDto> transactions;

	public List<TopFiveTransactionDto> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TopFiveTransactionDto> transactions) {
		this.transactions = transactions;
	}
}
