package com.cbox.base.processor.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务端解决js跨域方案.
 * js跨域请求时，会预先发送OPTIONS方式的预请求，只有在预请求通过后才会发起真正的请求.
 * 所以Access-Control-Allow-Methods中需要增加OPTIONS的请求方式.
 * @author cbox
 */
public class CORSFilter implements Filter {

	private static Logger log = LoggerFactory.getLogger(CORSFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("处理跨域过滤器器");
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		String method = request.getMethod();
		String origin = request.getHeader("Origin");
		
		if (origin != null) {
		    if(StringUtils.isBlank(response.getHeader("Access-Control-Allow-Origin"))){
		        response.addHeader("Access-Control-Allow-Origin", origin);
		    }
		}
		if(StringUtils.isBlank(response.getHeader("Access-Control-Allow-Headers"))){
		    response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Client-Type, Authorization, Access-Control-Allow-Url");
		}
		if(StringUtils.isBlank(response.getHeader("Access-Control-Allow-Methods"))){
		    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		}
		if(StringUtils.isBlank(response.getHeader("Access-Control-Allow-Credentials"))){
		    response.addHeader("Access-Control-Allow-Credentials", "true");
		}
		if ("OPTIONS".endsWith(method)) {
			response.getWriter().write("");
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		log.info("跨域过滤器销毁");
	}

}