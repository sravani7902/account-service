package com.acc.accountservice.account_service.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Account {
	

	@Id
	private Long accNum;
	private String name;
	private String accType;
	private Double balance;
	private String status;
	

	public Account(Long accNum, String name, String accType, double balance, String status) {
	    this.accNum = accNum;
	    this.name = name;
	    this.accType = accType;
	    this.balance = balance;
	    this.status = status;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getAccNum() {
		return accNum;
	}
	public void setAccNum(Long accNum) {
		this.accNum = accNum;
	}
	
}
