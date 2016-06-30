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
@Table(name = "testOuestions")
public class TestOuestions{
	  
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private long id;
	  
	  @NotNull
	  private String question;
	  
	  @ManyToOne
	  @JoinColumn(name = "testsID")
	  private Tests tests;
	  
	  
	  @NotNull
	  private String date;
	  
	  //All the answers
	  @NotNull
	  private String answer;
	  
	  @NotNull
	  private 	String multipleAnswers;
	  
	  @NotNull
	  private byte videoAnswer;
	  
	  
	  



	public String getMultipleAnswers() {
		return multipleAnswers;
	}

	public void setMultipleAnswers(String multipleAnswers) {
		this.multipleAnswers = multipleAnswers;
	}

	public byte getVideoAnswer() {
		return videoAnswer;
	}

	public void setVideoAnswer(byte videoAnswer) {
		this.videoAnswer = videoAnswer;
	}

	@ManyToOne
	  @JoinColumn(name = "userID")
	  private User user;

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


	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	  
	
}