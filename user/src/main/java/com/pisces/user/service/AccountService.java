package com.pisces.user.service;

import com.pisces.core.service.EntityService;
import com.pisces.user.bean.Account;

public interface AccountService extends EntityService<Account> {
	
	void register(Account account);
	void unregister(Account account);
}
