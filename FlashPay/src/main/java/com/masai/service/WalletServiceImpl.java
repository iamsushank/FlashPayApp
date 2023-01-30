package com.masai.service;

import java.lang.StackWalker.Option;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.naming.InsufficientResourcesException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import com.masai.exception.BankAccountNotExsists;
import com.masai.exception.BeneficiaryDetailException;
import com.masai.exception.CustomerNotException;
import com.masai.exception.InsufficientBalanceException;
import com.masai.exception.LoginException;
import com.masai.exception.NotAnyBankAddedYet;
import com.masai.model.BankAccount;
import com.masai.model.BeneficiaryDetail;
import com.masai.model.CurrentSessionUser;
import com.masai.model.Customer;
import com.masai.model.Transaction;
import com.masai.model.TransactionType;
import com.masai.model.Wallet;
import com.masai.repository.BankAccountDao;
import com.masai.repository.BeneficiaryDetailDao;
import com.masai.repository.CustomerDAO;
import com.masai.repository.LogInDAO;
import com.masai.repository.SessionDAO;
import com.masai.repository.TransactionDao;
import com.masai.repository.WalletDao;

@Service
public class WalletServiceImpl implements WalletService {
	
	@Autowired
	private TransactionDao transactiodao;
	
	@Autowired
	private TranscationServiceImpl transactionserviceImpl;
	
	@Autowired
	private BankAccountDao bankaccountdao;
	
	@Autowired
	private BeneficiaryDetailDao beneficiaryDetailDao;
	
	
	@Autowired
	private WalletDao walletDao;
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private SessionDAO currentSessionDAO;
	
	@Autowired
	private LogInDAO logindao;
	
	
	
	@Override
	public Double showBalance(String uniqueId) throws CustomerNotException, LoginException {
		
		Optional<CurrentSessionUser> currentSessionOptional = currentSessionDAO.findByUuid(uniqueId);
		
		if(!currentSessionOptional.isPresent()) {
			throw new LoginException("You need to login first");
		}

		Optional<Customer> customer =  customerDAO.findById(currentSessionOptional.get().getUserId());
		Wallet wallet =  customer.get().getWallet();
		Double balance = wallet.getBalance();
		return balance;
	}




	@Override
	public Transaction fundTransfer(String sourceMoblieNo, String targetMobileNo, Double amout,String uniqueId) throws CustomerNotException, LoginException,BeneficiaryDetailException, InsufficientBalanceException {
		
		Optional<CurrentSessionUser> currentUser = currentSessionDAO.findByUuid(uniqueId);
		if(!currentUser.isPresent()) {
			throw new LoginException("You need to login first");
		}
		Optional<Customer> customerUser = customerDAO.findByMobileNo(sourceMoblieNo);
		Customer customer = customerUser.get();
		Wallet wallet = customer.getWallet();
		
		
		Boolean flag=true;
		List<BeneficiaryDetail> beneficiarydetails = wallet.getBeneficiaryDetails();
		
		if(beneficiarydetails==null || beneficiarydetails.size()==0) {
			throw new BeneficiaryDetailException("You need to add beneficiary to you wallet");
		}
		
		for(BeneficiaryDetail bd:beneficiarydetails) {
			System.out.println(bd);
			if (bd.getBeneficiaryMobileNo().equals(targetMobileNo)) {
				flag = false;
				break;
			}
		}
		
		Optional<Customer> tragetopt = customerDAO.findByMobileNo(targetMobileNo);
		System.out.println(tragetopt);
		
		
		if(flag) {
			throw new CustomerNotException("beneficiary is not add to wallet list");
		}
		
		if(!tragetopt.isPresent()) {
			System.out.println(tragetopt);
			throw new CustomerNotException("Number is not linked with the Database");
		}
		Customer tragetCustomer = tragetopt.get();
		
		if(wallet.getBalance()<amout || wallet.getBalance()==null) {
			throw new InsufficientBalanceException("Insufficent balance");
		}
		
		wallet.setBalance(wallet.getBalance()-amout);
		if(tragetCustomer.getWallet().getBalance()==null ) {
			tragetCustomer.getWallet().setBalance(amout);
		}else {
			tragetCustomer.getWallet().setBalance(tragetCustomer.getWallet().getBalance()+amout);
		}
		
		// Add to transaction
		
		Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.WALLET_TO_WALLET);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAmount(amout);
        transaction.setDescription("Fund Transfer from Wallet to Wallet Successfull !");
        transaction.setWalletId(wallet.getWalletId());
        wallet.getTransaction().add(transaction);
        transactiodao.save(transaction);
		
		
		return transaction;
	}

	@Override
	public Transaction depositeAmount(String uniqueId, Double amount) throws CustomerNotException, LoginException, InsufficientBalanceException {
		
		Optional<CurrentSessionUser> currentUser = currentSessionDAO.findByUuid(uniqueId);
		
		if(!currentUser.isPresent()) {
			throw new LoginException("You need to login first");
		}
		
		Optional<Customer> customerUser = customerDAO.findById(currentUser.get().getUserId());
		
		Customer customer = customerUser.get();

		Wallet wallet = customer.getWallet();

		BankAccount bankacc = bankaccountdao.findByWalletId(wallet.getWalletId()); 
		
				
		if(bankacc==null) {
			throw new NotAnyBankAddedYet("Bank Account not added to the wallet yet");
		}
		
		if(wallet.getBalance()<amount) {
			throw new InsufficientBalanceException("Insufficient balance");
		}
		
		customer.getWallet().setBalance(wallet.getBalance()-amount);
		
		bankacc.setBankBalance(bankacc.getBankBalance()+amount);
		
		bankaccountdao.save(bankacc);
		walletDao.save(wallet);
		
		
		// Transaction Details
		Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.WALLET_TO_BANK);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAmount(amount);
        transaction.setDescription("Fund Transfer from Wallet to Bank is successfull!");
        transaction.setWalletId(wallet.getWalletId());
        wallet.getTransaction().add(transaction);
        transactiodao.save(transaction);  
        return transaction;
	}

	@Override
	public List<BeneficiaryDetail> getList(String uniqueId) throws CustomerNotException, LoginException, BeneficiaryDetailException {
		
			Optional<CurrentSessionUser> currentuser = currentSessionDAO.findByUuid(uniqueId);
			System.out.println(currentuser.get());
			
			if(!currentuser.isPresent()) {
				throw new LoginException("You need to login first");
			}
			
			Optional<Customer> custOptional = customerDAO.findById(currentuser.get().getUserId());
			Customer curruser = custOptional.get();
			Wallet wallet = curruser.getWallet();
			
			List<BeneficiaryDetail> beneficiarydetails = wallet.getBeneficiaryDetails();
			
			if(beneficiarydetails==null) {
				throw new BeneficiaryDetailException("You need to add beneficiary to your wallet");
			}
		
			return wallet.getBeneficiaryDetails();
		
	}


	/**
	 * This method is used to add money to the wallet from the bank account
	 * it will check the bank account is linked or not
	 * if linked then it will find the bank account and add the amount to the wallet
	 * @param accountNumber -> bank account number
	 * @param uniqueId -> unique id of the user
	 * @param amount -> amount to be added
	 * @return -> return the customer object
	 */
	public Customer addMoney(String accountNumber, String uniqueId, Double amount) throws LoginException, CustomerNotException, InsufficientBalanceException {

		Optional<CurrentSessionUser> currOptional = currentSessionDAO.findByUuid(uniqueId);
		
		if(currOptional.isEmpty()) {
			throw new LoginException("You need to login first");
		}
		
		Customer currcustomer = customerDAO.findById(currOptional.get().getUserId()).get();
		Wallet wallet = currcustomer.getWallet();

		Optional<BankAccount> bankacc = bankaccountdao.findByAccountNumber(accountNumber);
		BankAccount bankAccount = bankacc.get();

		if(bankAccount==null) {
		    throw new CustomerNotException("Bank not linked");
		}
		
		if(bankAccount.getBankBalance() == 0 || bankAccount.getBankBalance() < amount) {
		    throw new InsufficientBalanceException("Insufficient balance in bank");
		}

		bankAccount.setBankBalance(bankAccount.getBankBalance() - amount);
		
		wallet.setBalance(wallet.getBalance() + amount);
		
		bankaccountdao.save(bankAccount);
		walletDao.save(wallet);
		customerDAO.save(currcustomer);	
		
		Transaction transaction = new Transaction();

        transaction.setTransactionType(TransactionType.BANK_TO_WALLET);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAmount(amount);
        transaction.setDescription("Fund Transferred from Bank to Wallet successfully");
        transaction.setWalletId(wallet.getWalletId());
        
        wallet.getTransaction().add(transaction);
        transactiodao.save(transaction);
        
        return currcustomer;

	}

	
	

}
