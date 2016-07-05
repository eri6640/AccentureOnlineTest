package online.test.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "userAnswers")
public class UserAnswers {
	
	

	public UserAnswers(TestQuestions testsQuestions, Tests tests, User user, String answer, byte[] imageAnswer) {
		this.testsQuestions = testsQuestions;
		this.tests = tests;
		this.user = user;
		this.answer = answer;
		this.imageAnswer = imageAnswer;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne
	@JoinColumn(name = "questionID")
	private TestQuestions testsQuestions;

	@ManyToOne
	@JoinColumn(name = "testsID")
	private Tests tests;

	@ManyToOne
	@JoinColumn(name = "userID")
	private User user;

	@Column
	private String answer = "0";

	@Column
	private byte[] imageAnswer = null;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TestQuestions getTestsQuestions() {
		return testsQuestions;
	}

	public void setTestsQuestions(TestQuestions testsQuestions) {
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

	public byte[] getImageAnswer() {
		return imageAnswer;
	}

	public void setImageAnswer(byte[] imageAnswer) {
		this.imageAnswer = imageAnswer;
	}

}