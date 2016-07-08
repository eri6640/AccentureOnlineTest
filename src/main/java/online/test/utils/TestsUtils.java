package online.test.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import online.test.models.TestQuestions;
import online.test.models.Tests;
import online.test.models.User;
import online.test.models.UserAnswers;
import online.test.models.dao.TestQuestionsDao;
import online.test.models.dao.TestsDao;
import online.test.models.dao.UserAnswersDao;
import online.test.models.dao.UserDao;
import online.test.utils.interf.TestUtilsInterf;

@Component
public class TestsUtils implements TestUtilsInterf {

	@Autowired
	private TestsDao testsDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserAnswersDao userAnswersDao;

	@Autowired
	TestQuestionsUtils testQuestionsUtils;
	@Autowired
	MainUtils utils;
	@Override
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
	@Override
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

	@Override
	public Tests getTestObject( String testId_string ){
		
		if( testId_string.isEmpty() ) return null;
		int testId = 0;
		try{
			testId = Integer.parseInt( testId_string );
		}
		catch( Exception error ){
			return null;
		}
		
		return this.getTestObject( testId );
	}
	@Override
	public Tests getTestObject( int testId ){
		
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
		
		return test_test;
	}
	@Override
	public ArrayList<Tests> getAvailableTests( HttpServletRequest request ){
		
		CsrfToken csrf = (CsrfToken) request.getAttribute( CsrfToken.class .getName() );
		if( csrf == null ) return null;
		
		Cookie cookie = WebUtils.getCookie( request, utils.TokenName );
		String token = csrf.getToken();
		
		//if something is wrong with token or cookie, redirect to login page
		if ( cookie == null || token != null && ! token.equals( cookie.getValue() ) ) return null;
		
		String token_hash = utils.MD5( token );
		if( token_hash.isEmpty() ) return null;
		
		User token_user = null;
		
		try{
			token_user = userDao.findByToken( token_hash );
		}
		catch ( Exception error ){
			token_user = null;
			utils.showThis( "user null" );
		}
		
		if( token_user == null ) return null;
		
		Iterable<Tests> tests = testsDao.findAll();
		
		if( tests == null ) return null;
		
		ArrayList<Tests> availableTests = new ArrayList<Tests>();
		
		for( Tests test : tests ){
			
			if( ! testAlreadyStarted( token_user, test ) ){
				availableTests.add( test );
			}
			
		}
		
		
		return availableTests.isEmpty() ? null : availableTests;
		
	}
	@Override
	public boolean testAlreadyStarted( User user, Tests test ){
		
		List<UserAnswers> userAnswers = userAnswersDao.getCurrentUserStartedTests( user.getId() );
		
		if( userAnswers == null ) return false;
		
		for( UserAnswers answer : userAnswers ){
			if( test.getId() == answer.getTests().getId() ){
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * 
	 * 
	 * 
	 */
	@Override
	public Map<String,Object> getStartedTest( User user_user ){
		
		Map<String, Object> model = new HashMap<String,Object>();
		List<UserAnswers> tests_started = userAnswersDao.getCurrentUserStartedTests( user_user.getId() );
		List<UserAnswers> tests_stoped = userAnswersDao.getCurrentUserStopedTests( user_user.getId() );
				
		if( tests_started.isEmpty() ){
			utils.showThis( "! have started test" );
			return null;
		}
		
		if( tests_started.size() == 1 && tests_stoped.isEmpty() ){
			model.put( "test", tests_started.get( 0 ).getTests() );
			utils.showThis( "tests_started.size() == 1 && tests_stoped.isEmpty()" );
			return model;
		}
		
		List<Long> test_startedIds = new ArrayList<Long>();
				
		for( UserAnswers answerStop : tests_stoped ){
			test_startedIds.add( answerStop.getTests().getId() );
		}
		
		for( UserAnswers answerStart : tests_started ){
			if( ! test_startedIds.contains( answerStart.getTests().getId() ) ){
				utils.showThis( "have started test" );
				model.put( "test", answerStart.getTests() );
				return model;
			}
		}
				
		return null;
	}
	@Override
	public boolean startTest( User user_user, Tests test_test ){
		
		TestQuestions question = testQuestionsUtils.getUserNextQuestion( user_user, test_test );
		if( question == null ) return false;
		
		UserAnswers user_answer = new UserAnswers( question, test_test, user_user, null, null, 1 );
		
		userAnswersDao.save( user_answer );
		return true;
	}
	@Override
	public boolean forceStopTest( User user_user, Tests test_test ){
		
		TestQuestions question = testQuestionsUtils.getUserLastQuestion( user_user, test_test );
		if( question == null ){

			utils.showThis( "question null" );
			utils.showThis( "question null" );
			utils.showThis( "question null" );
			return false;
		}
		
		UserAnswers user_answer = new UserAnswers( question, test_test, user_user, null, null, 4 );
		
		userAnswersDao.save( user_answer );
		return true;
	}
	

}
