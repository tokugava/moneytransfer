package com.revolut.moneytransfer.api.account;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UpdateAccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9107818728936174502L;
	private Long id;
	private String name;

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

}
