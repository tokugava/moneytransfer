package com.revolut.moneytransfer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class AccountTest {

	@Before
	public void startup() {
		App.main(null);
		RestAssured.basePath = "http://localhost:8080";
	}
	
	@Test
	public void test() {
		RestAssured.expect().statusCode(200).contentType(ContentType.JSON).when()
        .get("/account");
	}

	@After
	public void teardown() {
		App.finish();
	}
}
