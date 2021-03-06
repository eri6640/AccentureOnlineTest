package online.test.utils;

import java.security.SecureRandom;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.web.csrf.CsrfToken;

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
	
	MainUtils utils = new MainUtils();	
	static final String AB = "0147SUfikyz";
	static SecureRandom rnd = new SecureRandom();

	
	public void deleteChoice(Long choiceID){

		questionChoiceDao.delete(choiceID);
	}
	

	public void deleteQuestion(Long questionID) {
		testQuestionsDao.delete(questionID);
	}

	public Iterable<UserAnswers> getAllUserTests() {
		List<UserAnswers>  list = new ArrayList<UserAnswers>();
		boolean repeat=false;
		Iterable<UserAnswers> userTestList = userAnswerDao.findAll();
		Iterable<Tests>tests=testsDao.findAll();
		for (Tests test : tests) {
			repeat=false;
			for (UserAnswers answer : userTestList) {
				if(answer.getTests().getId()==test.getId()&&repeat==false){
					list.add(answer);
					repeat=true;
				}
			}
		}
		return list;
	}

	public void addQuestion(int type, Long testID, Long userID, String question, String answer) {
		TestQuestions testQuestions = new TestQuestions(type, question, testsDao.findById(testID), userDao.findById(userID), answer);
		testQuestionsDao.save(testQuestions);
	}

	public void addChoices(Long questionID, String choice1, String choice2, String choice3, String choice4) {
		TestQuestions testQuestions = testQuestionsDao.findOne(questionID);

		if (!(choice1.equals("")) && !(choice1.equals(null)) && !(choice1.equals("undefined")) ) {
			QuestionChoices choice = new QuestionChoices();
			choice.setTestQuestion(testQuestions);
			choice.setQuestionOption(choice1);
			questionChoiceDao.save(choice);
		}

		if (!(choice2.equals("")) && !(choice2.equals(null)) && !(choice2.equals("undefined")) ) {
			QuestionChoices choice = new QuestionChoices();
			choice.setTestQuestion(testQuestions);
			choice.setQuestionOption(choice2);
			questionChoiceDao.save(choice);
		}

		if (!(choice3.equals("")) && !(choice3.equals(null)) && !(choice3.equals("undefined")) ) {
			QuestionChoices choice = new QuestionChoices();
			choice.setTestQuestion(testQuestions);
			choice.setQuestionOption(choice3);
			questionChoiceDao.save(choice);
		}

		if (!(choice4.equals("")) && !(choice4.equals(null)) && !(choice4.equals("undefined")) ) {
			QuestionChoices choice = new QuestionChoices();
			choice.setTestQuestion(testQuestions);
			choice.setQuestionOption(choice4);
			questionChoiceDao.save(choice);
		}
	}
	
	public void updateQuestion(int questionType, Long questionID) {
				TestQuestions testQuestion = testQuestionsDao.findOne(questionID);
				testQuestion.setType(questionType);
				testQuestionsDao.save(testQuestion);
		}
	
	public Iterable<TestQuestions> getAllTestsQuestions(Long testID) {
		Iterable<TestQuestions> testQuestions = testQuestionsDao.getCurrentTestQuestions(testID);
		return testQuestions;
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
		Iterable<UserAnswers> questionList = userAnswerDao.getCurrentUserTestAnswers(testID, userID);
		return questionList;
	}
	
	

	public String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();

	}

	public boolean send(String email, String password) {

		MimeMessage mail = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
			helper.setTo(email);
			helper.setReplyTo("accbootcamp2016.2@gmail.com");
			helper.setFrom("accbootcamp2016.2@gmail.com");
			helper.setSubject("Jūsu piekļuve Accenture testam");
			helper.setText("Jūsu piekļuves dati ir: \n\nLietotājvārds: " + email + "\nParole: " + password
					+ "\n\nVeiksmi testā!");
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		javaMailSender.send(mail);
		return true;
	}

	public Iterable<QuestionChoices> selectCurrentQuestionChoices(Long testID) {
		List<QuestionChoices> choices = new ArrayList<QuestionChoices>();
		Iterable<QuestionChoices> choiceList = questionChoiceDao.findAll();
		
	
			for (QuestionChoices questionChoices : choiceList) {
				if (testID == questionChoices.getTestQuestion().getId()) {
					choices.add(questionChoices);
				}
			}
		return (Iterable<QuestionChoices>) choices;
	}



	public User getActiveUser(HttpServletRequest request) {

		CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

		String token = csrf.getToken();

		String token_hash = utils.MD5(token);
		User token_user = null;

		try {
			token_user = userDao.findByToken(token_hash);
		} catch (Exception error) {
			token_user = null;
			utils.showThis("user null");
		}

		return token_user;

	}
	

	@Autowired
	UserAnswersDao userAnswerDao;
	@Autowired
	TestsDao testsDao;
	@Autowired
	UserDao userDao;
	@Autowired
	TestQuestionsDao testQuestionsDao;
	@Autowired
	QuestionChoicesDao questionChoiceDao;
	@Autowired
	private JavaMailSender javaMailSender;
}