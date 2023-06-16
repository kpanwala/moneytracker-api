package com.moneymanager.dto;

import java.util.List;

import com.moneymanager.entity.Transactions;

import lombok.Data;

@Data
public class TransactionByMonths {
	String month;
	List<Transactions> transactions;
	
	public TransactionByMonths(String month, List<Transactions> transactions) {
		super();
		this.month = month;
		this.transactions = transactions;
	}
	public TransactionByMonths() {
		super();
	}
}
