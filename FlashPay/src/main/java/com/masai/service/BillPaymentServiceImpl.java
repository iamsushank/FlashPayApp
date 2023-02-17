package com.masai.service;

import com.masai.exception.InsufficientBalanceException;
import com.masai.exception.UserNotLoggedInException;
import com.masai.model.*;
import com.masai.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
public class BillPaymentServiceImpl implements BillPaymentService{
	
	@Autowired
	private BillPaymentDao billDao;
	
	@Autowired
	private SessionDAO sessionDao;
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private BankAccountDao bankAccountDao;
	
	@Autowired
	private WalletDao walletDao;
	
	@Autowired
	private TransactionDao transactionDao;

	@Override
	public BillPayment billPaymentUsingBankAccount(BillPayment billpayment, String uniqueId) throws InsufficientBalanceException, UserNotLoggedInException {

		CurrentSessionUser currentUser =  sessionDao
											.findByUuid(uniqueId)
											.orElseThrow(() -> new UserNotLoggedInException("Please Login first"));

		Wallet wallet = customerDAO
						.findById(currentUser.getUserId())
						.orElseThrow(() -> new UserNotLoggedInException("Please Login first"))
						.getWallet();

		BankAccount bankAccount = bankAccountDao
									.findByAccountNumber(billpayment.getAccountNumber())
									.orElseThrow(() -> new UserNotLoggedInException("Bank Account not found"));



		return null;
	}

	@Override
	public BillPayment billPaymentUsingWallet(BillPayment billpayment, String uniqueId) throws InsufficientBalanceException, UserNotLoggedInException {
		Optional<CurrentSessionUser> currentUser =  sessionDao.findByUuid(uniqueId);

		if(currentUser.isEmpty()) {
			throw new UserNotLoggedInException("Please Login first");
		}

		Optional<Customer> customer =  customerDAO.findById(currentUser.get().getUserId());
		Wallet wallet = customer.get().getWallet();



		if(wallet.getBalance()<billpayment.getAmount()) {
			throw new InsufficientBalanceException("Insufficient balance in wallet, Add money to your wallet");
		}
		
		wallet.setBalance(wallet.getBalance()-billpayment.getAmount());
		walletDao.save(wallet);
		
		billpayment.setWalletId(wallet.getWalletId());
		billpayment.setTime(LocalDate.now());
		
		BillPayment completedPayment = billDao.save(billpayment);
		
		if(completedPayment!=null) {
			Transaction transaction = new Transaction();
			transaction.setDescription(billpayment.getBilltype() +  " successfull");
			transaction.setAmount(billpayment.getAmount());
			transaction.setTransactionDate(LocalDate.now());
			transaction.setTransactionType(billpayment.getTransactionType());
			transaction.setWalletId(wallet.getWalletId());
			wallet.getTransaction().add(transaction);
			transactionDao.save(transaction);
		}
		System.out.println(billpayment);
		return completedPayment;
	}

	@Override
	public Set<BillPayment> viewBillPayments(String uniqueId) throws UserNotLoggedInException {


		Optional<CurrentSessionUser> currentUser =  sessionDao.findByUuid(uniqueId);

		if(!currentUser.isPresent()) {
			throw new UserNotLoggedInException("Please Login first");
		}

		Optional<Customer> customer =  customerDAO.findById(currentUser.get().getUserId());
		Wallet wallet = customer.get().getWallet();

		Set<BillPayment> billpaymnets = billDao.findByWalletId(wallet.getWalletId());
		return billpaymnets;
	}

	
	
}
