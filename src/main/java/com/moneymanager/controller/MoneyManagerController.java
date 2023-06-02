package com.moneymanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moneymanager.entity.Transactions;
import com.moneymanager.entity.Usersdetails;
import com.moneymanager.service.MoneyManagerService;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/moneymanager")
public class MoneyManagerController {

	@Autowired
	public MoneyManagerService moneyManagerService;
	public static int id = -1;

	/*
	 * ENDPOINT: /moneymanager/authenticate?uname=kalp&pass=12345
	 */
	@GetMapping("/authenticate")
	@CrossOrigin(origins = "http://localhost:4200")
	@ResponseBody
	public ResponseEntity<Usersdetails> findUserDetails(@RequestParam("uname") String username,
			@RequestParam("pass") String password) {
		System.out.println("Into auth func UNAME: "+username+" password: "+password);
		
		id=this.moneyManagerService.getIdOfUser(username, password);
		
		System.out.println(id);
		Usersdetails uDetails = null;
		if (id != -1) {
			uDetails = this.moneyManagerService.getUserDetails(id).orElse(null);
		}
		return new ResponseEntity<>(uDetails, HttpStatus.OK);
	}
	
	/*
	 * ENDPOINT: /moneymanager/getUserTransactions?id=2
	 */
	@GetMapping("/getUserTransactions")
	@CrossOrigin(origins = "http://localhost:4200")
	@ResponseBody
	public ResponseEntity<List<Transactions>> findUserTransactions(@RequestParam("id") int id) {
		if (id != -1) {
			return new ResponseEntity<>(findUserTransactionsImpl(id), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	public List<Transactions> findUserTransactionsImpl(int id) {
		List<Transactions> uTransactions = null;
		uTransactions = this.moneyManagerService.getTransactionsOfUser(id);

		if (uTransactions != null) {
			return uTransactions.stream().collect(Collectors.toList());
		} else {
			return null;
		}
	}

	@GetMapping("/allUsers")
	@ResponseBody
	public ResponseEntity<List<Usersdetails>> getAllUserDetails() {
		return new ResponseEntity<>(this.moneyManagerService.getAllUsers(), HttpStatus.OK);
	}

	@GetMapping("/")
	@ResponseBody
	public ResponseEntity<List<String>> testApi() {
		return new ResponseEntity<>(this.moneyManagerService.testApi(), HttpStatus.OK);
	}

}
/*
 * PAYLOAD structure { userDetails:{ id:"", fName:"", lName:"", cards:[] },
 * transactions:{ transId placeOfTransaction transactionAmount category cardUsed
 * dateOfTransaction } }
 */
