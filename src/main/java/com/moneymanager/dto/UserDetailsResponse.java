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
	
	public UserDetailsResponse(Usersdetails userdetails) {
		super();
		this.userdetails = userdetails;
	}
	public UserDetailsResponse() {
		super();
	}
}
