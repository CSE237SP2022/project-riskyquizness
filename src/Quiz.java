import java.util.Arrays;
import java.util.Scanner;

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
		Scanner reader = new Scanner(System.in);
		for (int i=0; i<this.questions.length;i++) {
			System.out.println(this.questions[i]); // prompt
			// Here, the user will input
			String userAnswer = reader.next();
			char firstChar = userAnswer.charAt(0);
			// Error when invalid
			while (userAnswer.length()!=1 || firstChar<65 || firstChar >= 65 + this.questions[i].getNumPossibleAnswers()) {
				System.out.println("Please enter a valid input.");
				userAnswer = reader.next();
				firstChar = userAnswer.charAt(0);
			}
		}
		reader.close();
		return;
	}
	
	
}
