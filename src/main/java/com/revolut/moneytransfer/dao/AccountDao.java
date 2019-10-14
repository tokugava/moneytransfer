package com.revolut.moneytransfer.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.revolut.moneytransfer.api.account.Account;

public class AccountDao implements IAccountDao {

	ArrayList<Account> accountsList;
	AtomicLong counter;

	public AccountDao() {
		accountsList = new ArrayList<Account>();
		counter = new AtomicLong();
	}

	public Optional<Account> findById(Long id) {
		List<Account> accounts = accountsList.stream().filter(a -> a.getId().equals(id))
				.collect(Collectors.toList());
		if (accounts.size() != 1) {
			return Optional.empty();
		}
		return Optional.ofNullable(accounts.get(0));
	}

	public void insert(Account account) {
		synchronized (accountsList) {
			accountsList.add(account);
		}
	}

	public void delete(Long id) {
		Optional<Account> accountToDelete = findById(id);
		if (accountToDelete.isPresent()) {
			synchronized (accountsList) {
				accountsList.remove(accountToDelete.get());
			}
		}
	}

	public List<Account> getAll() {
		return accountsList;
	}

	@Override
	public long generateId() {
		return counter.incrementAndGet();
	}

}
