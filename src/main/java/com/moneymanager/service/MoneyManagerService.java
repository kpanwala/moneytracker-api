package com.moneymanager.service;

import java.util.List;
import java.util.Optional;

import com.moneymanager.dto.UserDetailsResponse;
import com.moneymanager.entity.Transactions;
import com.moneymanager.entity.Usersdetails;

public interface MoneyManagerService {

	List<String> testApi();

	int getIdOfUser(String uname, String password);

	//Optional<UserDetailsResponse> getUserDetails(int id);
	UserDetailsResponse getUserDetailsWithCardsById(int id);

	List<Transactions> getTransactionsOfUser(int id);

	List<Usersdetails> getAllUsers();
	
	public List<Transactions> findUserTransactionsByYear(int id, int startYear, int endYear);
}
