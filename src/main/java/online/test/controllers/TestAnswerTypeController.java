package online.test.controllers;

import online.test.models.TestAnswerType;
import online.test.models.User;
import online.test.models.dao.TestAnswerTypeDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestAnswerTypeController {
	
	@RequestMapping("/data/testanswertype/selectall")
	@ResponseBody
	public Iterable<TestAnswerType> selectAll() {
		Iterable<TestAnswerType> list = testAnswerTypeDao.findAll();
		return list;
	}
	
} //TestAnswerTypeController end