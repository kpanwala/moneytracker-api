package com.moneymanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CardsResponse {
	int cardId;
	String cardName;
	String cardType;
	public CardsResponse(int cardId, String cardName, String cardType) {
		super();
		this.cardId = cardId;
		this.cardName = cardName;
		this.cardType = cardType;
	}
	public CardsResponse() {
		super();
	}
	public int getCardId() {
		return cardId;
	}
	public void setCardId(int cardId) {
		this.cardId = cardId;
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
