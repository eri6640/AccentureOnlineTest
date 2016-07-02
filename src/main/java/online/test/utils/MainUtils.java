package online.test.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainUtils {
	
	public String TokenName = "XSRF-TOKEN";

	/**
	 * isAjax  --> checks request....
	 * 
	 * @param request http servlet request
	 * @return True if is ajax, False if isnt ajax
	 * */
	public boolean isAjax( HttpServletRequest request ){
		return "XMLHttpRequest".equals( request.getHeader("X-Requested-With")) ? true : false;
	}
	
	
	public String MD5( String string ){
		MessageDigest md5_password = null;
		try {
			md5_password = MessageDigest.getInstance("MD5");
		} catch ( NoSuchAlgorithmException error ) {
			error.printStackTrace();
			showThis( "MD5 error" );
			return null;
		}
		
		md5_password.update( string.getBytes() );
		return string;
	}
	
	
	public void showThis( String string ){
		System.out.println( string );
	}
	
	public String getIp( HttpServletRequest request ){
		return request.getHeader( "X-FORWARDED-FOR" ) == null ? request.getRemoteAddr() : null;
	}
	
	public HttpServletResponse removeToken( HttpServletResponse response ){
		
		Cookie cookie = new Cookie( TokenName, null );
		cookie.setPath( "/" );
		cookie.setHttpOnly( true );
		cookie.setMaxAge( -3600 );
		response.addCookie( cookie );
		
		return response;
	}
	
	public HttpServletResponse redirect( HttpServletResponse response, String url ){
		response.setHeader( "Location", url );
		return response;
	}
	
	public boolean isResource( String page ){
		
		ArrayList<String> resources = new ArrayList<String>();
		
		resources.add( "/css/" );
		resources.add( "/fonts/" );
		resources.add( "/img/" );
		resources.add( "/js/" );
		
		for( String string : resources ){
			if( page.startsWith( string ) ) return true;
		}
		
		return false;
	}

}
