package com.moneymanager.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Transactions")
public class Transactions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int transId;

	String placeOfTransaction;
	int transactionAmount;
	String cardIdUsed;
	String category;
	Timestamp dateOfTransaction;
	
	@JsonIgnore
	int id;
	public int getTransId() {
		return transId;
	}
	public void setTransId(int transId) {
		this.transId = transId;
	}
	public String getPlaceOfTransaction() {
		return placeOfTransaction;
	}
	public void setPlaceOfTransaction(String placeOfTransaction) {
		this.placeOfTransaction = placeOfTransaction;
	}
	public int getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(int transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public String getCardIdUsed() {
		return cardIdUsed;
	}
	public void setCardIdUsed(String cardIdUsed) {
		this.cardIdUsed = cardIdUsed;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Timestamp getDateOfTransaction() {
		return dateOfTransaction;
	}
	public void setDateOfTransaction(Timestamp dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Transactions(int transId, String placeOfTransaction, int transactionAmount, String cardIdUsed,
			String category, Timestamp dateOfTransaction, int id) {
		super();
		this.transId = transId;
		this.placeOfTransaction = placeOfTransaction;
		this.transactionAmount = transactionAmount;
		this.cardIdUsed = cardIdUsed;
		this.category = category;
		this.dateOfTransaction = dateOfTransaction;
		this.id = id;
	}
	public Transactions() {
		super();
	}
	@Override
	public String toString() {
		return "Transactions [transId=" + transId + ", placeOfTransaction=" + placeOfTransaction
				+ ", transactionAmount=" + transactionAmount + ", cardIdUsed=" + cardIdUsed + ", category=" + category
				+ ", dateOfTransaction=" + dateOfTransaction + ", id=" + id + "]";
	}
}
