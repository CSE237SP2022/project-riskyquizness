
public class Quiz {
	private Question[] questions;
	private String quiz_name;
	private int num_questions;
	
	public Quiz(Question[] questions, String quiz_name, int num_questions) {
		this.questions = questions;
		this.quiz_name = quiz_name;
		this.num_questions = num_questions;
	}
	
	public Question[] getQuestions() {
		return this.questions;
	}
	
	public String getQuizName() {
		return this.quiz_name;
	}
	
	public int getNumQuestions() {
		return this.num_questions;
	}
	
	public void takeQuiz() {
		return;
	}
}
