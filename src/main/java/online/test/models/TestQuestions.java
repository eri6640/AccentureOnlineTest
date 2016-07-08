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
@Table(name = "testQuestions")
public class TestQuestions {
	
	
	public TestQuestions() {}

	
	

	public TestQuestions(int type, String question, Tests tests, User user, String answer) {
		super();
		this.type = type;
		this.question = question;
		this.tests = tests;
		this.user = user;
		this.answer = answer;
	}




	/*public static enum TYPE {
		PICTURE("P"), MULTIPLE_CHOCE("M"), SINGLE_CHOICE("S"), TEXT("T");

		private String c;

		TYPE(String c) {
			this.c = c;
		}

		public String getValue() {
			return c;
		}
	}*/

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column
	private int type;

	@Column
	private String question;

	@ManyToOne
	@JoinColumn(name = "testsID")
	private Tests tests;

	@ManyToOne
	@JoinColumn(name = "userID")
	private User user;

	// All the answers
	@Column
	private String answer = "0";


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}



}