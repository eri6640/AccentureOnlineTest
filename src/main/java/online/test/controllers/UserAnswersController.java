package online.test.controllers;


import online.test.models.TestQuestions;
import online.test.models.Tests;
import online.test.models.User;
import online.test.models.UserAnswers;
import online.test.models.dao.UserAnswersDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserAnswersController {
	
	
	
	@RequestMapping("/data/useranswers/selectall")
	@ResponseBody
	public Iterable<UserAnswers> selectAll() {
		Iterable<UserAnswers> list = userAnswersDao.findAll();
		return list;
	} 
	
	@RequestMapping("/data/useranswers/create")
	@ResponseBody
	public Boolean addAnswer(TestQuestions testsQuestions, Tests tests, User user, String answer, byte[] imageAnswer) {
		UserAnswers questionAnswer = null;
		try {
			questionAnswer = new UserAnswers(testsQuestions, tests, user, answer, imageAnswer);
			userAnswersDao.save(questionAnswer);
		}
		catch (Exception ex) {
			return false;
			//return "Error adding answer: " + ex.toString();
		}
		return false;
	}
	
	@RequestMapping("/data/useranswers/delete")
	@ResponseBody
	public Boolean removeAnswer(TestQuestions testsQuestions, Tests tests, User user, String answer, byte[] imageAnswer) {
		try {
			UserAnswers questionAnswer = new UserAnswers(testsQuestions, tests, user, answer, imageAnswer);
			userAnswersDao.delete(questionAnswer);
		}
		catch (Exception ex) {
			return false;
			//return "Error deleting the answer: " + ex.toString();
		}
		return true;
	}
	
	@Autowired  
	private UserAnswersDao userAnswersDao;
} //UserAnswersController end