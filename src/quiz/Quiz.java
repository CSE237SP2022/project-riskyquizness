package quiz;

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
	
	public static void main(String[] args) {
		Quiz quiz = createQuiz();
		
	}
	
	public static Quiz createQuiz() {
		Scanner reader = new Scanner(System.in);
		System.out.println("What is the name of the quiz?");
		String quiz_name = reader.nextLine();
		
		System.out.println("How many questions are in your quiz?");
		int num_questions = Integer.parseInt(reader.nextLine());
		
		Question[] quiz_questions = new Question[num_questions];
		for (int i = 0; i < num_questions; i++) {
			quiz_questions[i] = addQuestion(i, reader);
		}
		
		Quiz new_quiz = new Quiz(quiz_questions, quiz_name, num_questions);
		
		return new_quiz;
	}
	
	public static Question addQuestion(int num, Scanner reader) {
		System.out.println("Question " + (num + 1));
		String question = reader.nextLine();
		
		System.out.println("How many possible answers are there?");
		int num_possible = Integer.parseInt(reader.nextLine());
		
		String[] possible_answers = new String[num_possible];
		for (int i = 0; i < num_possible; i++) {
			System.out.println("Answer Choice " + (i + 1));
			String answer_choice = reader.nextLine();
			possible_answers[i] = answer_choice;
		}
		
		Question current_question = new Question(question, possible_answers);
		
		while (current_question.correct_answer == ' ') {
			System.out.println("What is the correct answer?");
			System.out.println(current_question.possibleAnswersToString());
			
			String inputted_correct_answer = reader.nextLine();
			if (inputted_correct_answer.length() == 1) {
				if (Character.isLetter(inputted_correct_answer.charAt(0))) {
					current_question.correct_answer = Character.toUpperCase(inputted_correct_answer.charAt(0));
				}
				else {
					invalidInput();
				}
			}
			else{
				invalidInput();
			}
		}
		
		return current_question;
	}
	
	public void takeQuiz() {
		int score = 0;
		Scanner reader = new Scanner(System.in);
		for (int i=0; i<this.questions.length;i++) {
			System.out.println(this.questions[i]); // prompt
			// Here, the user will input
			String userAnswer = reader.nextLine();
			char firstChar = userAnswer.charAt(0);
			int firstIntVal = firstChar;
			// Error when invalid
			while (userAnswer.length()!=1 || firstIntVal<65 || firstIntVal >= 65 + this.questions[i].getNumPossibleAnswers()) {
				System.out.println("Please enter a valid input.");
				userAnswer = reader.nextLine();
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
	
	public static void invalidInput() {
		System.out.print("Invalid input. ");
	}
	
	
}
