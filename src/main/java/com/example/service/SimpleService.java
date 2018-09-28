package com.example.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.example.domain.User;
import com.example.domain.User.Role;

@Service
public class SimpleService {
	private Set<String> tokens;
	private Map<String, User> users;

	public SimpleService() {
		users = new HashMap<String, User>();
		tokens = new HashSet<String>();

		users.put("admin", new User("admin", DigestUtils.sha256Hex("adminpass"), Role.ADMIN));
	}

	public boolean checkToken(String token) {
		return tokens.contains(token);
	}

	public boolean checkUser(String username, String password) {
		String hexPassword = DigestUtils.sha256Hex(password);
		User currentUser;
		if ((currentUser = users.get(username)) != null) {
			return currentUser.getPassword().equals(hexPassword);
		}
		return registerUser(username, hexPassword);
	}

	public boolean registerUser(String username, String hexPassword) {
		users.put(username, new User(username, hexPassword, Role.USER));
		return true;
	}

	public boolean checkAdmin(String username, String password) {
		String hexPassword = DigestUtils.sha256Hex(password);
		User currentUser = users.get(username);
		if (currentUser != null) {
			return currentUser.getPassword().equals(hexPassword) && currentUser.getRole().equals(Role.ADMIN);
		}
		return false;
	}

	public String generateToken(String username, String password) {
		String token = DigestUtils.sha256Hex(DigestUtils.sha256Hex(username) + DigestUtils.sha256Hex(password));
		tokens.add(token);
		return token;
	}
}
