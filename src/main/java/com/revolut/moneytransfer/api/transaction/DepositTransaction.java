package com.revolut.moneytransfer.api.transaction;

import java.math.BigDecimal;

public class DepositTransaction {

	private Long destination;
	private BigDecimal amount;

	public Long getDestination() {
		return destination;
	}

	public void setDestination(Long destination) {
		this.destination = destination;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
