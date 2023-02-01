package com.masai.service;

import java.util.*;

import com.masai.exception.TransactionNotFoundException;
import com.masai.exception.UserNotLoggedInException;
import com.masai.model.Transaction;
import com.masai.model.TransactionType;

public interface TransactionService {
	
	public List<Transaction> viewAlltransaction(String  uniqueId)throws UserNotLoggedInException, TransactionNotFoundException ;
	
	public List<Transaction> viewTranscationByDate(String from, String to , String uniqueId)  throws UserNotLoggedInException,TransactionNotFoundException ;
		
	public List<Transaction> viewAllTransactionbyTransactionType(String uniqueId,TransactionType type) throws UserNotLoggedInException, TransactionNotFoundException;


}
