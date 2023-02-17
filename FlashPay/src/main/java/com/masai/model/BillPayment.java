package com.masai.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;


@Entity
@Getter@Setter@ToString
@NoArgsConstructor@AllArgsConstructor
public class BillPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer billId;
	
	private BillType billtype;
	
	private TransactionType transactionType;

	private CurrencyType currencyType;

	private String accountNumber;
	
	@DecimalMin(value = "0.1", inclusive = true)
	private Double amount;
	
	private LocalDate time;
	
	private Integer walletId;

}
