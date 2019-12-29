package com.pisces.platform.web.token;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserRepository {

    private static final Map<String, User> userMap = new ConcurrentHashMap<String, User>();

    public User findByUsername(final String username) {
        return userMap.get(username);
    }

    public User insert(User user) {
        userMap.put(user.getUsername(), user);
        return user;
    }

    public void remove(String username) {
        userMap.remove(username);
    }
}
