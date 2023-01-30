package com.masai.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Wallet {
   
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer walletId;
	
	private Double balance;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Customer customer;
	
	@OneToMany
	@JsonIgnore
	private List<Transaction> transaction;
	
	@OneToMany
	@JsonIgnore
	private List<BillPayment> billpayments;
	
	@OneToMany
	@JsonIgnore
	private List<BeneficiaryDetail> beneficiaryDetails;

	public Wallet() {
		super();
	}

	public Wallet(Integer walletId, Double balance, Customer customer, List<Transaction> transaction,
			List<BillPayment> billpayments, List<BeneficiaryDetail> beneficiaryDetails) {
		super();
		this.walletId = walletId;
		this.balance = balance;
		this.customer = customer;
		this.transaction = transaction;
		this.billpayments = billpayments;
		this.beneficiaryDetails = beneficiaryDetails;
	}

	public Integer getWalletId() {
		return walletId;
	}

	public void setWalletId(Integer walletId) {
		this.walletId = walletId;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Transaction> getTransaction() {
		return transaction;
	}

	public void setTransaction(List<Transaction> transaction) {
		this.transaction = transaction;
	}

	public List<BillPayment> getBillpayments() {
		return billpayments;
	}

	public void setBillpayments(List<BillPayment> billpayments) {
		this.billpayments = billpayments;
	}

	public List<BeneficiaryDetail> getBeneficiaryDetails() {
		return beneficiaryDetails;
	}

	public void setBeneficiaryDetails(List<BeneficiaryDetail> beneficiaryDetails) {
		this.beneficiaryDetails = beneficiaryDetails;
	}
	
	
	
}
