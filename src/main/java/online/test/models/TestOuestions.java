package online.test.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "testOuestions")
public class TestOuestions {

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

	@NotNull
	private TYPE type = TYPE.SINGLE_CHOICE;

	@NotNull
	private String question;

	@NotNull
	private String date;

	@ManyToOne
	@JoinColumn(name = "testsID")
	private Tests tests;

	@ManyToOne
	@JoinColumn(name = "userID")
	private User user;

	// All the answers
	@Column
	private String answer;

	@Column
	private String multipleChoice;

	@Column
	private byte[] imageAnswer;

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