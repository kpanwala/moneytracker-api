package com.moneymanager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "UsersDetails")
public class Usersdetails {

//	public class Cards {
//		String cardName;
//		String amountUsed;
//		String amountAllotted;
//		
//		public void Card() {
//			
//		}
//
//		public Cards(String cardName, String amountUsed, String amountAllotted) {
//			super();
//			this.cardName = cardName;
//			this.amountUsed = amountUsed;
//			this.amountAllotted = amountAllotted;
//		}
//
//		public String getCardName() {
//			return cardName;
//		}
//
//		public void setCardName(String cardName) {
//			this.cardName = cardName;
//		}
//
//		public String getAmountUsed() {
//			return amountUsed;
//		}
//
//		public void setAmountUsed(String amountUsed) {
//			this.amountUsed = amountUsed;
//		}
//
//		public String getAmountAllotted() {
//			return amountAllotted;
//		}
//
//		public void setAmountAllotted(String amountAllotted) {
//			this.amountAllotted = amountAllotted;
//		}
//		
//	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@Column(name = "fName")
	String f_name;
	@Column(name = "lName")
	String l_name;
	String cards;
	
	
	public Usersdetails() {
		
	}

	public Usersdetails(int id, String f_name, String l_name, String cards) {
		super();
		this.id = id;
		this.f_name = f_name;
		this.l_name = l_name;
		this.cards = cards;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getf_name() {
		return f_name;
	}

	public void setf_name(String f_name) {
		this.f_name = f_name;
	}

	public String getl_name() {
		return l_name;
	}

	public void setl_name(String l_name) {
		this.l_name = l_name;
	}

	public String getCards() {
		return cards;
	}

	public void setCards(String cards) {
		this.cards = cards;
	}

}
