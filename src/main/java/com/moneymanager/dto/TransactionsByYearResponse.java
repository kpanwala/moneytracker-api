package com.moneymanager.dto;

import java.util.List;
import lombok.Data;

@Data
public class TransactionsByYearResponse {
	List<TransactionByYear> transactionsByYear;

	public TransactionsByYearResponse(List<TransactionByYear> transactionsByYear) {
		super();
		this.transactionsByYear = transactionsByYear;
	}

	public TransactionsByYearResponse() {
		super();
	}

	public List<TransactionByYear> getTransactionsByYear() {
		return transactionsByYear;
	}

	public void setTransactionsByYear(List<TransactionByYear> transactionsByYear) {
		this.transactionsByYear = transactionsByYear;
	}
}
