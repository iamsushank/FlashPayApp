package com.masai.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter@Setter@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaryDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer beneficiaryId;
	
	@NotBlank
	@NotNull
	@Size(min = 4,max = 25 ,message = "Name length minimum 4 to 25")
	private String beneficiaryName;
	
	@NotBlank
	@NotNull
	@Size(min = 10,max = 10 ,message = "mobile number must be in 10 digits!")
	private String beneficiaryMobileNo;
	
	private Integer walletId;


}
