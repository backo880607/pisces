package com.pisces.platform.user.service.impl;

import com.pisces.platform.core.exception.ExistedException;
import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.user.bean.Account;
import com.pisces.platform.user.bean.Role;
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
        String password;
        List<String> roles = new ArrayList<>();
        if (env.equals("dev")) {
        	password = passwordEncoder.encode("123456");
        	roles.add("root");
        } else {
        	Example example = new Example(Account.class);
        	example.createCriteria().andEqualTo("username", username);
        	Account account = getDao().selectOneByExample(example);
        	if (account == null) {
        		if (username.equals("root")) {
					password = passwordEncoder.encode("123456");
				} else {
					throw new UsernameNotFoundException(username);
				}
        	} else {
				password = account.getPassword();
			}

			if (username.equals("root")) {
				roles.add("root");
			} else {
				for (Role role : account.getRoles()) {
					roles.add(role.getCode());
				}
			}
        }
        
		List<SimpleGrantedAuthority> authList = getAuthorities(roles);
		User user = new User(username, password, authList);
		return user;
	}

	private List<SimpleGrantedAuthority> getAuthorities(List<String> roles) {
		List<SimpleGrantedAuthority> authList = new ArrayList<>();
		for (String role : roles) {
			authList.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
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