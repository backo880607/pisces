package com.pisces.platform.framework.web;

import com.pisces.platform.framework.PiscesBaseTest;
import com.pisces.platform.user.bean.Account;
import org.junit.Test;

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
