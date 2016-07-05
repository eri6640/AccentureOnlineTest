package online.test.controllers;


import online.test.models.TestQuestions;
import online.test.models.dao.TestQuestionsDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestQuestionsController {
	
	@RequestMapping("/data/testquestions/selectall")
	@ResponseBody
	public Iterable<TestQuestions> selectAll() {
		Iterable<TestQuestions> list = testQuestionsDao.findAll();
		return list;
	}
	  @Autowired
	  private TestQuestionsDao testQuestionsDao;
	 
} //TestsQuestionsController end