package com.masai.service;

import java.util.Set;

import com.masai.exception.InsufficientBalanceException;
import com.masai.exception.UserNotLoggedInException;
import com.masai.model.BillPayment;

public interface BillPaymentService {

	BillPayment billPaymentUsingBankAccount(BillPayment billpayment, String uniqueId) throws InsufficientBalanceException, UserNotLoggedInException;
	
	BillPayment billPaymentUsingWallet(BillPayment billpayment, String uniqueId) throws InsufficientBalanceException, UserNotLoggedInException;

	Set<BillPayment> viewBillPayments(String uniqueId) throws UserNotLoggedInException;
}
