package com.masai.model;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Entity
@Getter@Setter@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;

	@Size(min=3,max=20)
	@Pattern(regexp="[a-zA-Z]{3,20}", message = "Name must not contains any numbers and Special Character")
	private String name;

	@NotNull
	@Size(min=3,max=20)
	private String userName;

	@NotNull
//	@Pattern(regexp="^(1[8-9]|[2-9][0-9]|100)$", message = "Age must be between 18 to 100")
	private Integer age;

	@NotNull
	@Size(min=10,max=10)
	@Pattern(regexp="[6-9]{1}[0-9]{9}", message = "Mobile number must have 10 digits mobile Number")
	private String mobileNo;

	@NotNull
	@Pattern(regexp="[a-zA-Z]{3,20}", message = "Name must not contains any numbers and Special Character")
	private String city;

	@Email
	@NotNull
	private String email;

	@NotNull
	@Size(min=6,max=20)
	private String password;

	@JsonIgnore
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	private Wallet wallet;

}
