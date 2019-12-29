package com.pisces.platform.user.service;

import com.pisces.platform.core.service.EntityService;
import com.pisces.platform.user.bean.Account;

public interface AccountService extends EntityService<Account> {
	
	void register(Account account);
	void unregister(Account account);
}
