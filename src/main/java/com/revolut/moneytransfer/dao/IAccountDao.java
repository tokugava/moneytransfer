package com.revolut.moneytransfer.dao;

import java.util.List;
import java.util.Optional;

import com.revolut.moneytransfer.api.account.Account;

public interface IAccountDao {

	Optional<Account> findById(Long id);
	
	void insert(Account account);
	
	void delete(Long id);
	
	List<Account> getAll();
	
	long generateId();
}
