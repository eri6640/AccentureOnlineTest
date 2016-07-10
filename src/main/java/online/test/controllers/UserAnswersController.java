package online.test.controllers;


import online.test.models.TestQuestions;
import online.test.models.Tests;
import online.test.models.User;
import online.test.models.UserAnswers;
import online.test.models.dao.TestQuestionsDao;
import online.test.models.dao.UserAnswersDao;
import online.test.post.classes.AnswerCountObj;
import online.test.post.classes.IdObj;
import online.test.post.classes.UserAnswerObj;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class UserAnswersController {
	
	@Autowired  
	private UserAnswersDao userAnswersDao;
	@Autowired  
	private TestQuestionsDao testQuestionsDao;
	
	@Autowired
	TestQuestionsUtils questionsUtils = new TestQuestionsUtils();
	@Autowired
	UserUtils userUtils = new UserUtils();
	@Autowired
	TestsUtils testsUtils = new TestsUtils();
	MainUtils utils = new MainUtils();
	
	@RequestMapping( value = "/data/tests/getAnswerCountData", method = RequestMethod.POST )
	public @ResponseBody Map<String, Object> getAnswerCountData( @RequestBody IdObj test_idObj, HttpServletRequest request ) {
		
		if( test_idObj.getId() <= 0 ) return null;
		
		User user_user = null;
		if( ( user_user = userUtils.getUserFromRequest( request ) ) == null ){
			utils.showThis( "User null" );
			return null;
		}
		
		if( testsUtils.getStartedTest( user_user ) == null ) return null;
		
		Tests test_test = testsUtils.getTest( test_idObj.getId() );
		if( test_test == null ){
			utils.showThis( "Test null" );
			return null;
		}
		
		int question_count = 0;
		int answer_count = 0;
		int pinPointedAnswer_count = 0;
		
		List<TestQuestions> q_list = testQuestionsDao.getCurrentTestQuestions( test_test.getId() );
		
		if( q_list == null ) return null;
		question_count = q_list.size();
		
		List<UserAnswers> a_list = userAnswersDao.getCurrentUserTestAnswers( test_test.getId(), user_user.getId() );
		
		if( a_list != null ) answer_count = a_list.size();
		
		List<UserAnswers> p_list = userAnswersDao.getCurrentUserTestAnswersPinPointed( test_test.getId(), user_user.getId() );
		
		if( p_list != null ) pinPointedAnswer_count = p_list.size();
		
		AnswerCountObj answerCountObj = new AnswerCountObj( answer_count, pinPointedAnswer_count, question_count );
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put( "json", answerCountObj );
		
		return map;
	}
	
	@RequestMapping("/data/useranswers/selectall")
	@ResponseBody
	public Iterable<UserAnswers> selectAll() {
		Iterable<UserAnswers> list = userAnswersDao.findAll();
		return list;
	}
	
	@RequestMapping( value = "/data/tests/saveAnswer", method = RequestMethod.POST )
	public @ResponseBody boolean saveAnswer( @RequestBody UserAnswerObj userAnswerObj, HttpServletRequest request ) {
		
		if( userAnswerObj == null || userAnswerObj.getId() <= 0 || userAnswerObj.getAnswer().isEmpty() ) return false;
		
		User user_user = null;
		if( ( user_user = userUtils.getUserFromRequest( request ) ) == null ){
			utils.showThis( "User null" );
			return false;
		}
		
		TestQuestions question = questionsUtils.getQuestion( (int) userAnswerObj.getId() );
		
		if( question == null ) return false;
		
		if( userAnswerObj.getAnswer().startsWith( "[" ) && userAnswerObj.getAnswer().endsWith( "]" ) ){
			userAnswerObj.setAnswer( userAnswerObj.getAnswer().replace( "[", "").replace( "]", "") );
		}

		return questionsUtils.saveAnswer( user_user, question, userAnswerObj.getAnswer() );
	}
	
} //UserAnswersController end