package online.test;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

public class CsrfHeaderFilter extends OncePerRequestFilter {
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		//if( Functions.isAjax( request ) ) return;
		
		CsrfToken csrf = (CsrfToken) request.getAttribute( CsrfToken.class .getName() );
		if( csrf != null ){
			Cookie cookie = WebUtils.getCookie( request, "XSRF-TOKEN" );
			String token = csrf.getToken();
			if ( cookie == null || token != null && ! token.equals( cookie.getValue() )) {
				cookie = new Cookie( "XSRF-TOKEN", token );
				cookie.setPath("/");
				response.addCookie(cookie);
			}

			Cookie cookieId = WebUtils.getCookie( request, "user_id" );
			Cookie cookieHash = WebUtils.getCookie( request, "user_hash" );
			
			if ( cookieId == null || cookieHash == null ) {
				if( cookieId != null ){
					cookieId.setMaxAge( -3600 );
					response.addCookie( cookieId );
				}
				if( cookieHash != null ){
					cookieHash.setMaxAge( -3600 );
					response.addCookie( cookieHash );
				}
			}
			else{
				int user_id = 0;
				try{
					user_id = Integer.parseInt( cookieId.getValue() );
				}
				catch( NumberFormatException error ){
					//logout
				}
				 
				if( user_id <= 0 ){
					//logout
				}
			}
		}
		filterChain.doFilter( request, response );
		
	}
	

}