package online.test.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import online.test.Functions;
import online.test.models.dao.UserDao;

//@Controller
@RestController
public class MainController {


	@RequestMapping( "/data/userdata" )
	public Map<String,Object> home( @RequestParam("first") String first, HttpServletRequest request ) {
	    Map<String,Object> model = new HashMap<String,Object>();
	    
	    //if( first.equals("kjest") ) model.put( "content", "get:if:" + first );
	    //else model.put( "content", "get:else:" + first );
	    //System.out.println(request.getRemoteAddr());
	    /*for( User user_user : userDao.findAll() ){
    	System.out.println( user_user.getId() + " " + user_user.getName() );
	    }*/
	    
	    //User user_user = userDao.findOne( (long) 1 );
	    //System.out.println( user_user.getId() );
	    
	    
	    
	    model.put( "content", "ajax?:" + Functions.isAjax( request ) );
	    return model;
	}
	
	/*@RequestMapping( "/data/repeat/" )
	public Map<String,Object> repeat( @RequestParam("field1") String first, HttpServletRequest request ) {
	    Map<String,Object> model = new HashMap<String,Object>();
	    model.put( "content", first );
	    return model;
	}*/
	
	@RequestMapping( "/data/repeat" )
    public Map<String,Object> repeat( @RequestParam("field1") String string, HttpServletRequest request ) {
        System.out.println( "repeat: " + string );
  
        Map<String,Object> model = new HashMap<String,Object>();

	    model.put( "content", Functions.isAjax(request) );
	    return model;
    }
	
	/*@RequestMapping( value = "/data/repeat.html", method = RequestMethod.POST )
	public ModelAndView httpServicePostJSONDataExample( ModelMap model ) {
		return new ModelAndView("httpservice_post_json");
	}*/
	
	
	
	/**
	 * 
	 *  UserDao
	 * 
	 */
	
	@Autowired
	  private UserDao userDao;

}