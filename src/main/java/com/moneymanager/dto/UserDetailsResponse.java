package com.moneymanager.dto;

import java.util.List;

import com.moneymanager.entity.Cards;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
public class UserDetailsResponse {
	int id;
	String f_name;
	String l_name;
	List<CardsResponse> cards;
	
	
	public UserDetailsResponse(int id, String f_name, String l_name, List<CardsResponse> cards) {
		super();
		this.id = id;
		this.f_name = f_name;
		this.l_name = l_name;
		this.cards = cards;
	}
	
	public UserDetailsResponse(int id, String f_name, String l_name) {
		super();
		this.id = id;
		this.f_name = f_name;
		this.l_name = l_name;
	}
	
	public UserDetailsResponse() {
	}


	@Override
	public String toString() {
		return "UserDetailsResponse [id=" + id + ", f_name=" + f_name + ", l_name=" + l_name + ", cards=" + cards + "]";
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getF_name() {
		return f_name;
	}


	public void setF_name(String f_name) {
		this.f_name = f_name;
	}


	public String getL_name() {
		return l_name;
	}


	public void setL_name(String l_name) {
		this.l_name = l_name;
	}


	public List<CardsResponse> getCards() {
		return cards;
	}


	public void setCards(List<CardsResponse> cards) {
		this.cards = cards;
	}
	
}
