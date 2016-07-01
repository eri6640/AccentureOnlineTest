package online.test.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;

import online.test.models.User;
import online.test.models.dao.UserDao;

public class LoginUtils {
	
	MainUtils utils = new MainUtils();
	
	/**
	 * rightPassword  --> checks password....
	 * 
	 * @param email
	 * @return password
	 * */
	public boolean rightPassword( String check_email, String check_password ){
		
		User user_user = null;
		if( ( user_user = userDao.findByEmail(check_email) ) == null ) return false;
		
		String user_password = user_user.getPassword();
		
		MessageDigest md5_password = null;
		try {
			md5_password = MessageDigest.getInstance("MD5");
		} catch ( NoSuchAlgorithmException e ) {
			e.printStackTrace();
			
			return false;
		}
		md5_password.update( check_password.getBytes() );
		
		return true;
		
	}
	
	
	
	// ------------------------
	// PRIVATE FIELDS
	// ------------------------

	@Autowired
	private UserDao userDao;

}
