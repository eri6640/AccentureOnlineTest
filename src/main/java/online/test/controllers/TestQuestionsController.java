package online.test.controllers;


import online.test.models.QuestionChoices;
import online.test.models.TestQuestions;
import online.test.models.Tests;
import online.test.models.User;
import online.test.models.dao.QuestionChoicesDao;
import online.test.models.dao.TestQuestionsDao;
import online.test.models.dao.TestsDao;
import online.test.models.dao.UserAnswersDao;
import online.test.models.dao.UserDao;
import online.test.utils.MainUtils;
import online.test.utils.TestQuestionsUtils;
import online.test.utils.TestsUtils;
import online.test.utils.UserUtils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public boolean removeQuestion(int type, String question, Tests tests, User user, String answer
				){
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
	
	
	
	@RequestMapping("/data/tests/getTestInProgress")
	@ResponseBody
	public Map<String,Object> getTestInProgress( HttpServletRequest request ){
		
		User user_user = null;
		if( ( user_user = userUtils.getUserFromRequest( request ) ) == null ){
			mainUtils.showThis( "User null" );
			return null;
		}
		
		return testsUtils.getStartedTest( user_user );
	}


	
	
	@RequestMapping("/data/tests/getNextQuestion")
	@ResponseBody
	public Map<String,Object> getNextQuestion( @RequestParam("testId") String testId_string, HttpServletRequest request ) {

		//iegustam testu pec string id
		Tests test_test = testsUtils.getTestObject( testId_string );
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
		
		if( question == null ) return null;
		
		Map<String,Object> model = new HashMap<String,Object>();
		
		model.put( "question", question);
		
		return model;
	}
	
	
	@RequestMapping("/data/tests/getQuestionChoices")
	@ResponseBody
	public Iterable<QuestionChoices> getQuestionChoices( @RequestParam("questionId") String questionId_string, HttpServletRequest request ) {
		
		int question_id = 0;
		
		try{
			question_id = Integer.parseInt( questionId_string );
		}
		catch ( Exception error ){
			question_id = 0;
		}
		
		if( question_id <= 0 ) return null;
		
		Iterable<QuestionChoices> list = questionChoicesDao.getCurrentTestQuestions( (long) question_id );
		return list;
		
	}
	 
	 
} //TestsQuestionsController end