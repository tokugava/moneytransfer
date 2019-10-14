package com.revolut.moneytransfer.dao;

import java.util.List;
import java.util.Optional;

import com.revolut.moneytransfer.api.transaction.Transaction;

public interface ITransactionDao {

	Optional<Transaction> findById(Long id);
	
	void insert(Transaction trx);
	
	List<Transaction> getAll();
	
	List<Transaction> getTransactionsByAccount(Long accountId);
	
	long generateId();
}