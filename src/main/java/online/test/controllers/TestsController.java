package online.test.controllers;

import online.test.models.TestQuestions;
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
	public Iterable<Tests> selectAll() {
		Iterable<Tests> list = testsDao.findAll();
		return list;
	}
	  @Autowired
	  private TestsDao testsDao;
	
	@RequestMapping("/data/tests/create")
	@ResponseBody
	public Boolean addTest(String title, User user, String date){
		Tests test = null;
		try {
			test = new Tests(title, user, date);
			testsDao.save(test);
		}
		catch (Exception ex) {
			//return "Error adding test: " + ex.toString();
			return false;
		}
			return true;
	}
	
	@RequestMapping("/data/tests/remove")
	@ResponseBody
	public Boolean removeTest(String title, User user, String date){
		Tests test = null;
		try {
			test = new Tests(title, user, date);
			testsDao.delete(test);
		}
		catch (Exception ex) {
			//return "Error delete test: " + ex.toString();
			return false;
		}
		return true;
	}
	  
} //TestsController end