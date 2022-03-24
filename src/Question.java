
public class Question {
	
	private String question;
	private String[] possible_answers;
	private char correct_answer;
	
	public Question(String question, String[] possible_answers, char correct_answer) {
		this.question = question;
		this.possible_answers = possible_answers;
		this.correct_answer = correct_answer;
	}
	
	public static boolean check_answer(char user_answer) {
		return true;
	}
	
	

}
