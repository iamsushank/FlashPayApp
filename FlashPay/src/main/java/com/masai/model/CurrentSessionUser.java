package com.masai.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter@Setter@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CurrentSessionUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(unique =  true)
	private Integer userId;
	
	private String uuid;
	
	private String mobileNo;
	
	private LocalDateTime localDateTime;

	public CurrentSessionUser(Integer userId, String uuid, String mobileNo, LocalDateTime localDateTime) {
		this.userId = userId;
		this.uuid = uuid;
		this.mobileNo = mobileNo;
		this.localDateTime = localDateTime;
	}
}
