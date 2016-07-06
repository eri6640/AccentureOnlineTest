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
	
	
	public TestQuestions() {
		
	}

	public TestQuestions(TYPE type, String question, String date, Tests tests, User user, String answer,
			String multipleChoice, byte[] imageAnswer, String textAnswer) {
		this.type = type;
		this.question = question;
		this.date = date;
		this.tests = tests;
		this.user = user;
		this.answer = answer;
		this.multipleChoice = multipleChoice;
		this.imageAnswer = imageAnswer;
		this.textAnswer = textAnswer;
	}

	public static enum TYPE {
		PICTURE('P'), MULTIPLE_CHOCE('M'), SINGLE_CHOICE('S'), TEXT('T');

		private Character c;

		TYPE(Character c) {
			this.c = c;
		}

		public Character getValue() {
			return c;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column
	private TYPE type = TYPE.SINGLE_CHOICE;

	@Column
	private String question;

	@Column
	private String date = "01/01/2000";

	@ManyToOne
	@JoinColumn(name = "testsID")
	private Tests tests;

	@ManyToOne
	@JoinColumn(name = "userID")
	private User user;

	// All the answers
	@Column
	private String answer = "0";

	@Column
	private String multipleChoice = "0";

	@Column
	private byte[] imageAnswer = null;

	@Column
	private String textAnswer;

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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getMultipleChoice() {
		return multipleChoice;
	}

	public void setMultipleChoice(String multipleChoice) {
		this.multipleChoice = multipleChoice;
	}

	public byte[] getImageAnswer() {
		return imageAnswer;
	}

	public void setImageAnswer(byte[] imageAnswer) {
		this.imageAnswer = imageAnswer;
	}

	public String getTextAnswer() {
		return textAnswer;
	}

	public void setTextAnswer(String textAnswer) {
		this.textAnswer = textAnswer;
	}

}