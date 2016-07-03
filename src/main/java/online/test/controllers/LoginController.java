package online.test.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import online.test.utils.LoginUtils;
import online.test.utils.MainUtils;

@RestController
public class LoginController {
	
	MainUtils utils = new MainUtils();
	LoginUtils loginUtils = new LoginUtils();
	
	@RequestMapping( "/data/loggedin/" )
    public Map<String,Object> loggedIn( HttpServletRequest request ) {
		
		boolean isLoggedIn = loginUtils.isLoggedIn( request );
		
        System.out.println( "loggedIn: " + isLoggedIn );
  
        Map<String,Object> model = new HashMap<String,Object>();
	    model.put( "response", isLoggedIn );
	    
	    return model;
    }

}
