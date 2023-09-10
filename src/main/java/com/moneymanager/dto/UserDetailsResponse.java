package com.moneymanager.dto;

import java.util.List;

import com.moneymanager.entity.Cards;
import com.moneymanager.entity.Usersdetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@Data
public class UserDetailsResponse {
	Usersdetails userdetails;
	List<CardsResponse> card;
	
	public UserDetailsResponse() {
		super();
	}

	public UserDetailsResponse(Usersdetails userdetails, List<CardsResponse> card) {
		super();
		this.userdetails = userdetails;
		this.card = card;
	}
}
