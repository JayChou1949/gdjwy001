package com.upd.hwcloud.common.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 一次请求只通过一次filter
 * @author zmcs81
 *
 */
public class HttpRequestSwitchFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpRequestWrapper httpRequestWrapper = new HttpRequestWrapper(request);
		super.doFilter(httpRequestWrapper, response, filterChain);
	}
}
