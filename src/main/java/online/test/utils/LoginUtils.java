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
		if( token_hash == null ) return false;
		
		utils.showThis( token_hash );
		
		User token_user = null;
		
		try{
			token_user = userDao.findByToken( token_hash );
		}
		catch ( Exception error ){
			token_user = null;
			utils.showThis( "user null" );
		}
		
		if( token_user == null ) return false; // <--- null error 
		/**
		if( token_user.getIp() == null || ! token_user.getIp().equals( utils.getIp( request ) ) ){
			token_user.unSetAuth();
			utils.showThis( "wrong ip" );
			return false;
		}
		
		if( ! token_hash.equals( token_user.getToken() ) ){
			token_user.unSetAuth();
			utils.showThis( "wrong token" );
			return false;
		}
		
		//if last activity was about 24 hours back... reset token and ip ... return false
		if( ( System.currentTimeMillis() - token_user.getActivity() ) / 1000 >= 60 * 60 * 24 ){
			//token_user.unSetAuth();
			//return false;
		}
		/**/
		
		return true; //is logged in
	}
	
	/**
	 * checkPassword  --> checks password....
	 * 
	 * @param email
	 * @return password
	 * */
	public boolean checkPassword( String check_email, String check_password ){
		
		User user_user = null;
		
		//if( ( user_user = userDao.findByEmail( check_email ) ) == null ) return false;
		
		String user_password_hash = user_user.getPasswordHash(); //hash
		String check_password_hash = utils.MD5( check_password ); //hash
		
		if( user_password_hash.isEmpty() || check_password_hash.isEmpty() ) return false;
		
		return user_password_hash.equals( check_password_hash ) ? true : false;
		
	}
	
	public void logout( User user_user ){
		user_user.unSetAuth();
		user_user.updateActivity();
	}

	/**
	 * 
	 *  UserDao
	 * 
	 */
	
	@Autowired
	private UserDao userDao;

}
