package com.revolut.moneytransfer.resources;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.revolut.moneytransfer.api.account.Account;
import com.revolut.moneytransfer.api.transaction.DepositTransaction;
import com.revolut.moneytransfer.api.transaction.Transaction;
import com.revolut.moneytransfer.api.transaction.TransferTransaction;
import com.revolut.moneytransfer.api.transaction.WithdrawTransaction;
import com.revolut.moneytransfer.dao.IAccountDao;
import com.revolut.moneytransfer.dao.ITransactionDao;

@Path("/transaction")
public class TransactionResource {

	private final ITransactionDao trxDao;
	private final IAccountDao accountDao;

	@Inject
	public TransactionResource(ITransactionDao trxDao, IAccountDao accountDao) {
		this.trxDao = trxDao;
		this.accountDao = accountDao;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
		return Response.ok(trxDao.getAll()).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response get(@PathParam("id") String id) {
		Long parsedId;
		try {
			parsedId = Long.parseLong(id);
		} catch (NumberFormatException exc) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Optional<Transaction> trx = trxDao.findById(parsedId);
		if (!trx.isPresent()) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(trx.get()).build();
	}
	


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("account/{id}")
	public Response getByAccount(@PathParam("id") String id) {
		Long parsedId;
		try {
			parsedId = Long.parseLong(id);
		} catch (NumberFormatException exc) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.ok(trxDao.getTransactionsByAccount(parsedId)).build();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("transfer")
	public Response transfer(TransferTransaction transfer) {
		Optional<Account> sourceAccountOpt = accountDao.findById(transfer.getSource());
		if (!sourceAccountOpt.isPresent()) {
			return Response.status(Status.NOT_FOUND).build();
		}
		Account sourceAccount = sourceAccountOpt.get();
		synchronized (sourceAccount) {
			if (sourceAccount.getBalance().compareTo(transfer.getAmount()) < 0) {
				return Response.status(Status.BAD_REQUEST).entity("Balance error").build();
			}
			Optional<Account> destinationAccountOpt = accountDao.findById(transfer.getDestination());
			if (!destinationAccountOpt.isPresent()) {
				return Response.status(Status.NOT_FOUND).build();
			}
			sourceAccount.setBalance(sourceAccount.getBalance().subtract(transfer.getAmount()));
			Account destinationAccount = destinationAccountOpt.get();
			synchronized (destinationAccount) {
				destinationAccount.setBalance(destinationAccount.getBalance().add(transfer.getAmount()));
			}
		}
		long id = trxDao.generateId();
		Transaction trx = new Transaction(id, transfer.getSource(), transfer.getDestination(), transfer.getAmount());
		trxDao.insert(trx);
		return Response.ok(String.format("Transaction created with id:%d", id)).build();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("deposit")
	public Response deposit(DepositTransaction deposit) {
		Optional<Account> destinationAccountOpt = accountDao.findById(deposit.getDestination());
		if (!destinationAccountOpt.isPresent()) {
			return Response.status(Status.NOT_FOUND).build();
		}
		Account destinationAccount = destinationAccountOpt.get();
		synchronized (destinationAccount) {
			destinationAccount.setBalance(destinationAccount.getBalance().add(deposit.getAmount()));
		}
		
		long id = trxDao.generateId();
		Transaction trx = new Transaction(id, null, deposit.getDestination(), deposit.getAmount());
		trxDao.insert(trx);
		return Response.ok(String.format("Transaction created with id:%d", id)).build();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("withdraw")
	public Response withdraw(WithdrawTransaction withdraw) {
		Optional<Account> sourceAccountOpt = accountDao.findById(withdraw.getSource());
		if (!sourceAccountOpt.isPresent()) {
			return Response.status(Status.NOT_FOUND).build();
		}
		Account sourceAccount = sourceAccountOpt.get();
		synchronized (sourceAccount) {
			if (sourceAccount.getBalance().compareTo(withdraw.getAmount()) < 0) {
				return Response.status(Status.BAD_REQUEST).entity("Balance error").build();
			}
			sourceAccount.setBalance(sourceAccount.getBalance().subtract(withdraw.getAmount()));
		}
		
		long id = trxDao.generateId();
		Transaction trx = new Transaction(id, withdraw.getSource(), null, withdraw.getAmount());
		trxDao.insert(trx);
		return Response.ok(String.format("Transaction created with id:%d", id)).build();
	}
}
