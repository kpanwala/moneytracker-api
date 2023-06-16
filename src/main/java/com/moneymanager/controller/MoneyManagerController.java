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

import com.moneymanager.dto.TransactionByYear;
import com.moneymanager.dto.TransactionsByYearResponse;
import com.moneymanager.dto.UserDetailsResponse;
import com.moneymanager.entity.Transactions;
import com.moneymanager.entity.Usersdetails;
import com.moneymanager.service.MoneyManagerService;

import jakarta.transaction.Transaction;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
	 * 
	 * Response:
	 * 
	 * { "userdetails": { "id": 1, "f_name": "Kalp", "l_name": "Panwala", "cards": [
	 * { "cardId": 1, "cardName": "HDFC Bank Millenia", "cardType": "Debit" }, {
	 * "cardId": 2, "cardName": "IDFC Bank Select", "cardType": "Credit" } ] } }
	 */
	@GetMapping("/authenticate")
	@CrossOrigin(origins = "http://localhost:4200")
	@ResponseBody
	public ResponseEntity<UserDetailsResponse> findUserDetails(@RequestParam("uname") String username,
			@RequestParam("pass") String password) {
		System.out.println("Into auth func UNAME: " + username + " password: " + password);

		id = this.moneyManagerService.getIdOfUser(username, password);

		System.out.println(id);
		UserDetailsResponse uDetails = null;
		if (id != -1) {
			uDetails = this.moneyManagerService.getUserDetailsWithCardsById(id);
		}
		return new ResponseEntity<>(uDetails, HttpStatus.OK);
	}

	/*
	 * ENDPOINT: /moneymanager/getUserTransactions?id=2
	 * 
	 * Response: [ { "transId": 7, "placeOfTransaction": "Jio Mart",
	 * "transactionAmount": 120, "cardIdUsed": "3", "category": "Food",
	 * "dateOfTransaction": "2023-03-11T18:30:00.000+00:00" }, { "transId": 8,
	 * "placeOfTransaction": "Swiggy", "transactionAmount": 203, "cardIdUsed": "4",
	 * "category": "Food", "dateOfTransaction": "2023-03-24T18:30:00.000+00:00" } ]
	 *
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

	/*
	 * ENDPOINT: /moneymanager/getUserTransactionsByYear?id=2&startYear=2022&endYear=2023
	 * 
	 * {
		    "transactionsByYear": [
		        {
		            "year": "2023",
		            "transactions": [
		                {
		                    "transId": 8,
		                    "placeOfTransaction": "Swiggy",
		                    "transactionAmount": 203,
		                    "cardIdUsed": "4",
		                    "category": "Food",
		                    "dateOfTransaction": "2023-03-24T18:30:00.000+00:00"
		                },
		                {
		                    "transId": 7,
		                    "placeOfTransaction": "Jio Mart",
		                    "transactionAmount": 120,
		                    "cardIdUsed": "3",
		                    "category": "Food",
		                    "dateOfTransaction": "2023-03-11T18:30:00.000+00:00"
		                }
		            ]
		        },
		        {
		            "year": "2022",
		            "transactions": [
		                {
		                    "transId": 14,
		                    "placeOfTransaction": "Ajio",
		                    "transactionAmount": 3200,
		                    "cardIdUsed": "1",
		                    "category": "Lifestyle",
		                    "dateOfTransaction": "2022-03-22T13:00:00.000+00:00"
		                }
		            ]
		        }
		    ]
		}
	 * 
	 * 
	 */
	@GetMapping("/getUserTransactionsByYear")
	@CrossOrigin(origins = "http://localhost:4200")
	@ResponseBody
	public ResponseEntity<TransactionsByYearResponse> findUserTransactionsByYear(@RequestParam(value="id", required = true) int id,
			@RequestParam(value="startYear", required = true) int startYear, @RequestParam(value="endYear", required = false) Optional<Integer> endYear) {
		int endYearTemp;
		if (id != -1) {
			if (endYear.isPresent()) {
				endYearTemp = endYear.get(); 
			}
			else{
				endYearTemp = startYear;
			}
			return new ResponseEntity<>(findUserTransactionsByYearImpl(id, startYear, endYearTemp), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	
	public TransactionsByYearResponse findUserTransactionsByYearImpl(int id, int startYear, int endYear) {
		List<Transactions> uTransactions = null;
		uTransactions = this.moneyManagerService.findUserTransactionsByYear(id, startYear, endYear);
		Map<String, List<Transactions>> mp = new HashMap<>();
		
		uTransactions.stream().forEach((e)->{
			Timestamp t = e.getDateOfTransaction();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(t);
			int year = calendar.get(Calendar.YEAR);
			System.out.println(year);
			List<Transactions> list;
			if(mp.containsKey(year+"")) {
				list = mp.get(year+"");
			}
			else {
				list = new LinkedList<Transactions>();
			}
			list.add(e);
			mp.put(year+"", list);
		});
		
		TransactionsByYearResponse output = new TransactionsByYearResponse();
		List <TransactionByYear> outputYears = new LinkedList<>();
		
		for(Map.Entry<String, List<Transactions>> t: mp.entrySet()) {
			TransactionByYear outputYear = new TransactionByYear();
			outputYear.setYear(t.getKey());
			outputYear.setTransactions(t.getValue());
			outputYears.add(outputYear);
		}
		output.setTransactionsByYear(outputYears);
		
		return output;
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
