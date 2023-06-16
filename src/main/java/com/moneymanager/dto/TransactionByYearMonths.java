package com.moneymanager.dto;

import java.util.List;

import lombok.Data;

@Data
public class TransactionByYearMonths {
	
	String year;
	List<TransactionByMonths> transactionsByMonths;
	
	public TransactionByYearMonths(String year, List<TransactionByMonths> transactionsByMonths) {
		super();
		this.year = year;
		this.transactionsByMonths = transactionsByMonths;
	}
	
	public TransactionByYearMonths() {
		super();
	}
	
}
