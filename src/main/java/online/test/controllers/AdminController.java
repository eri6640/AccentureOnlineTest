package online.test.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import online.test.models.QuestionChoices;
import online.test.models.TestQuestions;
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

	
	@RequestMapping("/data/tests/addQuestion")
	@ResponseBody
	public Boolean addQuestions(@RequestParam("testID") Long testID,@RequestParam("userID") Long userID,@RequestParam("question") String question) {
		adminUtils.addQuestion(testID, userID, question);
		return true;
	}
	
	@RequestMapping("/data/tests/addChoices")
	@ResponseBody
	public Boolean addChoices(@RequestParam("questionID") Long questionID,@RequestParam("choice1") String choice1,@RequestParam("choice2") String choice2,@RequestParam("choice3") String choice3,@RequestParam("choice4") String choice4) {
		adminUtils.addChoices(questionID, choice1, choice2, choice3, choice4);
		return true;
	}
	
	@RequestMapping("/data/tests/getTestsQuestions")
	@ResponseBody
	public Iterable<TestQuestions> selectTestsQuestions(@RequestParam("testID") Long testID) {
		return adminUtils.getAllTestsQuestions(testID);
	}
	
	@RequestMapping("/data/tests/getUsers")
	@ResponseBody
	public Iterable<User> getUsers() {
		return adminUtils.selectAllUsers();
	}

	@RequestMapping("/data/tests/userAnswers")
	@ResponseBody
	public Iterable<UserAnswers> getSelectedUserAnswers(@RequestParam("userID") Long userID,
			@RequestParam("testID") Long testID, HttpServletRequest request) {
		return adminUtils.selectCurrentUserTest(userID, testID);
	}

	@RequestMapping("/data/tests/questionChoices")
	@ResponseBody
	public Iterable<QuestionChoices> getQuestionChoices(@RequestParam("testID") Long testID,
			HttpServletRequest request) {
		return adminUtils.selectCurrentQuestionChoices(testID);
	}

	@RequestMapping("/data/tests/deleteQuestionChoices")
	@ResponseBody
	public Boolean deleteQuestionChoice(@RequestParam("choiceID") Long choiceID, HttpServletRequest request) {
		adminUtils.deleteChoice(choiceID);
		return true;
	}

	@RequestMapping("/data/tests/deleteQuestion")
	@ResponseBody
	public Boolean deleteQuestion(@RequestParam("testsID") Long questionID, @RequestParam("questionID") Long testsID,
			HttpServletRequest request) {
		boolean questionExists = false;
		Iterable<UserAnswers> userAnswers = adminUtils.getAllUserTests();
		for (UserAnswers userAnswers2 : userAnswers) {
			if (userAnswers2.getTestsQuestions().getId() == questionID) {
				questionExists = true;
			}
		}
		if (questionExists == false) {
			Iterable<QuestionChoices> choices = adminUtils.selectCurrentQuestionChoices(testsID);
			for (QuestionChoices questionChoices : choices) {
				if (questionChoices.getTestQuestion().getId() == questionID) {
					adminUtils.deleteChoice(questionChoices.getId());
				}
			}
			adminUtils.deleteQuestion(questionID);
			return false;
		} else {
			return true;
		}
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
