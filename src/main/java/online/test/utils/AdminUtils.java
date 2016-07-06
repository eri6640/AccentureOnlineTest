package online.test.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class AdminUtils{
	
	
	
	public Iterable<UserAnswers> getAllUserTests(){
		Iterable<UserAnswers> userTestList = userAnswerDao.findAll();
		return userTestList;
	}
	
	public Iterable<Tests> selectAllTests() {
		Iterable<Tests> testList = testsDao.findAll();
		return testList;
	}
	
	public Iterable<User> selectAllUsers() {
		Iterable<User> userList = userDao.findAll();
		return userList;
	}
	
	public Iterable<UserAnswers> selectCurrentUserTest(Long userID, Long testID) {
		Iterable<UserAnswers> questionList =userAnswerDao.getCurrentUserTestAnswers(testID, userID);
		return questionList;
	}
//	public Iterable<QuestionChoices> selectCurrentQuestionChoices(Long testID) {
//		
//		Iterable<QuestionChoices> choiceList = questionChoiceDao.findAll();
//		Iterable<TestQuestions> questionList = testQuestions.getCurrentTestQuestions(testID);
//		
//		for (TestQuestions testQuestions : questionList) {
//			for (QuestionChoices questionChoices : choiceList) {
//				if(testQuestions.getId()==questionChoices.getTestQuestion().getId()){
//					Iterable<QuestionChoices>
//				}
//			}
//		}
//		return;
//	}
	
	@Autowired
	UserAnswersDao userAnswerDao;
	@Autowired
	TestsDao testsDao;
	@Autowired
	UserDao userDao;
	@Autowired
	TestQuestionsDao testQuestions;
	@Autowired
	QuestionChoicesDao questionChoiceDao;
}