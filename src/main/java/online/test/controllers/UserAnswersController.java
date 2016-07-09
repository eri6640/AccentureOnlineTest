package online.test.controllers;


import online.test.models.TestQuestions;
import online.test.models.User;
import online.test.models.UserAnswers;
import online.test.models.dao.UserAnswersDao;
import online.test.post.classes.UserAnswerObj;
import online.test.utils.MainUtils;
import online.test.utils.TestQuestionsUtils;
import online.test.utils.UserUtils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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