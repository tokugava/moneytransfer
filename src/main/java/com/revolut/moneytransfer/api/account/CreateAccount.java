package com.revolut.moneytransfer.api.account;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreateAccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9107818728936174502L;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
