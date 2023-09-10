package com.moneymanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moneymanager.dto.CardsResponse;
import com.moneymanager.dto.TransactionByMonths;
import com.moneymanager.dto.TransactionByYear;
import com.moneymanager.dto.TransactionByYearMonths;
import com.moneymanager.dto.TransactionsByMonthsResponse;
import com.moneymanager.dto.TransactionsByYearResponse;
import com.moneymanager.dto.UserData;
import com.moneymanager.dto.UserDetailsResponse;
import com.moneymanager.entity.Cards;
import com.moneymanager.entity.Transactions;
import com.moneymanager.entity.Usercredentials;
import com.moneymanager.entity.Usersdetails;
import com.moneymanager.service.MoneyManagerService;

import jakarta.transaction.Transaction;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		uTransactions.stream().forEach((e)->{
			Date parsedDate;
			Timestamp t = new Timestamp(System.currentTimeMillis());
			try {
				parsedDate = dateFormat.parse(e.getDateOfTransaction());
				t = new java.sql.Timestamp(parsedDate.getTime());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    
			//Timestamp t = e.getDateOfTransaction();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(t);
			int year = calendar.get(Calendar.YEAR);
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
	
	/*
	 * 
	 * ENDPOINT: /getUserTransactionsByMonths?id=4&startYear=2022&endYear=2023
	 * 
	 * RESPONSE: 
	 * 
	 * {
		    "transactionsByMonths": [
		        {
		            "year": "2023",
		            "transactionsByMonths": [
		                {
		                    "month": "1",
		                    "transactions": [
		                        {
		                            "transId": 13,
		                            "placeOfTransaction": "Big Basket",
		                            "transactionAmount": 579,
		                            "cardIdUsed": "8",
		                            "category": "Food",
		                            "dateOfTransaction": "2023-01-04T18:30:00.000+00:00"
		                        }
		                    ]
		                },
		                {
		                    "month": "3",
		                    "transactions": [
		                        {
		                            "transId": 12,
		                            "placeOfTransaction": "Jio Mart",
		                            "transactionAmount": 608,
		                            "cardIdUsed": "6",
		                            "category": "Food",
		                            "dateOfTransaction": "2023-03-21T18:30:00.000+00:00"
		                        },
		                        {
		                            "transId": 11,
		                            "placeOfTransaction": "Asian Paints",
		                            "transactionAmount": 1478,
		                            "cardIdUsed": "7",
		                            "category": "Other",
		                            "dateOfTransaction": "2023-03-13T18:30:00.000+00:00"
		                        }
		                    ]
		                }
		            ]
		        },
		        {
		            "year": "2022",
		            "transactionsByMonths": [
		                {
		                    "month": "7",
		                    "transactions": [
		                        {
		                            "transId": 10,
		                            "placeOfTransaction": "Jio Mart",
		                            "transactionAmount": 420,
		                            "cardIdUsed": "6",
		                            "category": "Food",
		                            "dateOfTransaction": "2022-07-01T18:30:00.000+00:00"
		                        }
		                    ]
		                }
		            ]
		        }
		    ]
		}
	 * 
	 */

	@GetMapping("/getUserTransactionsByMonths")
	@CrossOrigin(origins = "http://localhost:4200")
	@ResponseBody
	public ResponseEntity<TransactionsByMonthsResponse> findUserTransactionsByMonths(@RequestParam(value="id", required = true) int id,
			@RequestParam(value="startYear", required = true) int startYear, @RequestParam(value="endYear", required = false) Optional<Integer> endYear) {
		int endYearTemp;
		if (id != -1) {
			if (endYear.isPresent()) {
				endYearTemp = endYear.get(); 
			}
			else{
				endYearTemp = startYear;
			}
			return new ResponseEntity<>(findUserTransactionsByMonthsImpl(id, startYear, endYearTemp), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	public TransactionsByMonthsResponse findUserTransactionsByMonthsImpl(int id, int startYear, int endYear) {
		List<Transactions> uTransactions = null;
		uTransactions = this.moneyManagerService.findUserTransactionsByYear(id, startYear, endYear);
		Map<String, Map <Integer, List<Transactions>>> mp = new HashMap<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		uTransactions.stream().forEach((e)->{
		    Date parsedDate;
		    Timestamp t = new Timestamp(System.currentTimeMillis());
			try {
				parsedDate = dateFormat.parse(e.getDateOfTransaction());
				t = new java.sql.Timestamp(parsedDate.getTime());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(t);
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH)+1;
			Map <Integer, List<Transactions>> monthsMap;
			
			// Finding the year 
			if(mp.containsKey(year+"")) {
				monthsMap = mp.get(year+"");
			}
			else {
				monthsMap = new HashMap <Integer, List<Transactions>>();
			}
			
			// Finding the Month of the year  			
			List <Transactions> transMonth;
			if(monthsMap.containsKey(month)) {
				transMonth = monthsMap.get(month);
			}
			else {
				transMonth = new LinkedList <Transactions> ();
			}
			transMonth.add(e);
			
			monthsMap.put(Integer.valueOf(month), transMonth);
			mp.put(year+"", monthsMap);
		});
		
		TransactionsByMonthsResponse output = new TransactionsByMonthsResponse();
		List <TransactionByYearMonths> outputYearsMonths = new LinkedList<TransactionByYearMonths>();
		
		
		for(Entry<String, Map<Integer, List<Transactions>>> t: mp.entrySet()) {
			TransactionByYearMonths outputYear = new TransactionByYearMonths();
			List<TransactionByMonths> outputMonthsList = new LinkedList<TransactionByMonths>();
			
			outputYear.setYear(t.getKey()+"");
			Map<Integer, List<Transactions>> outputMonthMap = t.getValue();
			TransactionByMonths outputMonths;
			
			for(Entry<Integer, List<Transactions>> child: outputMonthMap.entrySet()) {
				outputMonths = new TransactionByMonths();
				outputMonths.setMonth(child.getKey()+"");
				outputMonths.setTransactions(child.getValue());
				outputMonthsList.add(outputMonths);
			}
			
			outputYear.setTransactionsByMonths(outputMonthsList);
			outputYearsMonths.add(outputYear);
		}
		output.setTransactionsByYears(outputYearsMonths);
		
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
	
	/*
	 * Payload: 
	 * {
		    "f_name": "Raj",
		    "l_name": "Kapoor"
	   }

	 * 
	 */
//	@PostMapping("/addUser")
//	@CrossOrigin(origins = "http://localhost:4200")
//	public ResponseEntity<String> addUserDetails(@RequestBody Usersdetails user)  
//	{  
//		this.moneyManagerService.saveUsersDetails(user);
//		return new ResponseEntity<>("Success", HttpStatus.OK); 
//	}
	
//	@PostMapping("/addCredential")
//	@CrossOrigin(origins = "http://localhost:4200")
//	public ResponseEntity<String> addUserCredential(@RequestBody Usercredentials user)  
//	{  
//		this.moneyManagerService.saveUserCredential(user);
//		return new ResponseEntity<>("Success", HttpStatus.OK); 
//	}
	
	@PostMapping("/addUser")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<String> addUserCredential(@RequestBody UserData user)  
	{  
		this.moneyManagerService.saveUsersDetails(new Usersdetails(0,user.getF_name(), user.getL_name()));
		this.moneyManagerService.saveUserCredential(new Usercredentials(0, user.getUsername(), user.getPassword()));
		return new ResponseEntity<>("Success", HttpStatus.OK); 
	}
	
	/*
	 * Payload: 
	 * 
	 * {
		  "cardName":"Axis Magnus",
		  "cardType":"debit",
		  "uId":5
	   }
	 * 
	 */
	@PostMapping("/addCard")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<String> addCardForUser(@RequestBody Cards card)  
	{  
		this.moneyManagerService.saveCardForUser(card);
		return new ResponseEntity<>("Success", HttpStatus.OK); 
	}
	
	/*
	 * Payload: 
	 * 
	 * {
		  "cardName":"Axis Magnus",
		  "cardType":"debit",
		  "uId":5
		}
	 * 
	 */
	@PostMapping("/addTransaction")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<String> addTransaction(@RequestBody Transactions t)  
	{
		this.moneyManagerService.saveTransaction(t);
		return new ResponseEntity<>("Success", HttpStatus.OK); 
	}

}
/*
 * PAYLOAD structure { userDetails:{ id:"", fName:"", lName:"", cards:[] },
 * transactions:{ transId placeOfTransaction transactionAmount category cardUsed
 * dateOfTransaction } }
 */
