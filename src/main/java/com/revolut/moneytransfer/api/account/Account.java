package com.revolut.moneytransfer.api.account;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6475695361379434650L;
	
	private Long id;
	private String name;
	private BigDecimal balance;
	
	public Account() {
		// for jackson initializaion
	}
	
	public Account(Long id, String name, BigDecimal balance) {
		this.id = id;
		this.name = name;
		this.balance = balance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}
