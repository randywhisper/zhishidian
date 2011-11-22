package org.flacro;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

public class SessionFilter implements Filter {
	static Logger log = Logger.getLogger(SessionFilter.class);
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession();
		Object login = session.getAttribute("username");
		
		log.debug("principal:"+request.getUserPrincipal().getName());
		log.debug("get request");
		Enumeration<String> es = request.getAttributeNames();
		while(es.hasMoreElements()){
			String s = es.nextElement();
			log.debug("header:"+s);
			log.debug(request.getHeader(s));
		}
		log.debug("param:"+request.getParameterMap());
		log.debug("cookies"+request.getCookies());
		for(Cookie c : request.getCookies()){

			log.debug(c.getName()+":"+c.getValue());
		}
		log.debug("authtype:"+request.getAuthType());
		chain.doFilter(req, resp);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
