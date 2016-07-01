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
@Table(name = "testAnswerType")
public class TestAnswerType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "testQuestionID")
	private TestQuestions testQuestion;

	@NotNull
	private String type;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TestQuestions getTestQuestion() {
		return testQuestion;
	}

	public void setTestQuestion(TestQuestions testQuestion) {
		this.testQuestion = testQuestion;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TestAnswerType(TestQuestions testQuestion, String type) {
		this.testQuestion = testQuestion;
		this.type = type;
	}

	
	

}