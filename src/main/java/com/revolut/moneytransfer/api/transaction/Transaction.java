package com.revolut.moneytransfer.api.transaction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;

@XmlRootElement
public class Transaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4613141423788454913L;

	private Long id;
	private Long sourceAccountId;
	private Long destinationAccountId;
	private BigDecimal amount;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date createDate;

	public Transaction() {
		// for Jackson init
	}

	public Transaction(Long id, Long sourceId, Long destinationId, BigDecimal amount) {
		this.id = id;
		this.sourceAccountId = sourceId;
		this.destinationAccountId = destinationId;
		this.amount = amount;
		this.createDate = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSourceAccountId() {
		return sourceAccountId;
	}

	public void setSourceAccountId(Long sourceAccountId) {
		this.sourceAccountId = sourceAccountId;
	}

	public Long getDestinationAccountId() {
		return destinationAccountId;
	}

	public void setDestinationAccountId(Long destinationAccountId) {
		this.destinationAccountId = destinationAccountId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
