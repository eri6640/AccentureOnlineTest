package online.test.utils.interf;

import javax.servlet.http.HttpServletRequest;

public interface LoginUtilsInterf {

	public boolean isAdmin( HttpServletRequest request );
	
	public boolean isLoggedIn( HttpServletRequest request );
	
	public boolean doLogin( String form_email, String form_password, HttpServletRequest request );	
	
	public boolean logout( HttpServletRequest request );
}