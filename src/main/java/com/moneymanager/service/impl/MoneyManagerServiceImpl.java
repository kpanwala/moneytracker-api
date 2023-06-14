package com.moneymanager.service.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moneymanager.dto.CardsResponse;
import com.moneymanager.dto.UserDetailsResponse;
import com.moneymanager.entity.Cards;
import com.moneymanager.entity.Transactions;
import com.moneymanager.entity.Usersdetails;
import com.moneymanager.repository.CardsRepository;
import com.moneymanager.repository.TransactionsRepository;
import com.moneymanager.repository.UserCredentialsRepository;
import com.moneymanager.repository.UserRepository;
import com.moneymanager.service.MoneyManagerService;

import lombok.AllArgsConstructor;

@Service
public class MoneyManagerServiceImpl implements MoneyManagerService {

	@Autowired
	public UserRepository userRepository;
	@Autowired
	public UserCredentialsRepository userCredentialsRepository;
	@Autowired
	public TransactionsRepository transactionRepository;
	@Autowired
	public CardsRepository cardsRepository;

	public int getIdOfUser(String uname, String password) {
		List<Integer> userCreds = userCredentialsRepository.findUserCredentials(uname, password);
		return (userCreds.size()==0 ? -1 : userCreds.get(0));
	}

	
	public UserDetailsResponse getUserDetailsWithCardsById(int id) {
		Usersdetails ud = userRepository.findById(id).orElse(null);
		List<Cards> cds = cardsRepository.findUserCards(id);
		UserDetailsResponse output = new UserDetailsResponse(ud.getId(),ud.getF_name(), ud.getL_name());
		List<CardsResponse> cdList = new LinkedList<>();  
		for(Cards cd: cds) {
			cdList.add(new CardsResponse(cd.getId(), cd.getCardName(), cd.getCardType()));
		}
		output.setCards(cdList);
        return output;
    }

	public List<Transactions> getTransactionsOfUser(int id) {
		return transactionRepository.findUserTransations(id);
	}

	public List<Usersdetails> getAllUsers() {
		return (List<Usersdetails>) userRepository.findAll();
	}

	public List<String> testApi() {
		return List.of("kalp", "aakash", "denis");
	}
}
