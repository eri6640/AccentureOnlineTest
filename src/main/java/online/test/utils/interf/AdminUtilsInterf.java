package online.test.utils.interf;

import javax.servlet.http.HttpServletRequest;

import online.test.models.QuestionChoices;
import online.test.models.TestQuestions;
import online.test.models.Tests;
import online.test.models.User;
import online.test.models.UserAnswers;

public interface AdminUtilsInterf{
	
	public void deleteChoice(Long choiceID);
	
	public void deleteQuestion(Long questionID);

	public Iterable<UserAnswers> getAllUserTests();

	public void addQuestion(int type, Long testID, Long userID, String question, String answer);

	public void addChoices(Long questionID, String choice1, String choice2, String choice3, String choice4);
	
	public void updateQuestion(int questionType, Long questionID) ;
	
	public Iterable<TestQuestions> getAllTestsQuestions(Long testID);

	public Iterable<Tests> selectAllTests();

	public Iterable<User> selectAllUsers();

	public Iterable<UserAnswers> selectCurrentUserTest(Long userID, Long testID);

	public String randomString(int len);

	public boolean send(String email, String password);

	public Iterable<QuestionChoices> selectCurrentQuestionChoices(Long testID);

	public User getActiveUser(HttpServletRequest request);
}