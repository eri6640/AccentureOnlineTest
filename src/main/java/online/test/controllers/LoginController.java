package online.test.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import online.test.post.classes.AuthObj;
import online.test.utils.LoginUtils;
import online.test.utils.MainUtils;

@RestController
public class LoginController {
	
	MainUtils utils = new MainUtils();
	@Autowired
	LoginUtils loginUtils = new LoginUtils();
	
	/*@RequestMapping( "/data/auth/isloggedin" )
    public boolean isLoggedIn( HttpServletRequest request ) {    
	    return loginUtils.isLoggedIn( request );
    }*/
	
	
	@RequestMapping( value = "/data/auth/isloggedin", method = RequestMethod.POST )	
	public @ResponseBody boolean isLoggedIn( HttpServletRequest request ) {
		return loginUtils.isLoggedIn( request );
	}
	
	
	/*@RequestMapping( "/data/auth/submitlogin" )
    public ModelAndView submitLogin( @RequestParam("email") String email, @RequestParam("password") String password, HttpServletRequest request, ModelAndView md ) {
				
		//String page_name = request.getServletPath().replaceFirst( "/page", "" );
		ModelAndView mv_response_login = new ModelAndView( "/templates" + "/login.html" );
		ModelAndView mv_response_home= new ModelAndView( "/templates" + "/home.html" );
		

		if( email.isEmpty() || password.isEmpty() ) return mv_response_login;
		
	    return loginUtils.doLogin( email, password, request ) ? mv_response_home : mv_response_login;
    }*/
	
	@RequestMapping( value = "/data/auth/doLogin", method = RequestMethod.POST )	
    public @ResponseBody boolean doLogin( @RequestBody AuthObj authObj, HttpServletRequest request ) {
		
		if( authObj == null || authObj.getEmail().isEmpty() || authObj.getPassword().isEmpty() ){
			utils.showThis( "authObj == null" );
			return false;
		}
		
	    return loginUtils.doLogin( authObj.getEmail(), authObj.getPassword(), request );
    }
	
	@RequestMapping( value = "/data/auth/doLogout", method = RequestMethod.POST )
    public @ResponseBody boolean logout( HttpServletRequest request ) {
	    return loginUtils.logout( request );
    }

}
