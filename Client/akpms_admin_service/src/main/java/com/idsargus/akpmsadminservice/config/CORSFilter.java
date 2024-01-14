package com.idsargus.akpmsadminservice.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class CORSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;

		log.debug("Http Method: {}, Locale: {}, Server: {}", request.getMethod(), request.getLocale(),
				request.getServerName());

		HttpServletResponse response = (HttpServletResponse) res;
		//TODO header should be less otherwise it will throw 500
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, PATCH");
		response.setHeader("Access-Control-Max-Age", "3500");
		response.setHeader("Access-Control-Allow-Headers",
				"Access-Control-Allow-Headers, ,X-Access-Token,XKey,Authorization");
		response.setHeader("Access-Control-Allow-Headers",
				"Access-Control-Allow-Headers,Accept, X-Requested-With, Content-Type, Access-Control-Request-Headers,X-Access-Token,XKey,Authorization");

		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
