package com.lti.dto;

import java.util.List;

public class TopFiveTransactionsStatus extends Status {
	private List<TopFiveTransactionDto> topTransactions;

	public List<TopFiveTransactionDto> getTopTransactions() {
		return topTransactions;
	}

	public void setTopTransactions(List<TopFiveTransactionDto> topTransactions) {
		this.topTransactions = topTransactions;
	}
}
