package com.masai.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter@Setter@ToString
@NoArgsConstructor@AllArgsConstructor
public class Transaction {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer transactionId;
    
    private TransactionType transactionType;
    
    @CreatedDate
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate transactionDate;
    
    private double amount;
    
	private String description;
    
    private Integer  walletId;


}
