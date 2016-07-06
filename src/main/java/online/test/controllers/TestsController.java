package online.test.controllers;

import online.test.models.Tests;
import online.test.models.User;
import online.test.models.dao.TestsDao;
import online.test.utils.MainUtils;
import online.test.utils.TestsUtils;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestsController {
	
	@Autowired
	private TestsDao testsDao;
	MainUtils utils = new MainUtils();
	
	@Autowired
	TestsUtils testsUtils = new TestsUtils();

	
	@RequestMapping("/data/tests/getTest")
	@ResponseBody
	public Map<String,Object> getTest( @RequestParam("testId") String testId_string ) {
		return testsUtils.getTest( testId_string );
	}
	
	
	@RequestMapping("/data/tests/selectallTests")
	@ResponseBody
	public Iterable<Tests> selectAll() {
		Iterable<Tests> list = testsDao.findAll();
		return list;
	}
	
	@RequestMapping("/data/tests/create")
	@ResponseBody
	public boolean addTest(String title, User user){
		Tests test = null;
		try {
			test = new Tests(title, user);
			testsDao.save(test);
		}
		catch (Exception ex) {
			return false;
		}
		return true;
	}
	
	@RequestMapping("/data/tests/remove")
	@ResponseBody
	public boolean removeTest( String title, User user ){
		Tests test = null;
		try {
			test = new Tests( title, user );
			testsDao.delete( test );
		}
		catch ( Exception ex ) {
			return false;
		}
		return true;
	}
	  
} //TestsController end