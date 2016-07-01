package online.test.controllers;

import online.test.models.Tests;
import online.test.models.User;
import online.test.models.dao.TestsDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestsController {
	
	@RequestMapping("/data/tests/selectallTests")
	@ResponseBody
	public Iterable<User> selectAll() {
		Iterable<User> list = testsControllerDao.findAll();
		return list;
	}
	
} //TestsController end