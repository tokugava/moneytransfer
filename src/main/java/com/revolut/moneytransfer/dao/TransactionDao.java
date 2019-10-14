package com.revolut.moneytransfer.dao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.revolut.moneytransfer.api.transaction.Transaction;

public class TransactionDao implements ITransactionDao {
	
	ArrayList<Transaction> transactionsList;
	AtomicLong counter;
	
	public TransactionDao() {
		transactionsList = new ArrayList<Transaction>();
		counter = new AtomicLong();
	}

	public Optional<Transaction> findById(Long id) {
		List<Transaction> transactions = transactionsList.stream().filter(a -> a.getId().equals(id))
				.collect(Collectors.toList());
		if (transactions.size() != 1) {
			return Optional.empty();
		}
		return Optional.ofNullable(transactions.get(0));
	}

	public synchronized void insert(Transaction trx) {
		transactionsList.add(trx);
	}

	@Override
	public List<Transaction> getAll() {
		return transactionsList;
	}

	@Override
	public long generateId() {
		return counter.incrementAndGet();
	}

	@Override
	public List<Transaction> getTransactionsByAccount(Long accountId) {
		return transactionsList.stream().filter(t -> accountId.equals(t.getSourceAccountId()) 
				|| accountId.equals(t.getDestinationAccountId()))
				.sorted(Comparator.comparing(Transaction::getCreateDate).reversed())
		.collect(Collectors.toList());
	}

}
