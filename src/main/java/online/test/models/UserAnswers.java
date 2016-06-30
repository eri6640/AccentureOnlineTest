package online.test.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "userAnswers")
public class UserAnswers{
	  
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private long id;
	  
	  @ManyToOne
	  @JoinColumn(name = "questionID")
	  private TestOuestions testsQuestions;
	  
	  
	  @ManyToOne
	  @JoinColumn(name = "testsID")
	  private Tests tests;
	  
	  
	  @ManyToOne
	  @JoinColumn(name = "userID")
	  private User user;

	  @NotNull
	  private String answer;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TestOuestions getTestsQuestions() {
		return testsQuestions;
	}

	public void setTestsQuestions(TestOuestions testsQuestions) {
		this.testsQuestions = testsQuestions;
	}

	public Tests getTests() {
		return tests;
	}

	public void setTests(Tests tests) {
		this.tests = tests;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	  
	  
	  
	
}