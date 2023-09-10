package com.moneymanager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Cards")
public class Cards {
	@Id
	@Column(name = "cardId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String cardName;
	String cardType;	
	int uId;
	
	public Cards(int id, String cardName, String cardType, int uId) {
		super();
		this.id = id;
		this.cardName = cardName;
		this.cardType = cardType;
		this.uId = uId;
	}
	
	public Cards() {
		super();
	}

	public int getuId() {
		return uId;
	}

	public void setuId(int uId) {
		this.uId = uId;
	}

//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "uId")
//	Usersdetails userdetails;
//
//	public Usersdetails getUserdetails() {
//		return userdetails;
//	}
//
//	public void setUserdetails(Usersdetails userdetails) {
//		this.userdetails = userdetails;
//	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

}