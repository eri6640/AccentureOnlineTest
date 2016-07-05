package online.test.controllers;

import java.util.HashMap;
import java.util.Map;

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
public class MainController {
	
	MainUtils utils = new MainUtils();
	@Autowired
	LoginUtils loginUtils = new LoginUtils();
	
	@RequestMapping( value = "/acp/**", method = RequestMethod.GET )
    public ModelAndView acpControl( Model model, HttpServletRequest request, ModelAndView md ) throws Exception {
		
		//String page_name = request.getServletPath().replaceFirst( "/admin/page", "" );
		ModelAndView mv_response = new ModelAndView( "/admin/index.html" );
		
		boolean loggedIn = loginUtils.isLoggedIn( request );
		
		if( ! loggedIn ) return new ModelAndView("redirect:/#/login");
		
		return mv_response;
    }
	
	@RequestMapping( value = "/admin/page/**", method = RequestMethod.GET )
    public ModelAndView acpPageControl( Model model, HttpServletRequest request, ModelAndView md ) throws Exception {
		
		String page_name = request.getServletPath().replaceFirst( "/admin/page", "" );
		ModelAndView mv_response = new ModelAndView( "/admin/templates" + page_name );
		
		return mv_response;
    }
	
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
	
	
	/*
	 * 
	 * for testing
	 * 
	 */
	@RequestMapping( "/data/repeat" )
    public Map<String,Object> repeat( @RequestParam("field1") String string, HttpServletRequest request ) {
        System.out.println( "repeat: " + string );
  
        Map<String,Object> model = new HashMap<String,Object>();

	    model.put( "content", userDao.findById( 1 ) );
	    return model;
    }	
	
	
	/**
	 * 
	 *  UserDao
	 * 
	 */
	
	@Autowired
	private UserDao userDao;

}