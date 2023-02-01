package com.masai.service;

import java.util.List;

import com.masai.exception.BankAccountNotExists;
import com.masai.exception.BankAlreadyAdded;
import com.masai.exception.NotAnyBankAddedYet;
import com.masai.exception.UserNotLoggedInException;
import com.masai.model.BankAccount;

public interface BankAccountService {
	
	public BankAccount createAccount(BankAccount bankAccount, String uniqueId) throws UserNotLoggedInException,BankAlreadyAdded;

	public BankAccount removeBank(String accountNumber,String uniqueId) throws BankAccountNotExists, UserNotLoggedInException;
	
	public BankAccount viewBankAccountByAccountNumber(String accountNumber, String uniqueId) throws BankAccountNotExists, UserNotLoggedInException;
	
	public List<BankAccount> viewAllAccount(String uniqueId) throws UserNotLoggedInException,NotAnyBankAddedYet, BankAccountNotExists;
}
