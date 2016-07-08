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
public class UserUtils {
	
	MainUtils utils = new MainUtils();
	
	@Autowired
	private UserDao userDao;
	
	public User getUserFromRequest( HttpServletRequest request ){
		
		CsrfToken csrf = (CsrfToken) request.getAttribute( CsrfToken.class .getName() );
		if( csrf == null ) return null;
		
		Cookie cookie = WebUtils.getCookie( request, utils.TokenName );
		String token = csrf.getToken();
		
		//if something is wrong with token or cookie, redirect to login page
		if ( cookie == null || token != null && ! token.equals( cookie.getValue() ) ) return null;
		
		String token_hash = utils.MD5( token );
		if( token_hash.isEmpty() ) return null;
		
		User token_user = null;
		
		try{
			token_user = userDao.findByToken( token_hash );
		}
		catch ( Exception error ){
			token_user = null;
			utils.showThis( "user null" );
		}
		
		return token_user;
		
	}

}
