package com.pisces.platform.web;

import org.junit.Test;

import com.pisces.platform.PiscesBaseTest;
import com.pisces.user.bean.Account;

public class CrudTest extends PiscesBaseTest {
	
	//@Test
	public void getTest() {
		httpGet("/user/Account/getAll");
	}
	
	@Test
	public void addAccount() {
		Account account = new Account();
		account.setUsername("chenting");
		account.setPassword("iloveyou");
		httpPost("/user/Account/insert", account);
	}
}
