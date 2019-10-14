package com.revolut.moneytransfer.resources;

import java.math.BigDecimal;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.revolut.moneytransfer.api.account.Account;
import com.revolut.moneytransfer.api.account.CreateAccount;
import com.revolut.moneytransfer.api.account.UpdateAccount;
import com.revolut.moneytransfer.dao.IAccountDao;

@Path("/account")
public class AccountResource {

	private final IAccountDao accountDao;
	
	@Inject
	public AccountResource(IAccountDao dao) {
		this.accountDao = dao;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
		return Response.ok(accountDao.getAll()).build();
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
		Optional<Account> account = accountDao.findById(parsedId);
		if (!account.isPresent()) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(account.get()).build();
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response create(CreateAccount createAccount) {
		long id = accountDao.generateId();
		Account newAccount = new Account(id, createAccount.getName(), BigDecimal.ZERO);
		accountDao.insert(newAccount);
		return Response.ok(String.format("Account created with id:%d", id)).build();
	}
	
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("{id}")
	public Response update(UpdateAccount updatingAccount) {
		Optional<Account> account = accountDao.findById(updatingAccount.getId());
		if (!account.isPresent()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Account accountToSave = account.get();
		accountToSave.setName(updatingAccount.getName());
		return Response.ok(String.format("Account updated with id:%d", accountToSave.getId())).build();
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") String id) {
		long idToDelete;
		try {
			idToDelete = Long.parseLong(id);
		}
		catch (NumberFormatException e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		accountDao.delete(idToDelete);
		return Response.ok(String.format("Account deleted with id:%d", id)).build();
	}
}
