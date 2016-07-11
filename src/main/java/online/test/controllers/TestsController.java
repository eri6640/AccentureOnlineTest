package online.test.controllers;

import online.test.models.Tests;
import online.test.models.User;
import online.test.models.dao.TestsDao;
import online.test.models.dao.UserDao;
import online.test.post.classes.IdObj;
import online.test.post.classes.TestObj;
import online.test.utils.MainUtils;
import online.test.utils.TestQuestionsUtils;
import online.test.utils.TestsUtils;
import online.test.utils.UserUtils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestsController {
	
	@Autowired
	private TestsDao testsDao;
	@Autowired
	private UserDao userDao;

	
	MainUtils mainUtils = new MainUtils();
	
	@Autowired
	TestsUtils testsUtils = new TestsUtils();
	@Autowired
	UserUtils userUtils = new UserUtils();
	@Autowired
	TestQuestionsUtils testQuestionsUtils = new TestQuestionsUtils();
	
	@RequestMapping("/data/tests/selectallTests")
	@ResponseBody
	public Iterable<Tests> selectAll() {
		Iterable<Tests> list = testsDao.findAll();
		return list;
	}
	
	@RequestMapping("/data/tests/create")
	@ResponseBody
	public Boolean addTest(String title, long userID, String description){
		Tests test = null;
		try {
			User user=userDao.findById(userID);
			test = new Tests(title, user, description);
			testsDao.save(test);
		}
		catch (Exception ex) {
			return false;
		}
			return true;
	}
	
	@RequestMapping("/data/tests/remove")
	@ResponseBody
	public Boolean removeTest(long id){
		Tests test = null;
		try {
			test = new Tests(id);
			testsDao.delete(test);
		}
		catch (Exception ex) {
			return false;
		}
		return true;
	}

	@RequestMapping("/data/tests/edit")
	@ResponseBody
	public String editTest(long id, String title, String description){
		Tests test = null;	
		
		try {			
			test = new Tests(id);
			test.setTitle(title);
			test.setDescription(description);
			testsDao.save(test);						
		}
	    catch (Exception ex) {
	    	return "Error updating the test: " + ex.toString();
	    }
	    return "Test succesfully updated!";
	}
	
	@RequestMapping( value = "/data/tests/selectAvailableTests", method = RequestMethod.POST )
	public @ResponseBody Iterable<Tests> selectAvailableTests( HttpServletRequest request ) {
		Iterable<Tests> list = testsUtils.getAvailableTests( request );
		return list;
	}
	
	
	@RequestMapping( value = "/data/tests/getTest", method = RequestMethod.POST )
	public @ResponseBody TestObj getTest( @RequestBody TestObj testObj ) {
		if( testObj == null || testObj.getId() == 0 ) return null;
		
		Tests test_test = testsUtils.getTest( (int) testObj.getId() );
		if( test_test == null ) return null;
		
		testObj.setUserId( test_test.getUser().getId() );
		testObj.setTitle( test_test.getTitle() );
		testObj.setDescription( test_test.getDescription() );
		testObj.setCreated( test_test.getCreated() );
		
		return testObj;
	}
	
	@RequestMapping( value = "/data/tests/startTest", method = RequestMethod.POST )
	public @ResponseBody boolean startTest( @RequestBody IdObj test_idObj, HttpServletRequest request ) {
		
		if( test_idObj.getId() <= 0 ) return false;
		
		User user_user = null;
		if( ( user_user = userUtils.getUserFromRequest( request ) ) == null ){
			mainUtils.showThis( "User null" );
			return false;
		}
		
		if( testsUtils.getStartedTest( user_user ) != null ) return false;
		
		Tests test_test = testsUtils.getTest( test_idObj.getId() );
		if( test_test == null ){
			mainUtils.showThis( "Test null" );
			return false;
		}
		
		return testsUtils.startTest( user_user, test_test );
	}

	
	@RequestMapping( value = "/data/tests/forceStopTest", method = RequestMethod.POST )
	public @ResponseBody boolean forceStopTest( @RequestBody IdObj test_idObj, HttpServletRequest request ) {
		
		if( test_idObj.getId() <= 0 ) return false;
		
		User user_user = null;
		if( ( user_user = userUtils.getUserFromRequest( request ) ) == null ){
			mainUtils.showThis( "User null" );
			return false;
		}
		
		if( testsUtils.getStartedTest( user_user ) == null ) return false;
		
		Tests test_test = testsUtils.getTest( test_idObj.getId() );
		if( test_test == null ){
			mainUtils.showThis( "Test null" );
			return false;
		}
		
		return testsUtils.forceStopTest( user_user, test_test );
	}

	  
} //TestsController end