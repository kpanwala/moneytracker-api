package com.moneymanager.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneymanager.entity.Transactions;
import com.moneymanager.entity.Usersdetails;
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

	public int getIdOfUser(String uname, String password) {
		List<Integer> userCreds = userCredentialsRepository.findUserCredentials(uname, password);
		return (userCreds.size()==0 ? -1 : userCreds.get(0));
	}

	public Optional<Usersdetails> getUserDetails(int id) {
		Optional<Usersdetails> tp=userRepository.findById(id);
		return tp;
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
