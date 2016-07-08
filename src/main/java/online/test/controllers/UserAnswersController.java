package online.test.controllers;


import online.test.models.TestQuestions;
import online.test.models.User;
import online.test.models.UserAnswers;
import online.test.models.dao.UserAnswersDao;
import online.test.utils.MainUtils;
import online.test.utils.TestQuestionsUtils;
import online.test.utils.UserUtils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserAnswersController {
	
	@Autowired  
	private UserAnswersDao userAnswersDao;
	
	@Autowired
	TestQuestionsUtils questionsUtils = new TestQuestionsUtils();
	@Autowired
	UserUtils userUtils = new UserUtils();
	MainUtils utils = new MainUtils();
	
	@RequestMapping("/data/useranswers/selectall")
	@ResponseBody
	public Iterable<UserAnswers> selectAll() {
		Iterable<UserAnswers> list = userAnswersDao.findAll();
		return list;
	}
	
	@RequestMapping("/data/tests/saveAnswer")
	@ResponseBody
	public boolean saveAnswer( @RequestParam("questionId") String questionId_string, @RequestParam("answer") String answer_string, HttpServletRequest request ) {
		
		if( questionId_string.isEmpty() || answer_string.isEmpty() ) return false;
		
		User user_user = null;
		if( ( user_user = userUtils.getUserFromRequest( request ) ) == null ){
			utils.showThis( "User null" );
			return false;
		}
		
		TestQuestions question = questionsUtils.getQuestion( questionId_string );
		
		if( question == null ) return false;
		
		if( answer_string.startsWith( "[" ) && answer_string.endsWith( "]" ) ){
			answer_string = answer_string.replace( "[", "").replace( "]", "");
		}

		return questionsUtils.saveAnswer( user_user, question, answer_string );
	}
	
} //UserAnswersController end