package online.test.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import online.test.models.User;
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
		
		//if( ! page_name.equalsIgnoreCase( "/login.html" ) && ! loginUtils.isLoggedIn( request ) ) return mv_response_login;
		
		
		utils.showThis( "/templates/** " + page_name );
		return mv_response;
    }
	
	/**
	 * loggedIn
	 * 
	 * @return true if user is logged on, false if isnt logged in
	 * */
	/*@RequestMapping( "/data/loggedin" )
    public Map<String,Object> loggedIn( HttpServletRequest request ) {
		
		boolean isLoggedIn = loginUtils.isLoggedIn( request );
		
        System.out.println( "loggedIn: " + isLoggedIn );
  
        Map<String,Object> model = new HashMap<String,Object>();
	    model.put( "response", isLoggedIn );
	    
	    return model;
    }
	
	@RequestMapping( "/data/dologin" )
    public Map<String,Object> doLogin( HttpServletRequest request ) {
		
		boolean isLoggedIn = loginUtils.isLoggedIn( request );
		
        System.out.println( "loggedIn: " + isLoggedIn );
  
        Map<String,Object> model = new HashMap<String,Object>();
	    model.put( "response", isLoggedIn );
	    
	    return model;
    }
	*/
	

	/**
	 * 
	 *  UserDao
	 * 
	 */
	
	@Autowired
	private UserDao userDao;

}
