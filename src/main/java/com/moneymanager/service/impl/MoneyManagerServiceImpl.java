package com.moneymanager.service.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moneymanager.dto.CardsResponse;
import com.moneymanager.dto.TransactionsByYearResponse;
import com.moneymanager.dto.UserDetailsResponse;
import com.moneymanager.entity.Cards;
import com.moneymanager.entity.Transactions;
import com.moneymanager.entity.Usercredentials;
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
		UserDetailsResponse output = new UserDetailsResponse();
		Usersdetails userDetails = userRepository.findById(id).get();
		output.setUserdetails(userDetails);
        List<Cards> cards = cardsRepository.findByUserId(userDetails.getId());
        List<CardsResponse> cardsResponse = cards
        		.stream()
        		.map((e)->new CardsResponse(e.getId(), e.getCardName(), e.getCardType()))
        		.collect(Collectors.toList());
        output.setCard(cardsResponse);
        
        return output;
    }
	
	public List<Transactions> findUserTransactionsByYear(int id, int startYear, int endYear) {
		return transactionRepository.findUserTransationsByYear(id, startYear, endYear);
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
	
	public void saveUsersDetails(Usersdetails usr) {		
		userRepository.save(usr);
	}
	
	public void saveCardForUser(Cards card) {
		cardsRepository.save(card);
	}
	
	public void saveTransaction(Transactions t) {
		transactionRepository.save(t);
	}
	
	public void saveUserCredential(Usercredentials uc) {
		userCredentialsRepository.save(uc);
	}
	
	
}
