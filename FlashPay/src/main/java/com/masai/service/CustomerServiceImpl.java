package com.masai.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.LoginException;
import com.masai.model.Customer;
import com.masai.model.Wallet;
import com.masai.repository.CustomerDAO;
import com.masai.repository.WalletDao;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerDAO signUpDAO;
	
	@Autowired
	private WalletDao walletDao;
 	
	@Autowired
	private CurrentUserSessionService getCurrentLoginUserSession;

	@Override
	public Customer	createNewSignUp(Customer customer) throws LoginException {
		Optional<Customer> options = signUpDAO.findByMobileNo(customer.getMobileNo());
		if(options.isPresent()) {
			throw new LoginException("User already exist with this mobile number");
		}
		Wallet wallet = new Wallet();
		wallet.setBalance(0.0);
		walletDao.save(wallet);
		customer.setWallet(wallet);
		signUpDAO.save(customer);
		return customer;
	}

	@Override
	public Customer updateSignUpDetails(Customer signUp, String key) throws LoginException {
		Customer signUpDetails = getCurrentLoginUserSession.getSignUpDetails(key);
		
		if(signUpDetails == null)
		{
			throw new LoginException("UnAuthorized!!! No User Found....Try To login first!");
		}
		
		if(signUpDetails.getUserId() == signUp.getUserId())
			{
			signUpDAO.save(signUp);
			return signUp;
			}
		else
			throw new LoginException("Can't change UserId!!");
	}

	
	

}
