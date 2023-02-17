package com.masai.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
	
	@Id
	private String accountNumber;

	@NotNull
	@Pattern(regexp="[6-9]{1}[0-9]{9}", message = "Mobile number must have 10 digits mobile Number")
	private String mobileNumber;
	
	@NotNull
	@Pattern(regexp="[a-zA-Z0-9]{11}", message = "IFSC Code must have 11 characters")
	private String ifscCode;
	
	@NotNull
	@Pattern(regexp="[a-zA-Z]{2,20}", message = "Name must not contains any numbers and Special Character")
	private String bankName;

	private CurrencyType currencyType;

	@Digits(integer=10, fraction=2)
	@PositiveOrZero
	private double bankBalance;

	private Integer walletId;


}
