package online.test.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.util.WebUtils;

import online.test.models.User;
import online.test.models.dao.UserDao;

public class LoginUtils {
	
	MainUtils utils = new MainUtils();
	
	public boolean isLoggedIn( HttpServletRequest request ){
		
		CsrfToken csrf = (CsrfToken) request.getAttribute( CsrfToken.class .getName() );

		if( csrf == null ) return false;
		
		Cookie cookie = WebUtils.getCookie( request, utils.TokenName );
		String token = csrf.getToken();
		if ( cookie == null || token != null && ! token.equals( cookie.getValue() )) {
			return false;
		}		
		
		return true;
	}
	
	/**
	 * checkPassword  --> checks password....
	 * 
	 * @param email
	 * @return password
	 * */
	public boolean checkPassword( String check_email, String check_password ){
		
		User user_user = null;
		
		if( ( user_user = userDao.findByEmail( check_email ) ) == null ) return false;
		
		String user_password_hash = user_user.getPasswordHash(); //hash
		String check_password_hash = utils.MD5( check_password ); //hash
		
		if( user_password_hash.isEmpty() || check_password_hash.isEmpty() ) return false;
		
		return user_password_hash.equals( check_password_hash ) ? true : false;
		
	}
	
	/**
	 * isLogged  --> checks if user is logged in....
	 * 
	 * @param token token from cookie
	 * @return ip client ip address
	 * */
	public boolean isLogged( String token, String ip ){
		
		String token_hash = utils.MD5( token );
		
		User user_user = null;
		if( ( user_user = userDao.findByToken( token_hash ) ) == null ) return false;
		
		if( ! user_user.getToken().equals( token ) || ! user_user.getIp().equals( ip ) ){
			user_user.removeToken();
			return false;
		}
		
		user_user.updateActivity();
		
		return true;
	}
	
	public void logout( User user_user ){
		
		user_user.removeToken();

	}
	
	
	
	
	
	// ------------------------
	// PRIVATE FIELDS
	// ------------------------

	@Autowired
	private UserDao userDao;

}