package com.pallot.cms.conf;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.filter.OncePerRequestFilter;

public class ApplicationContextCMSExceptionFilter extends OncePerRequestFilter {
	private static final Logger logger = LogManager.getLogger(ApplicationContextCMSExceptionFilter.class.getName());
	public ApplicationContext applicationContext;

	public ApplicationContextCMSExceptionFilter(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (Exception ex) {
			logger.error("Message from filter", ex);
			request.setAttribute("errorMessage", ex.getMessage());
			request.getRequestDispatcher("/product/info/error").forward(request, response);
		}
	}
}
