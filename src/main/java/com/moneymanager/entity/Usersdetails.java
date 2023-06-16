package com.moneymanager.entity;


import java.util.List;

import com.moneymanager.dto.CardsResponse;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity

@Table(name = "UsersDetails")
public class Usersdetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@Column(name = "fName")
	String f_name;
	@Column(name = "lName")
	String l_name;
	
	@Transient
	@Column(name = "cards")
	List<CardsResponse> cards;

	public Usersdetails(int id, String f_name, String l_name, List<CardsResponse> cards) {
		super();
		this.id = id;
		this.f_name = f_name;
		this.l_name = l_name;
		this.cards = cards;
	}

	public Usersdetails() {
		super();
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
