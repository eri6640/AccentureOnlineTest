package online.test.post.classes;

public class AnswerCountObj {
	
	private int answers;
	private int pinpointed_answers;
	private int questions;
	
	
	
	public AnswerCountObj(int answers, int pinpointed_answers, int questions) {
		this.answers = answers;
		this.pinpointed_answers = pinpointed_answers;
		this.questions = questions;
	}
	
	public int getAnswers() {
		return answers;
	}
	public void setAnswers( int answers ) {
		this.answers = answers;
	}
	public int getPinpointedAnswers() {
		return pinpointed_answers;
	}
	public void setPinpointedAnswers( int pinpointed_answers ) {
		this.pinpointed_answers = pinpointed_answers;
	}
	public int getQuestions() {
		return questions;
	}
	public void setQuestions( int questions ) {
		this.questions = questions;
	}

}
