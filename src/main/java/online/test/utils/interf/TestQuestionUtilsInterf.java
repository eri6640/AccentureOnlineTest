package online.test.utils.interf;

import online.test.models.TestQuestions;
import online.test.models.Tests;
import online.test.models.User;

public interface TestQuestionUtilsInterf {
	
	public TestQuestions getQuestion( String questionId_string );
	
	public TestQuestions getUserNextQuestion( User user_user, Tests test_test );
	
	public TestQuestions getUserLastQuestion( User user_user, Tests test_test );
	
	public boolean isAlreadyAnswered( User user_user, TestQuestions quest_quest );
	
	public boolean isPinPointed( User user_user, TestQuestions quest_quest );
	
	public int getPinPointedAnswerId( User user_user, TestQuestions quest_quest );
	
	public boolean saveAnswer( User user_user, TestQuestions question, String answer );
	
	public boolean pinPoint( User user_user, TestQuestions question );

	TestQuestions getQuestion(int question_id);
}