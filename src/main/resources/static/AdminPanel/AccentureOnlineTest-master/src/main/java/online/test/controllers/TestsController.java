package online.test.controllers;

import online.test.models.Tests;
import online.test.models.User;
import online.test.models.dao.TestsDao;
import online.test.models.dao.UserDao;
import online.test.utils.MainUtils;
import online.test.utils.TestQuestionsUtils;
import online.test.utils.TestsUtils;
import online.test.utils.UserUtils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@RequestMapping("/data/tests/selectAvailableTests")
	@ResponseBody
	public Iterable<Tests> selectAvailableTests( HttpServletRequest request ) {
		Iterable<Tests> list = testsUtils.getAvailableTests( request );
		return list;
	}
	
	@RequestMapping("/data/tests/getTest")
	@ResponseBody
	public Map<String,Object> getTest( @RequestParam("testId") String testId_string ) {
		return testsUtils.getTest( testId_string );
	}
	
	@RequestMapping("/data/tests/startTest")
	@ResponseBody
	public boolean startTest( @RequestParam("testId") String testId_string, HttpServletRequest request ) {
		
		User user_user = null;
		if( ( user_user = userUtils.getUserFromRequest( request ) ) == null ){
			mainUtils.showThis( "User null" );
			return false;
		}
		
		if( testsUtils.getStartedTest( user_user ) != null ) return false;
		
		Tests test_test = testsUtils.getTestObject( testId_string );
		if( test_test == null ){
			mainUtils.showThis( "Test null" );
			return false;
		}
		
		return testsUtils.startTest( user_user, test_test );
	}
	  
} //TestsController end