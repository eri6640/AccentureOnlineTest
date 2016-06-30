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
	private TestOuestions testQuestion;

	@NotNull
	private String title;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TestOuestions getTestQuestion() {
		return testQuestion;
	}

	public void setTestQuestion(TestOuestions testQuestion) {
		this.testQuestion = testQuestion;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	

}