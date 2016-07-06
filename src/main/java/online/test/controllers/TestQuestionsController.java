package online.test.controllers;


import online.test.models.TestQuestions;
import online.test.models.TestQuestions.TYPE;
import online.test.models.Tests;
import online.test.models.User;
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
	
	@RequestMapping("/data/testquestions/create")
	@ResponseBody
	public String addQuestion(TYPE type, String question, String date, Tests tests, User user, String answer,
				String multipleChoice, byte[] imageAnswer, String textAnswer){
		 TestQuestions testQuestion = null;
			try {
				testQuestion = new TestQuestions(type, question, date, tests, user, answer, multipleChoice, imageAnswer, textAnswer);
				testQuestionsDao.save(testQuestion);
			}
			catch (Exception ex) {
				return "Error adding answer: " + ex.toString();
			}
			return "Answer succesfully added!";
	 }
	 
	@RequestMapping("/data/testquestions/delete")
	@ResponseBody
	 public String removeQuestion(TYPE type, String question, String date, Tests tests, User user, String answer,
				String multipleChoice, byte[] imageAnswer, String textAnswer){
		 TestQuestions testQuestion = null;
			try {
				testQuestion = new TestQuestions(type, question, date, tests, user, answer, multipleChoice, imageAnswer, textAnswer);
				testQuestionsDao.delete(testQuestion);
			}
			catch (Exception ex) {
				return "Error deleting answer: " + ex.toString();
			}
			return "Answer succesfully deleted!";
	 }
	
	  @Autowired
	  private TestQuestionsDao testQuestionsDao;
	  
	 
	 
} //TestsQuestionsController end