package online.test.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import online.test.models.TestQuestions;
import online.test.models.Tests;
import online.test.models.User;
import online.test.models.UserAnswers;
import online.test.models.dao.TestQuestionsDao;
import online.test.models.dao.UserAnswersDao;

@Component
public class TestQuestionsUtils {

	@Autowired
	private TestQuestionsDao questionDao;
	@Autowired
	private UserAnswersDao userAnswersDao;
	
	public TestQuestions getUserNextQuestion( User user_user, Tests test_test ){
		
		List<TestQuestions> question_list = questionDao.getCurrentTestQuestions( test_test.getId() );
		
		if( question_list.isEmpty() ) return null;
		
		for( TestQuestions question : question_list ){
			
			if( ! isAlreadyAnswered( user_user, question ) ){
				return question;
			}
			
		}
		
		//kad it ka ir izpilditi visi jautajumi
		for( TestQuestions question : question_list ){
			
			if( isPinPointed( user_user, question ) ){
				return question;
			}
			
		}
		
		return null;
	}
	
	public boolean isAlreadyAnswered( User user_user, TestQuestions quest_quest ){
		
		List<UserAnswers> answer_list = userAnswersDao.getCurrentUserTestAnswers( quest_quest.getTests().getId(), user_user.getId() );
		
		if( answer_list.isEmpty() ) return false;
		
		for( UserAnswers answer : answer_list ){
			if( answer.getTestsQuestions().getId() == quest_quest.getId() ){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isPinPointed( User user_user, TestQuestions quest_quest ){
		
		List<UserAnswers> answer_list = userAnswersDao.getCurrentUserTestAnswersPinPointed( quest_quest.getTests().getId(), user_user.getId() );
		
		if( answer_list.isEmpty() ) return false;
		
		for( UserAnswers answer : answer_list ){
			if( answer.getTestsQuestions().getId() == quest_quest.getId() ){
				return true;
			}
		}
		
		return false;
	}
	
	
	
}
