package online.test.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import online.test.models.dao.UserDao;
import online.test.utils.LoginUtils;
import online.test.utils.MainUtils;

@RestController
public class LoginController {
	
	MainUtils utils = new MainUtils();
	@Autowired
	LoginUtils loginUtils = new LoginUtils();
	
	@RequestMapping( "/data/auth/isloggedin" )
    public boolean isLoggedIn( HttpServletRequest request ) {    
	    return loginUtils.isLoggedIn( request );
    }
	
	
	@RequestMapping( "/data/auth/submitlogin" )
    public ModelAndView submitLogin( @RequestParam("email") String email, @RequestParam("password") String password, HttpServletRequest request, ModelAndView md ) {
				
		//String page_name = request.getServletPath().replaceFirst( "/page", "" );
		ModelAndView mv_response_login = new ModelAndView( "/templates" + "/login.html" );
		ModelAndView mv_response_home= new ModelAndView( "/templates" + "/home.html" );
		

		if( email.isEmpty() || password.isEmpty() ) return mv_response_login;
		
	    return loginUtils.doLogin( email, password, request ) ? mv_response_home : mv_response_login;
    }
	
	@RequestMapping( "/data/auth/logout" )
    public boolean logout( HttpServletRequest request ) {
	    return loginUtils.logout( request );
    }
	

	/**
	 * 
	 *  UserDao
	 * 
	 */

	@Autowired
	private UserDao userDao;

}
