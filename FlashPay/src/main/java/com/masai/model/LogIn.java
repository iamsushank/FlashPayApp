package com.masai.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class LogIn {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;
	
	
	@NotNull
	@Size(min=10,max=10)
	@Pattern(regexp="[6-9]{1}[0-9]{9}", message = "Mobile number must have 10 digits mobile Number")
	private String mobileNo;
	
	
	@NotNull
	//password must be between 8 to 20 characters which contain at least one lowercase letter, one uppercase letter, can have digits and can have any of these characters (@, #, $, %, &, !, ^, *, ~, _, -)
	@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%&!^*~_-]).{8,20}$", message = "Password must be between 8 to 20 characters which contain at least one lowercase letter, one uppercase letter, can have digits and can have any of these characters (@, #, $, %, &, !, ^, *, ~, _, -)")
	private String password;

	public LogIn(Integer userId, String mobileNo, String password) {
		super();
		this.userId = userId;
		this.mobileNo = mobileNo;
		this.password = password;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public LogIn() {
		super();

	}
	
	
}
