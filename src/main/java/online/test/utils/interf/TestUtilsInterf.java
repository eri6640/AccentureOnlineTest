package online.test.utils.interf;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import online.test.models.Tests;
import online.test.models.User;

public interface TestUtilsInterf {
	
	public Map<String,Object> getTest( String testId_string );
	
	public Map<String,Object> getTest( int testId );
	
	public Tests getTestObject( String testId_string );
	
	public Tests getTestObject( int testId );
	
	public ArrayList<Tests> getAvailableTests( HttpServletRequest request );
	
	public boolean testAlreadyStarted( User user, Tests test );

	public Map<String,Object> getStartedTest( User user_user );
	
	public boolean startTest( User user_user, Tests test_test );

	boolean forceStopTest(User user_user, Tests test_test);
}
