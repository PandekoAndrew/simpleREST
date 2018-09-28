package com.example.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.exception.ForbiddenException;
import com.example.service.SimpleService;

@RestController
public class SimpleController {

	private static final String SAMPLE_TEXT = "sample text";
	private final SimpleService service;

	@Autowired
	public SimpleController(SimpleService service) {
		this.service = service;
	}

	@RequestMapping("/free")
	public String free() {
		return SAMPLE_TEXT;
	}

	@RequestMapping("/user")
	public String user(@RequestParam(value = "username", defaultValue = "") String username,
			@RequestParam(value = "password", defaultValue = "") String password,
			@CookieValue(value = "foo", defaultValue = "") String token, HttpServletResponse response) {

		if (!username.equals("") && !password.equals("")) {
			if (service.checkUser(username, password)) {
				response.addCookie(new Cookie("foo", service.generateToken(username, password)));
				return SAMPLE_TEXT;
			}
			throw new ForbiddenException();
		}
		if (service.checkToken(token)) {
			return SAMPLE_TEXT;
		}
		throw new ForbiddenException();
	}

	@RequestMapping("/admin")
	public String admin(@RequestParam(value = "username", defaultValue = "") String username,
			@RequestParam(value = "password", defaultValue = "") String password) {
		if (service.checkAdmin(username, password)) {
			return SAMPLE_TEXT;
		}

		throw new ForbiddenException();
	}
}
