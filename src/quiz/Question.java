package quiz;

public class Question {
	
	private String question;
	public String[] possible_answers;
	public char correct_answer;
	
	String[] alphabet = {"A","B","C","D","E"};
	
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
	
	public int getNumPossibleAnswers() {
		return this.possible_answers.length;
	}
	
	public char getCorrectAnswer() {
		return this.correct_answer;
	}
	
	@Override
	public String toString() {
		if (this.possible_answers.length>5) {
			return "Error";
		}
		String output = this.question;
	    for (int i=0; i<this.possible_answers.length;i++) {
	    	output += "\n"+ alphabet[i] + " " + this.possible_answers[i];
	    }
	    return output;
	}
	
}
