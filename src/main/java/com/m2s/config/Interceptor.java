package com.m2s.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;

@Transactional
@Component
public class Interceptor implements HandlerInterceptor {

	private final static Logger logger = LoggerFactory.getLogger(Interceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String tokenIdWithRole = request.getHeader("Authorization");
		String requestUrl = request.getRequestURI();

		logger.info("tokenId from request header : " + tokenIdWithRole);
		logger.info("requestUrl : " + requestUrl);

		String methodType = request.getMethod();
		if (methodType.equalsIgnoreCase("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);
			return true;
		}

		response.setStatus(HttpServletResponse.SC_OK);
		if (requestUrl.contains("/login")) {
			return true;
		}

		if (tokenIdWithRole == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			logger.info("request without authentication not allowed");
			return false;
		}

		return true;
	}

}
