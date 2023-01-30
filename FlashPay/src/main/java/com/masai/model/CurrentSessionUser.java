package com.masai.model;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
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
		super();
		this.userId = userId;
		this.uuid = uuid;
		this.mobileNo = mobileNo;
		this.localDateTime = localDateTime;
	}

	public CurrentSessionUser() {
		super();
	}

	public CurrentSessionUser(Integer id, Integer userId, String uuid, String mobileNo, LocalDateTime localDateTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.uuid = uuid;
		this.mobileNo = mobileNo;
		this.localDateTime = localDateTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}
	

}
