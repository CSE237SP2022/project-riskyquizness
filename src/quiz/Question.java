package quiz;

public class Question {
	
	private String question;
	private String[] possible_answers;
	private char correct_answer;
	
	public Question(String question, String[] possible_answers, char correct_answer) {
		this.question = question;
		this.possible_answers = possible_answers;
		this.correct_answer = correct_answer;
	}
	
	public String get_question() {
		return this.question;
	}
	
	public String[] get_possible_answers() {
		return this.possible_answers;
	}
	
	public char get_correct_answer() {
		return this.correct_answer;
	}
	
	public boolean check_answer(char user_answer) {
		return user_answer == this.correct_answer;
	}
	



}
