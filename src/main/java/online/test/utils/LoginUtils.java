package online.test.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import online.test.models.User;
import online.test.models.dao.UserDao;

@Component
public class LoginUtils {
	
	MainUtils utils = new MainUtils();

	public String TokenName = "XSRF-TOKEN";
	
	public boolean isLoggedIn( HttpServletRequest request ){
		
		CsrfToken csrf = (CsrfToken) request.getAttribute( CsrfToken.class .getName() );
		if( csrf == null ) return false;
		
		Cookie cookie = WebUtils.getCookie( request, TokenName );
		String token = csrf.getToken();
		
		//if something is wrong with token or cookie, redirect to login page
		if ( cookie == null || token != null && ! token.equals( cookie.getValue() ) ) return false;
		
		String token_hash = utils.MD5( token );
		if( token_hash.isEmpty() ) return false;
		
		User token_user = null;
		
		try{
			token_user = userDao.findByToken( token_hash );
		}
		catch ( Exception error ){
			token_user = null;
			utils.showThis( "user null" );
		}
		
		if( token_user == null ) return false;
		/**/
		if( token_user.getIp() == null || ! token_user.getIp().equals( utils.getIp( request ) ) ){
			token_user.unSetAuth();
			userDao.save( token_user );
			utils.showThis( "wrong ip" );
			return false;
		}
		
		if( ! token_hash.equals( token_user.getToken() ) ){
			token_user.unSetAuth();
			userDao.save( token_user );
			utils.showThis( "wrong token" );
			return false;
		}
		
		//if last activity was about 24 hours back... reset token and ip ... return false
		if( ( System.currentTimeMillis() - token_user.getActivity() ) / 1000 >= 60 * 60 * 24 ){
			token_user.unSetAuth();
			return false;
		}
		/**/
		
		token_user.updateActivity();
		userDao.save( token_user );
		
		return true; //is logged in
	}
	
	public boolean doLogin( String form_email, String form_password, HttpServletRequest request ){
		
		CsrfToken csrf = (CsrfToken) request.getAttribute( CsrfToken.class .getName() );
		if( csrf == null ) return false;
		
		Cookie cookie = WebUtils.getCookie( request, TokenName );
		String token = csrf.getToken();
		
		if ( cookie == null || token != null && ! token.equals( cookie.getValue() ) ) return false;
		
		String form_password_hash = utils.MD5( form_password );
		if( form_password_hash.isEmpty() ) return false;

		String token_hash = utils.MD5( token );
		if( token_hash.isEmpty() ) return false;
		
		User form_user = null;
		
		try{
			form_user = userDao.findByEmail( form_email );
		}
		catch ( Exception error ){
			form_user = null;
			utils.showThis( "user null" );
			return false;
		}
		
		if( form_user == null ) return false;
		
		//
		
		if( ! form_user.getPasswordHash().equals( form_password_hash ) ) return false;
		
		form_user.setAuth( utils.getIp( request ), token_hash );
		userDao.save( form_user );

		return true;
		
	}
	
	public boolean logout( HttpServletRequest request ){
		
		CsrfToken csrf = (CsrfToken) request.getAttribute( CsrfToken.class .getName() );
		if( csrf == null ) return false;
		
		Cookie cookie = WebUtils.getCookie( request, TokenName );
		String token = csrf.getToken();
		
		if ( cookie == null || token != null && ! token.equals( cookie.getValue() ) ) return false;
		
		String token_hash = utils.MD5( token );
		if( token_hash.isEmpty() ) return false;
		
		User token_user = null;
		
		try{
			token_user = userDao.findByToken( token_hash );
		}
		catch ( Exception error ){
			token_user = null;
			utils.showThis( "user null" );
			return false;
		}
		
		if( token_user == null ) return false;
		
		token_user.unSetAuth();
		token_user.updateActivity();
		userDao.save( token_user );
		
		return true;
	}

	/**
	 * 
	 *  UserDao
	 * 
	 */
	
	@Autowired
	private UserDao userDao;

}
