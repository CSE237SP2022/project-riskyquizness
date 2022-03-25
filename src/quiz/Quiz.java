package quiz;

import java.util.Arrays;
import java.util.Scanner;


public class Quiz {
	private Question[] questions;
	private String quiz_name;
	private int num_questions;
	
	public Quiz() {
		
	}
	
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
	
	public static void main(String[] args) {
		Quiz new_quiz = new Quiz();
	}
	
	public void createQuiz() {
		Scanner reader = new Scanner(System.in);
		System.out.println("What is the name of the quiz?");
		this.quiz_name = reader.next();
		System.out.println("How many questions are in your quiz?");
		this.num_questions = Integer.parseInt(reader.next());
		
		this.questions = new Question[this.num_questions];
		for (int i = 0; i < this.num_questions; i++) {
			this.questions[i] = addQuestion(i, reader);
		}
		
	}
	
	public Question addQuestion(int num, Scanner reader) {
		System.out.println("Question " + num);
		String question = reader.next();
		System.out.println("How many possible answers are there?");
		int num_possible = Integer.parseInt(reader.next());
		String[] possible_answers = new String[num_possible];
		for (int i = 0; i < num_possible; i++) {
			System.out.println("Answer Choice " + i);
			String answer_choice = reader.next();
			possible_answers[i] = answer_choice;
		}
		System.out.println("What is the correct answer?");
		char correct = reader.next().charAt(0);
		Question current_question = new Question(question, possible_answers, correct);
		return current_question;
	}
	
	public void takeQuiz() {
		int score = 0;
		Scanner reader = new Scanner(System.in);
		for (int i=0; i<this.questions.length;i++) {
			System.out.println(this.questions[i]); // prompt
			// Here, the user will input
			String userAnswer = reader.next();
			char firstChar = userAnswer.charAt(0);
			int firstIntVal = firstChar;
			// Error when invalid
			while (userAnswer.length()!=1 || firstIntVal<65 || firstIntVal >= 65 + this.questions[i].getNumPossibleAnswers()) {
				System.out.println("Please enter a valid input.");
				userAnswer = reader.next();
				firstChar = userAnswer.charAt(0);
				firstIntVal = firstChar;
			}
			// Now we have valid input
			if (firstChar==this.questions[i].getCorrectAnswer()) {
				score++;
			}
		}
		reader.close();
		double percentage = (double)score/this.num_questions * 100.0;
		System.out.println("You got "+ percentage + "% of this quiz correct.");
		return;
	}
	
	
}
