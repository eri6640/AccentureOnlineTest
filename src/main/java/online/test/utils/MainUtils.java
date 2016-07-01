package online.test.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import online.test.models.User;
import online.test.models.dao.UserDao;

public class MainUtils {
	

	/**
	 * isAjax  --> checks request....
	 * 
	 * @param request http servlet request
	 * @return True if is ajax, False if isnt ajax
	 * */
	public boolean isAjax( HttpServletRequest request ){
		return "XMLHttpRequest".equals( request.getHeader("X-Requested-With")) ? true : false;
	}
	
	
	public String getMD5( String string ){
		MessageDigest md5_password = null;
		try {
			md5_password = MessageDigest.getInstance("MD5");
		} catch ( NoSuchAlgorithmException error ) {
			error.printStackTrace();
			showThis( "getMD5 error" );
			return null;
		}
		
		md5_password.update( string.getBytes() );
		return string;
	}
	
	
	public void showThis( String string ){
		System.out.println( string );
	}

}
