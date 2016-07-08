package online.test.utils.interf;

import javax.servlet.http.HttpServletRequest;

import online.test.models.User;

public interface UserUtilsInterf {
	
	public User getUserFromRequest( HttpServletRequest request );
}