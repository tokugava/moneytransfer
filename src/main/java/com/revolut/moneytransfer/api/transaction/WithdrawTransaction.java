package com.revolut.moneytransfer.api.transaction;

import java.math.BigDecimal;

public class WithdrawTransaction {

	private Long source;
	private BigDecimal amount;

	public Long getSource() {
		return source;
	}

	public void setSource(Long source) {
		this.source = source;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
