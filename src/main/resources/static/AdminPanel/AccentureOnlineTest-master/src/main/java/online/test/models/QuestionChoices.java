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
@Table(name = "choices")
public class QuestionChoices {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	private String questionOption;

	@ManyToOne
	@JoinColumn(name = "testQuestionID")
	private TestQuestions testQuestion;
	
	public QuestionChoices(){}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getQuestionOption() {
		return questionOption;
	}

	public void setQuestionOption(String questionOption) {
		this.questionOption = questionOption;
	}

	public TestQuestions getTestQuestion() {
		return testQuestion;
	}

	public void setTestQuestion(TestQuestions testQuestion) {
		this.testQuestion = testQuestion;
	}

	
}