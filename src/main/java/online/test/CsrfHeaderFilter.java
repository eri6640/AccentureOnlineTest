package online.test;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.web.util.WebUtils;

import online.test.utils.MainUtils;

public class CsrfHeaderFilter extends OncePerRequestFilter {
	
	MainUtils utils = new MainUtils();
	
	@Override
	protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain ) throws ServletException, IOException {
		
		CsrfToken csrf = (CsrfToken) request.getAttribute( CsrfToken.class .getName() );
		if( csrf != null ){
			
			Cookie cookie = WebUtils.getCookie( request, utils.TokenName );
			String token = csrf.getToken();
			if ( cookie == null || token != null && ! token.equals( cookie.getValue() )) {
				cookie = new Cookie( utils.TokenName, token );
				cookie.setPath("/");
				response.addCookie(cookie);				
			}
			
		}
		
		/*if( ! utils.isResource( request.getRequestURI() ) && ! request.getRequestURI().equals( "/login.html" ) ){
			utils.redirect( response, "http://5.196.149.161:8080/#/login" );
		}*/
		

		utils.showThis(  new UrlPathHelper().getOriginatingRequestUri(request) );
		
		//utils.showThis( utils.getIp( request ) );
		/*if( utils.isAjax( request ) ){
			utils.showThis(  new UrlPathHelper().getOriginatingRequestUri(request) );
			
			String url = new UrlPathHelper().getOriginatingRequestUri( request );
			
			if( url.contains( ".html" ) ){
				if( ! url.contains( "/login.html" ) ){
					utils.showThis( "true" );
					utils.showThis( "true" );
					utils.showThis( "true" );
					//utils.redirect( response, "http://5.196.149.161:8080/" );
					//response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
					//response.setHeader( "Location", "http://5.196.149.161:8080/" );
				}
			}
		}*/
		
		//utils.removeToken( response );
		//utils.redirect( response, "https://www.google.lv/" );
		
		filterChain.doFilter( request, response );
		
	}
	

}