package online.test.controllers;

import online.test.models.Tests;
import online.test.models.User;
import online.test.models.UserAnswers;
import online.test.models.dao.TestsDao;
import online.test.models.dao.UserAnswersDao;
import online.test.models.dao.UserDao;
import online.test.utils.LoginUtils;
import online.test.utils.TestsUtils;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestsController {
	
	@Autowired
	private TestsDao testsDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserAnswersDao userAnswersDao;
	
	@Autowired
	TestsUtils testsUtils = new TestsUtils();
	
	@RequestMapping("/data/tests/selectallTests")
	@ResponseBody
	public Iterable<Tests> selectAll() {
		Iterable<Tests> list = testsDao.findAll();
		return list;
	}
	
	@RequestMapping("/data/tests/create")
	@ResponseBody
	public String addTest(String title, User user, String date){
		Tests test = null;
		try {
			test = new Tests(title, user);
			testsDao.save(test);
		}
		catch (Exception ex) {
				return "Error adding test: " + ex.toString();
		}
		return "Test succesfully added!";
	}
	
	@RequestMapping("/data/tests/remove")
	@ResponseBody
	public String removeTest(String title, User user, String date){
		Tests test = null;
		try {
			test = new Tests(title, user);
			testsDao.delete(test);
		}
		catch (Exception ex) {
				return "Error delete test: " + ex.toString();
		}
		return "Test succesfully deleted!";
	}
	
	@RequestMapping("/data/tests/selectAvailableTests")
	@ResponseBody
	public Iterable<Tests> selectAvailableTests( HttpServletRequest request ) {
		Iterable<Tests> list = testsUtils.getAvailableTests( request );
		return list;
	}
	  
} //TestsController end