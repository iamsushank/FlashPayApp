package com.masai.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter@Setter@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CurrentSessionUser {
	
	@Id
	private Integer id;
	
	@Column(unique =  true)
	private Integer userId;
	
	private String uuid;
	
	private String mobileNo;
	
	private LocalDate localDate;

	public CurrentSessionUser(Integer userId, String uuid, String mobileNo, LocalDate localDate) {
		this.userId = userId;
		this.uuid = uuid;
		this.mobileNo = mobileNo;
		this.localDate = localDate;
	}
}
