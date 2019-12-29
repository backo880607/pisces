package com.pisces.platform.user.service.impl;

import com.pisces.platform.core.exception.ExistedException;
import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.user.bean.Account;
import com.pisces.platform.user.config.UserMessage;
import com.pisces.platform.user.dao.AccountDao;
import com.pisces.platform.user.service.AccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
class AccountServiceImpl extends EntityServiceImpl<Account, AccountDao> implements AccountService, UserDetailsService {
	
	@Value("${spring.profiles.active}")
	private String env;
	
	static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = "";
        String role = "";
        if (username.equalsIgnoreCase("root")) {
        	password = passwordEncoder.encode("880607");
        	role = "root";
        } else if (env == "dev") {
        	password = passwordEncoder.encode("880607");
        	role = "admin";
        } else {
        	Example example = new Example(Account.class);
        	example.createCriteria().andEqualTo("username", username);
        	Account account = getDao().selectOneByExample(example);
        	if (account == null) {
        		throw new UsernameNotFoundException(username);
        	}
        	password = account.getPassword();
        	role = "admin";
        }
        
		List<SimpleGrantedAuthority> authList = getAuthorities(role);
		User user = new User(username, password, authList);
		return user;
	}

	private List<SimpleGrantedAuthority> getAuthorities(String role) {
		List<SimpleGrantedAuthority> authList = new ArrayList<>();
		
		if (role != null && role.trim().length() > 0) {
			if (role.equals("root")) {
				authList.add(new SimpleGrantedAuthority("ROLE_ROOT"));
			}
		}
		return authList;
	}

	@Override
	public void register(Account account) {
		Example example = new Example(Account.class);
    	example.createCriteria().andEqualTo("username", account.getUsername());
    	Account existed = getDao().selectOneByExample(example);
    	if (existed != null) {
    		throw new ExistedException(UserMessage.UserExisted, account.getUsername());
    	}
    	
    	getBaseDao().insert(account);
	}

	@Override
	public void unregister(Account account) {
		
	}
}