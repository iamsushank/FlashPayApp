package com.masai.service;

import com.masai.exception.LoginException;
import com.masai.model.CurrentSessionUser;
import com.masai.model.Customer;
import com.masai.model.LogIn;
import com.masai.repository.CustomerDAO;
import com.masai.repository.LogInDAO;
import com.masai.repository.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	private CustomerDAO signUpDAO;
	
	@Autowired
	private SessionDAO SessionDAO;
	
	@Autowired
	private CurrentUserSessionService getCurrentLoginUserSession;
	
	@Autowired
	private LogInDAO loginDAO;
	
	

	/*
	* This method is used to login into the account
	* @param loginData
	* @return String
	* */
	@Override
	public String logInAccount(LogIn loginData) throws LoginException {
		Optional<Customer> customerOptional = signUpDAO.findByMobileNo(loginData.getMobileNo());
		
		if(customerOptional.isEmpty()) {
			throw new LoginException("Invalid mobile Number ");
		}
		
		Customer customerLogIn = customerOptional.get();

		Integer customerId = customerLogIn.getUserId();
		Optional<CurrentSessionUser> currentSessionUser = SessionDAO.findByUserId(customerId);
		
		if(currentSessionUser.isPresent()) {
			throw new LoginException("User already login with this userId");
		}
		
		if((customerLogIn.getMobileNo().equals(loginData.getMobileNo()))  && customerLogIn.getPassword().equals(loginData.getPassword())) {

			String key = RandomString.getRandomString();

			loginDAO.save(loginData);

			CurrentSessionUser persistCurrentSessionUser = new CurrentSessionUser(loginData.getUserId(), customerLogIn.getUserId(), key, customerLogIn.getMobileNo(), LocalDate.now());

			SessionDAO.save(persistCurrentSessionUser);

			return persistCurrentSessionUser.toString();
		}
		else {
			throw new LoginException("Invalid mobile and Password");
		}
		
	}
	




	/*
	* This method is used to log out from the account
	* @param key
	* @return String
	* */
	@Override
	public String logOutFromAccount(String key) throws LoginException {
		Optional<CurrentSessionUser> currentSessionuserOptional = SessionDAO.findByUuid(key);
		
		if(currentSessionuserOptional.isEmpty()) {
			throw new LoginException("User has not logged in with this Userid");
		}
		
		CurrentSessionUser currentSessionUser = getCurrentLoginUserSession.getCurrentUserSession(key);

		Optional<LogIn> logInData = loginDAO.findById(currentSessionuserOptional.get().getId());

		if (logInData.isEmpty()) {
			throw new LoginException("User has not logged in with this Userid");
		}

		SessionDAO.delete(currentSessionUser);
		loginDAO.delete(logInData.get());
		
		return "Logged Out Successfully....";
	}
	

}
