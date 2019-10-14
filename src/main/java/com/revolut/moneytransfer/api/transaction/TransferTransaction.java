package com.revolut.moneytransfer.api.transaction;

import java.math.BigDecimal;

public class TransferTransaction {

	private Long source;
	private Long destination;
	private BigDecimal amount;

	public Long getSource() {
		return source;
	}

	public void setSource(Long source) {
		this.source = source;
	}

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
