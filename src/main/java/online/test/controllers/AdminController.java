package online.test.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import online.test.models.QuestionChoices;
import online.test.models.Tests;
import online.test.models.User;
import online.test.models.UserAnswers;
import online.test.utils.AdminUtils;
import online.test.utils.LoginUtils;
import online.test.utils.MainUtils;

@RestController
public class AdminController {

	@RequestMapping("/data/tests/getAllTests")
	@ResponseBody
	public Iterable<Tests> selectAll() {
		return adminUtils.selectAllTests();
	}

	@RequestMapping("/data/tests/getUserTests")
	@ResponseBody
	public Iterable<UserAnswers> selectUserTests() {
		return adminUtils.getAllUserTests();
	}

	@RequestMapping("/data/tests/getUsers")
	@ResponseBody
	public Iterable<User> getUsers() {
		return adminUtils.selectAllUsers();
	}
	
	@RequestMapping("/data/tests/userAnswers")
	@ResponseBody
	public Iterable<UserAnswers> getSelectedUserAnswers(@RequestParam("userID") Long userID, @RequestParam("testID") Long testID, HttpServletRequest request) {
	return adminUtils.selectCurrentUserTest(userID, testID);
	}
	

	@RequestMapping("/data/tests/questionChoices")
	@ResponseBody
	public Iterable<QuestionChoices> getQuestionChoices(@RequestParam("testID") Long testID, HttpServletRequest request) {
		return adminUtils.selectCurrentQuestionChoices(testID);
	}


	@RequestMapping("/data/tests/deleteQuestionChoices")
	@ResponseBody
	public void deleteQuestionChoice(@RequestParam("choiceID") Long choiceID, HttpServletRequest request) {
		
		adminUtils.deleteChoice(choiceID);
	}
	
	@RequestMapping("/data/tests/deleteQuestion")
	@ResponseBody
	public void deleteQuestion(@RequestParam("questionID") Long questionID, HttpServletRequest request) {
		Iterable<QuestionChoices> choices = adminUtils.selectCurrentQuestionChoices(questionID);
		for (QuestionChoices questionChoices : choices) {
			if(questionChoices.getTestQuestion().getId()==questionID){
			adminUtils.deleteChoice(questionChoices.getId());
			}
		}
		adminUtils.deleteQuestion(questionID);
	}
	
	@RequestMapping("/data/tests/getActiveUser")
	@ResponseBody
	public long getUser(){
		User newuser=adminUtils.getActiveUser(context);
		return newuser.getId();
		
	}
	
	@Autowired
	private HttpServletRequest context;
	
	@Autowired
	LoginUtils loginUtils = new LoginUtils();
	
	@Autowired
	AdminUtils adminUtils = new AdminUtils();
	
	MainUtils utils = new MainUtils();
}
