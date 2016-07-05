package online.test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

//	@Autowired
	LoginUtils loginUtils = new LoginUtils();
//	@Autowired
	AdminUtils adminUtils = new AdminUtils();
	
	MainUtils utils = new MainUtils();
}
