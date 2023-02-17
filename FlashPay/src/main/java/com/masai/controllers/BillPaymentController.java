package com.masai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.InsufficientBalanceException;
import com.masai.exception.UserNotLoggedInException;
import com.masai.model.BillPayment;
import com.masai.service.BillPaymentService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.util.*;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/billPayment")
public class BillPaymentController {
	
	@Autowired
	private BillPaymentService billPaymentService;
	
	@PostMapping("/{uniqueId}")
	public ResponseEntity<BillPayment> addNewBillPaymentDetails(@RequestBody BillPayment billPayment, @PathVariable String uniqueId) throws UserNotLoggedInException, InsufficientBalanceException {
		BillPayment addBill =  billPaymentService.billPaymentUsingWallet(billPayment, uniqueId);
		return new ResponseEntity<> (addBill, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Set<BillPayment>> viewAllBillPayments(@PathVariable("id") String uniqueId) throws UserNotLoggedInException {
		Set<BillPayment> billPayments = billPaymentService.viewBillPayments(uniqueId);
		return new ResponseEntity<>(billPayments,HttpStatus.ACCEPTED);
	}
	
}
