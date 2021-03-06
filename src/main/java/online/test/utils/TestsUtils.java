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

import online.test.models.QuestionChoices;
import online.test.models.TestQuestions;
import online.test.models.Tests;
import online.test.models.User;
import online.test.models.UserAnswers;
import online.test.models.dao.QuestionChoicesDao;
import online.test.models.dao.TestQuestionsDao;
import online.test.models.dao.TestsDao;
import online.test.models.dao.UserAnswersDao;
import online.test.models.dao.UserDao;

@Component
public class TestsUtils {

	@Autowired
	private TestsDao testsDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private TestQuestionsDao questionDao;
	@Autowired
	private UserAnswersDao userAnswersDao;
	@Autowired
	private QuestionChoicesDao questionChoicesDao;

	@Autowired
	TestQuestionsUtils testQuestionsUtils = new TestQuestionsUtils();
	@Autowired
	private UserUtils userUtils;
	
	MainUtils utils = new MainUtils();
	
	/*public Map<String,Object> getTest( String testId_string ){
		
		if( testId_string.isEmpty() ) return null;
		int testId = 0;
		try{
			testId = Integer.parseInt( testId_string );
		}
		catch( Exception error ){
			return null;
		}
		
		return this.getTest( testId );
	}*/
	
	public Tests getTest( String testId_string ){
		
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
	
	public Tests getTest( int testId ){
		
		if( testId <= 0 ) return null;
		
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
	
	public ArrayList<Tests> getAvailableTests( HttpServletRequest request ){
		
		/*CsrfToken csrf = (CsrfToken) request.getAttribute( CsrfToken.class .getName() );
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
		}*/
		
		User token_user = userUtils.getUserFromRequest( request );
		
		if( token_user == null ) return null;
		
		Iterable<Tests> tests = testsDao.findAll();
		
		if( tests == null ) return null;
		
		ArrayList<Tests> availableTests = new ArrayList<Tests>();
		
		for( Tests test : tests ){
			
			if( ! testAlreadyStarted( token_user, test ) ){
				
				List<TestQuestions> question_list = questionDao.getCurrentTestQuestions( test.getId() );
				
				if( ! question_list.isEmpty() ){
					
					boolean error = true;
					
					for( TestQuestions question : question_list ){
						if( question.getType() == 1 || question.getType() == 2 ){
							
							List<QuestionChoices> list = questionChoicesDao.getCurrentTestQuestions( question.getId() );
							
							if( ! list.isEmpty() ){
								error = false;
							}
						}
						else{
							error = false;
						}
					}
					
					if( ! error ) availableTests.add( test );
				}
			}
			
		}
		
		
		return availableTests.isEmpty() ? null : availableTests;
		
	}
	
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
	
	public Tests getStartedTest( User user_user ){
		
		List<UserAnswers> tests_started = userAnswersDao.getCurrentUserStartedTests( user_user.getId() );
		List<UserAnswers> tests_stoped = userAnswersDao.getCurrentUserStopedTests( user_user.getId() );
				
		if( tests_started.isEmpty() ){
			utils.showThis( "! have started test" );
			return null;
		}
		
		if( tests_started.size() == 1 && tests_stoped.isEmpty() ){
			utils.showThis( "tests_started.size() == 1 && tests_stoped.isEmpty()" );
			return tests_started.get( 0 ).getTests();
		}
		
		List<Long> test_startedIds = new ArrayList<Long>();
				
		for( UserAnswers answerStop : tests_stoped ){
			test_startedIds.add( answerStop.getTests().getId() );
		}
		
		for( UserAnswers answerStart : tests_started ){
			if( ! test_startedIds.contains( answerStart.getTests().getId() ) ){
				utils.showThis( "have started test" );
				return answerStart.getTests();
			}
		}
				
		return null;
	}
	
	public boolean startTest( User user_user, Tests test_test ){
		
		TestQuestions question = testQuestionsUtils.getUserNextQuestion( user_user, test_test );
		if( question == null ) return false;
		
		UserAnswers user_answer = new UserAnswers( question, test_test, user_user, null, null, 1 );
		
		userAnswersDao.save( user_answer );
		return true;
	}
	
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
