package online.test.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import online.test.models.Tests;
import online.test.models.dao.TestsDao;

@Component
public class TestsUtils {

	@Autowired
	private TestsDao testsDao;
	MainUtils utils = new MainUtils();
	
	public Map<String,Object> getTest( String testId_string ){
		
		if( testId_string.isEmpty() ) return null;
		int testId = 0;
		try{
			testId = Integer.parseInt( testId_string );
		}
		catch( Exception error ){
			return null;
		}
		
		return this.getTest( testId );
	}
	
	public Map<String,Object> getTest( int testId ){
		
		if( testId <= 0 ) return null;
		
		Map<String,Object> model = new HashMap<String,Object>();
		
		Tests test_test = null;
		
		try{
			test_test = testsDao.findById( (long)testId );
		}
		catch( Exception error ){
			utils.showThis( "Tests null, id:" + testId );
			return null;
		}
		
		model.put( "test", test_test );
		
		return test_test != null ? model : null;
	}
	
}
