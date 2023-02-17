package com.masai.service;

import java.util.List;
import java.util.Optional;

import com.masai.exception.BankAccountNotExists;
import com.masai.exception.UserNotLoggedInException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.BankAlreadyAdded;
import com.masai.exception.NotAnyBankAddedYet;
import com.masai.model.BankAccount;
import com.masai.model.CurrentSessionUser;
import com.masai.model.Customer;
import com.masai.model.Wallet;
import com.masai.repository.BankAccountDao;
import com.masai.repository.CustomerDAO;
import com.masai.repository.SessionDAO;

@Service
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{


	@Autowired
	private BankAccountDao bankDao;
	
	@Autowired
	private SessionDAO sessionDao;
	
	@Autowired
	private CustomerDAO customerDAO;




	/*
	* This method is used to create a new bank account
	* @param bankAccount
	* @param uniqueId
	* @return BankAccount
	* @throws UserNotLoggedInException
	* @throws BankAlreadyAdded
	* */
	@Override
	public BankAccount createAccount(BankAccount bankAccount, String uniqueId) throws UserNotLoggedInException,BankAlreadyAdded {

		Optional<CurrentSessionUser> currentUser =  sessionDao.findByUuid(uniqueId);

		if(currentUser.isEmpty()) {
			throw new UserNotLoggedInException("Please Login first");
		}

		Optional<Customer> customer =  customerDAO.findById(currentUser.get().getUserId());
		Wallet wallet = customer.get().getWallet();

		Optional<BankAccount> bankAc = bankDao.findByAccountNumber(bankAccount.getAccountNumber());

		if(bankAc.isPresent()) {
			throw new BankAlreadyAdded("Bank with "+bankAccount.getAccountNumber()+" this Account Number Already Exist");
		}

		bankAccount.setWalletId(wallet.getWalletId());
		BankAccount persistedAccount = bankDao.save(bankAccount);
		log.info(persistedAccount.getCurrencyType() + " Bank Account Created Successfully");
		return persistedAccount;

	}




	/*
	* This method is used to delete bank account by account number
	* @param accountNumber
	* @param uniqueId
	* @return BankAccount
	* @throws BankAccountNotExists
	* @throws UserNotLoggedInException
	* */
	@Override
	public BankAccount removeBank(String accountNumber, String uniqueId) throws BankAccountNotExists, UserNotLoggedInException {
		Optional<CurrentSessionUser> currentUser =  sessionDao.findByUuid(uniqueId);
		
		if(currentUser.isEmpty()) {
			throw new UserNotLoggedInException("Please Login first");
		}
		
		Optional<BankAccount> bankAccount = bankDao.findByAccountNumber(accountNumber);

		if(bankAccount.isEmpty()) {
			throw new BankAccountNotExists("Bank account is not existed with current account Number :" + accountNumber);
		}

		bankDao.delete(bankAccount.get());
		
		return bankAccount.get();
		
	}




	/*
	* This method is used to view bank account by account number
	* @param accountNumber
	* @param uniqueId
	* @return BankAccount
	* */
	@Override
	public BankAccount viewBankAccountByAccountNumber(String accountNumber, String uniqueId) throws BankAccountNotExists, UserNotLoggedInException {
		
		Optional<CurrentSessionUser> currentUser =  sessionDao.findByUuid(uniqueId);
		
		if(currentUser.isEmpty()) {
			throw new UserNotLoggedInException("Please Login first");
		}
		
		Optional<BankAccount> bankAccount = bankDao.findByAccountNumber(accountNumber);
		
		if(bankAccount.isPresent()) {
			return bankAccount.get();
		}else {
			throw new BankAccountNotExists("Bank account is not existed with current account Number :" + accountNumber);
		}
		
		
	}




	/*
	* This method is used to view all bank account
	* @param uniqueId
	* @return List<BankAccount>
	* */
	@Override
	public List<BankAccount> viewAllAccount(String uniqueId) throws UserNotLoggedInException, NotAnyBankAddedYet, BankAccountNotExists {
		Optional<CurrentSessionUser> currentUser =  sessionDao.findByUuid(uniqueId);
		
		if(currentUser.isEmpty()) {
			throw new UserNotLoggedInException("Please Login first");
		}
		
		Optional<Customer> customer =  customerDAO.findById(currentUser.get().getUserId());
		Wallet wallet = customer.get().getWallet();

		List<BankAccount> bankAccounts= bankDao.findAllByWalletId(wallet.getWalletId());
		
		if(bankAccounts.size() > 0) {
			return bankAccounts;
		}else {
			throw new BankAccountNotExists("Bank account is not existed in current user ");
		}
		
		
	}

}
