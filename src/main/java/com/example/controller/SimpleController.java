package com.example.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {
	
	private static final String SAMPLE_TEXT = "sample text";
	
	@RequestMapping("/free")
    public String free() {
        return SAMPLE_TEXT;
    }
}
