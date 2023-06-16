package com.moneymanager.dto;

import java.util.List;

import lombok.Data;

@Data
public class TransactionsByMonthsResponse {
	List<TransactionByYearMonths> transactionsByYears;

	public TransactionsByMonthsResponse(List<TransactionByYearMonths> transactionsByMonths) {
		super();
		this.transactionsByYears = transactionsByMonths;
	}

	public TransactionsByMonthsResponse() {
		super();
	}
	
	
}