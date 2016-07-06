package online.test.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import online.test.models.Tests;
import online.test.models.User;
import online.test.models.UserAnswers;
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
	
	@Autowired
	UserAnswersDao userAnswerDao;
	@Autowired
	TestsDao testsDao;
	@Autowired
	UserDao userDao;
}