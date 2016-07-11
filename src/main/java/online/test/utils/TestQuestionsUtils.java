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
	@Autowired
	UserAnswersDao userAnswerDao;
	
	MainUtils utils = new MainUtils();
	
	public TestQuestions getQuestion( String questionId_string ){
		
		if( questionId_string.isEmpty() ) return null;
		int question_id = 0;
		try{
			question_id = Integer.parseInt( questionId_string );
		}
		catch( Exception error ){
			return null;
		}
		
		return getQuestion( question_id );
	}
	
	public TestQuestions getQuestion( int question_id ){
		
		if( question_id <= 0 ) return null;
		
		TestQuestions question_question = null;
		
		try{
			question_question = questionDao.findById( (long)question_id );
		}
		catch( Exception error ){
			utils.showThis( "TestQuestions null, id:" + question_id );
			return null;
		}
		
		return question_question;
	}
	
	public TestQuestions getUserNextQuestion( User user_user, Tests test_test ){
		
		List<TestQuestions> question_list = questionDao.getCurrentTestQuestions( test_test.getId() );
		
		if( question_list.isEmpty() ) return null;
		
		for( TestQuestions question : question_list ){
			
			if( ! isAlreadyAnswered( user_user, question ) && ! isPinPointed( user_user, question ) ){
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
	
	public TestQuestions getUserLastQuestion( User user_user, Tests test_test ){
		
		List<TestQuestions> question_list = questionDao.getLastTestQuestions( test_test.getId() );
		
		if( question_list.isEmpty() ){
			utils.showThis( "question_list null" );
			utils.showThis( "question_list null" );
			utils.showThis( "question_list null" );
			return null;
		}
		
		return question_list.get( 0 );
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
	
	public int getPinPointedAnswerId( User user_user, TestQuestions quest_quest ){
		
		List<UserAnswers> answer_list = userAnswersDao.getCurrentUserTestAnswersPinPointed( quest_quest.getTests().getId(), user_user.getId() );
		
		if( answer_list.isEmpty() ) return 0;
		
		for( UserAnswers answer : answer_list ){
			if( answer.getTestsQuestions().getId() == quest_quest.getId() ){
				return (int) answer.getId();
			}
		}
		
		return 0;
	}
	
	
	public boolean saveAnswer( User user_user, TestQuestions question, String answer ){
		
		if( question == null || answer.isEmpty() ) return false;
		
		UserAnswers user_answer = null;
		
		if( question.getType() != 4 ){
			user_answer = new UserAnswers( question, question.getTests(), user_user, answer, "", 3 );// 3==izpildits
		}
		else{
			user_answer = new UserAnswers( question, question.getTests(), user_user, "", answer, 3 );// 3==izpildits
		}
		
		int pined_answerId = 0;
		
		if( ( pined_answerId = getPinPointedAnswerId( user_user, question ) ) > 0  ){
			userAnswerDao.delete( (long) pined_answerId );
		}
		
		userAnswersDao.save( user_answer );
		
		return true;		
	}
	
	public boolean pinPoint( User user_user, TestQuestions question ){
		
		UserAnswers user_answer = new UserAnswers( question, question.getTests(), user_user, null, null, 2 );
		
		userAnswersDao.save( user_answer );
		return true;
	}
	
	
	
}
