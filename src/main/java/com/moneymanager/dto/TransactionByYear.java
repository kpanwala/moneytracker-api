package com.moneymanager.dto;

import java.util.List;

import com.moneymanager.entity.Transactions;

import lombok.Data;

@Data
public class TransactionByYear{
	String year;
	List<Transactions> transactions;

	public TransactionByYear() {
		super();
	}

	public TransactionByYear(String year, List<Transactions> transactions) {
		super();
		this.year = year;
		this.transactions = transactions;
	}
}
