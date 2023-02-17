package com.masai.controllers;

import java.util.List;

import com.masai.exception.BankAccountNotExists;
import com.masai.exception.UserNotLoggedInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.masai.exception.BankAlreadyAdded;
import com.masai.exception.NotAnyBankAddedYet;
import com.masai.model.BankAccount;
import com.masai.service.BankAccountService;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bank")
public class BankAccountController {
	
	@Autowired
	private BankAccountService bankService;

	@PostMapping(value = "/{id}", consumes = "application/json")
	public  ResponseEntity<BankAccount> addBankAccountToWallet(@Valid @RequestBody BankAccount bankaccount, @PathVariable("id") String uniqueId) throws BankAlreadyAdded, UserNotLoggedInException {
		System.out.println(bankaccount.getAccountNumber());
		System.out.println(uniqueId);
		BankAccount accountAdded = bankService.createAccount(bankaccount,uniqueId);

		return new ResponseEntity<>(accountAdded, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{acc}/{id}")
	public  ResponseEntity<BankAccount> deleteBankAccountfromWallet( @PathVariable("acc") String accountNumber,@PathVariable("id") String uniqueId) throws BankAccountNotExists, UserNotLoggedInException {
		BankAccount accountDeleted = bankService.removeBank(accountNumber, uniqueId);
		return new ResponseEntity<>(accountDeleted, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{acc}/{id}")
	public  ResponseEntity<BankAccount> viewBankAccountDetails( @PathVariable("acc") String accountNumber,@PathVariable("id") String uniqueId) throws BankAccountNotExists, UserNotLoggedInException {
		BankAccount accountDetails = bankService.viewBankAccountByAccountNumber(accountNumber, uniqueId);
		return new ResponseEntity<>(accountDetails,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{id}")
	public  ResponseEntity<List<BankAccount>> viewAllAccountDetails(@PathVariable("id") String uniqueId) throws NotAnyBankAddedYet, UserNotLoggedInException, BankAccountNotExists {
		List<BankAccount> accountDetails = bankService.viewAllAccount(uniqueId);
		return new ResponseEntity<>(accountDetails, HttpStatus.ACCEPTED);
	}
	
	
	
}
