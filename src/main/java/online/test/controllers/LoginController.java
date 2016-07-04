package online.test.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	/*
	 * 
	 * for unauthorized
	 * 
	 */
	@RequestMapping( value = "/page/**", method = RequestMethod.GET )
    public ModelAndView loginControl( Model model, HttpServletRequest request, ModelAndView md ) throws Exception {
		
		String page_name = request.getServletPath().replaceFirst( "/page", "" );
		ModelAndView mv_response = new ModelAndView( "/templates" + page_name );
		ModelAndView mv_response_login = new ModelAndView( "/templates" + "/login.html" );
		ModelAndView mv_response_home= new ModelAndView( "/templates" + "/home.html" );
		
		boolean loggedIn = loginUtils.isLoggedIn( request );
		
		if( ! page_name.equalsIgnoreCase( "/login.html" ) && ! loggedIn ) return mv_response_login;
		
		if( page_name.equalsIgnoreCase( "/login.html" ) && loggedIn ) return mv_response_home;
		
		utils.showThis( "/templates/** " + page_name );
		return mv_response;
    }
	
	/**
	 * loggedIn
	 * 
	 * @return true if user is logged on, false if isnt logged in
	 * */
	@RequestMapping( "/data/auth/isloggedin" )
    public boolean isLoggedIn( HttpServletRequest request ) {    
	    return loginUtils.isLoggedIn( request );
    }
	
	
	@RequestMapping( "/data/auth/submitlogin" )
    public ModelAndView submitLogin( @RequestParam("email") String email, @RequestParam("password") String password, HttpServletRequest request, ModelAndView md ) {
				
		String page_name = request.getServletPath().replaceFirst( "/page", "" );
		ModelAndView mv_response_login = new ModelAndView( "/templates" + "/login.html" );
		ModelAndView mv_response_home= new ModelAndView( "/templates" + "/home.html" );
		

		if( email.isEmpty() || password.isEmpty() ) return mv_response_login;
		utils.showThis( "submitlogin ----------" );
		
	    return loginUtils.doLogin( email, password, request ) ? mv_response_home : mv_response_login;
    }
	
	@RequestMapping( "/data/auth/logout" )
    public boolean logout( HttpServletRequest request ) {
		boolean loggedOut = loginUtils.logout( request );
	    return loggedOut;
    }
	

	/**
	 * 
	 *  UserDao
	 * 
	 */
	
	@Autowired
	private UserDao userDao;

}