package com.example.filter;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class SimpleInterceptor extends HandlerInterceptorAdapter {

	private static final Logger log = LoggerFactory.getLogger(SimpleInterceptor.class);
	private StopWatch stopWatch;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		stopWatch = new StopWatch(request.getRequestURI());
		stopWatch.start();
		return true;
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		stopWatch.stop();
		String uri = request.getRequestURI();
		StringBuilder stringBuilder = new StringBuilder();
		if (uri.equals("/free") || uri.equals("/user")) {
			stringBuilder.append("ID: ").append(UUID.randomUUID().toString()).append(" ");
		}
		stringBuilder.append(stopWatch.shortSummary());
		log.info(stringBuilder.toString());
	}
}
