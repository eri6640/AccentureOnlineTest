package online.test.controllers;


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
import online.test.post.classes.IdObj;
import online.test.utils.MainUtils;
import online.test.utils.TestQuestionsUtils;
import online.test.utils.TestsUtils;
import online.test.utils.UserUtils;

import java.util.HashMap;
import java.util.List;
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
public class TestQuestionsController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private TestsDao testsDao;
	@Autowired
	private UserAnswersDao userAnswersDao;
	@Autowired
	private TestQuestionsDao testQuestionsDao;
	@Autowired
	private QuestionChoicesDao questionChoicesDao;
	
	MainUtils mainUtils = new MainUtils();
	
	@Autowired
	TestsUtils testsUtils = new TestsUtils();
	@Autowired
	UserUtils userUtils = new UserUtils();
	@Autowired
	TestQuestionsUtils testQuestionsUtils = new TestQuestionsUtils();
	
	@RequestMapping("/data/testquestions/selectall")
	@ResponseBody
	public Iterable<TestQuestions> selectAll() {
		Iterable<TestQuestions> list = testQuestionsDao.findAll();
		return list;
	}
	
	@RequestMapping("/data/testquestions/create")
	@ResponseBody
	public boolean addQuestion(int type, String question, Tests tests, User user, String answer){
		 TestQuestions testQuestion = null;
			try {
				testQuestion = new TestQuestions(type, question, tests, user, answer);
				testQuestionsDao.save(testQuestion);
			}
			catch (Exception ex) {
				return false;
				//return "Error adding answer: " + ex.toString();
			}
				return true;
	 }
	 
	@RequestMapping("/data/testquestions/delete")
	@ResponseBody
	public boolean removeQuestion(int type, String question, Tests tests, User user, String answer ){
		TestQuestions testQuestion = null;
			try {
				testQuestion = new TestQuestions(type, question, tests, user, answer);
				testQuestionsDao.delete(testQuestion);
			}
			catch (Exception ex) {
				return false;
				//return "Error deleting answer: " + ex.toString();
			}
			return true;
	}
	
	
	
	@RequestMapping( value = "/data/tests/getTestInProgress", method = RequestMethod.POST )
	public @ResponseBody Tests getTestInProgress( HttpServletRequest request ){
		
		User user_user = null;
		if( ( user_user = userUtils.getUserFromRequest( request ) ) == null ){
			mainUtils.showThis( "User null" );
			return null;
		}
		
		return testsUtils.getStartedTest( user_user );
	}


	
	
	@RequestMapping( value = "/data/tests/getNextQuestion", method = RequestMethod.POST )
	public @ResponseBody TestQuestions getNextQuestion( @RequestBody IdObj test_idObj, HttpServletRequest request ) {

		if( test_idObj.getId() <= 0 ) return null;
		
		//iegustam testu pec string id
		Tests test_test = testsUtils.getTest( test_idObj.getId() );
		if( test_test == null ){
			mainUtils.showThis( "Test null" );
			return null;
		}
		
		//iegustam lietotaju pec request
		User user_user = null;
		if( ( user_user = userUtils.getUserFromRequest( request ) ) == null ){
			mainUtils.showThis( "User null" );
			return null;
		}
		
		//iegustam nakosho jautajumu, kur status == 2
		TestQuestions question = testQuestionsUtils.getUserNextQuestion( user_user, test_test );
		
		return question != null ? question : null;
	}
	
	
	@RequestMapping( value = "/data/tests/getQuestionChoices", method = RequestMethod.POST )
	public @ResponseBody Iterable<QuestionChoices> getQuestionChoices( @RequestBody IdObj question_idObj, HttpServletRequest request ) {
		
		if( question_idObj.getId() <= 0 ) return null;
		
		Iterable<QuestionChoices> list = questionChoicesDao.getCurrentTestQuestions( (long) question_idObj.getId() );
		return list;
		
	}
	
	
	@RequestMapping( value = "/data/tests/pinPoint", method = RequestMethod.POST )
	public @ResponseBody boolean pinPoint( @RequestBody IdObj question_idObj, HttpServletRequest request ) {
		
		if( question_idObj.getId() <= 0 ) return false;
		
		User user_user = null;
		if( ( user_user = userUtils.getUserFromRequest( request ) ) == null ){
			mainUtils.showThis( "User null" );
			return false;
		}
		
		//if( testsUtils.getStartedTest( user_user ) == null ) return false;
		
		TestQuestions question = testQuestionsUtils.getQuestion( question_idObj.getId() );
		if( question == null ){
			mainUtils.showThis( "Test null" );
			return false;
		}
		
		return testQuestionsUtils.pinPoint( user_user, question );
	}
	
	@RequestMapping( value = "/data/tests/getTimerTime", method = RequestMethod.POST )
	public @ResponseBody long getTimerTime( @RequestBody IdObj test_idObj, HttpServletRequest request ) {
		
		if( test_idObj.getId() <= 0 ) return -7;
		
		//iegustam testu pec string id
		Tests test_test = testsUtils.getTest( test_idObj.getId() );
		if( test_test == null ){
			mainUtils.showThis( "Test null" );
			return -7;
		}
				
		//iegustam lietotaju pec request
		User user_user = null;
		if( ( user_user = userUtils.getUserFromRequest( request ) ) == null ){
			mainUtils.showThis( "User null" );
			return -7;
		}
		
		List<UserAnswers> answer_list = userAnswersDao.getUserTestAllAnswers( test_test.getId(), user_user.getId() );
		
		if( answer_list.isEmpty() ) return -7;
		
		return answer_list.get( 0 ).getPosted();		
	}

	 
	 
} //TestsQuestionsController end