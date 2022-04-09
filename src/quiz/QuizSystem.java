package quiz;

import java.util.ArrayList;
import java.util.Scanner;

public class QuizSystem {
	
	enum Types {
		INT,
		STRING,
		CHAR
	}
	
	public ArrayList<Quiz> quizzes;
	
	public QuizSystem() {
		this.quizzes = new ArrayList<Quiz>();
	}
	
	public void createQuiz(Scanner reader) {
		Quiz new_quiz = new Quiz();
		
		String quiz_name = questionAndReadInput("What is the name of the quiz?", reader, Types.STRING);
		if (!new_quiz.setQuizName(quiz_name)){
			return;
		}
		
		String num_questions_string = questionAndReadInput("How many questions are in your quiz?", reader, Types.INT);
		if (!new_quiz.setNumQuestions(num_questions_string)) {
			return;
		}
		
		if (!new_quiz.addQuestion(new_quiz.getNumQuestions(), reader)) {
			return;	
		}
		
		this.quizzes.add(new_quiz);
		this.quizzes.get(this.quizzes.size()-1).previewQuiz();
						
	}
	
	public static String questionAndReadInput(String question, Scanner reader, Types t) {
		System.out.println(question);
		String response = reader.nextLine();
		
		if (response.toUpperCase().equals("CANCEL")) {
			cancelQuiz();
			return "CANCEL";
		}
		else {
		// check validity
//			if (num_questions == 0) {
//			invalidInput();
//		}
			return response;
		} 
	}
	
	
	public void takeQuizSelection(int quizNum, Scanner reader) {
		this.quizzes.get(quizNum-1).takeQuiz(reader);
	}
	
	public void quizSelection(Scanner reader) {
		String user_selection  = questionAndReadInput("Would you like to create or take a quiz? (type 'create' or 'take')", reader, Types.STRING);
		
		if (user_selection.toLowerCase().equals("create")) {
			createQuiz(reader);
		}
		else if (user_selection.toLowerCase().equals("take")) {
			displayQuizzes();
			int quiz_num = Integer.parseInt(questionAndReadInput("Which quiz would you like to take? Please input the number.", reader, Types.INT));
			takeQuizSelection(quiz_num, reader);
		}
		else {
			invalidInput();
		}
		
	}
	
	public void displayQuizzes() {
		for (int i = 0; i < this.quizzes.size(); i++) {
			System.out.println((i+1) + " " + this.quizzes.get(i).getQuizName());
		}
	}
	
	public static void invalidInput() {
		System.out.print("Invalid input. ");
	}
	
	public static void cancelQuiz() {
		System.out.println("\nQuiz Creation Cancelled");
		System.out.println("____________________________");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner reader = new Scanner(System.in);
		QuizSystem quiz_list = new QuizSystem();
		
		while (true) {
			quiz_list.quizSelection(reader);
		}

	}

}
