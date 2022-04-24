package quiz;

import java.util.ArrayList;
import java.util.Scanner;

public class QuizSystem {

	public enum Types {
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
		String response = "";
		boolean validInput = false;
		
		while (!validInput) {
			System.out.println(question);
			response = reader.nextLine();

			if (response.toUpperCase().equals("CANCEL")) {
				cancelQuiz();
				return "CANCEL";
			}
			
			if (t == Types.STRING) {
				validInput = true;
			}
			else if (t == Types.INT) {
				validInput = checkValidInt(response);
			} 
		}
		return response;
	}

	public static String questionAndReadInput(String question, Scanner reader, Types t, int constraint) {
		String response = "";
		boolean validInput = false;

		while (!validInput) {
			System.out.println(question);
			response = reader.nextLine();

			if (response.toUpperCase().equals("CANCEL")) {
				cancelQuiz();
				return "CANCEL";
			}
			else {
				if (t == Types.CHAR) {
					validInput = checkValidChar(response, constraint);
				}
			} 
		}
		return response.toUpperCase();
	}
	
	public static String questionAndReadInputTaking(String question, Scanner reader, Types t, int constraint) {
		String response = "";
		boolean validInput = false;

		while (!validInput) {
			System.out.println(question);
			response = reader.nextLine();

			if (response.toUpperCase().equals("CANCEL")) {
				cancelQuizTaking();
				return "CANCEL";
			}
			else {
				if (t == Types.CHAR) {
					validInput = checkValidChar(response, constraint);
				}
			} 
		}
		return response.toUpperCase();
	}

	public static boolean checkValidInt(String valueToCheck) {
		try {
			int value = Integer.parseInt(valueToCheck);
			if (value == 0) {
				invalidInput();
				return false;
			}
			return true;
		}
		catch (NumberFormatException e){
			invalidInput();
			return false;
		}
	}

	public static boolean checkValidChar(String valueToCheck, int constraint) {
		if (valueToCheck.length() != 1) {
			invalidInput();
			return false;
		}
		if (Character.isLetter(valueToCheck.charAt(0))) {
			if ((int)valueToCheck.toUpperCase().charAt(0) < 'A' + constraint) {
				return true;
			}
		}
		invalidInput();
		return false;
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
			if (this.quizzes.size() == 0) {
				System.out.println("No quizzes available.");
				return;
			}
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
	
	public static void cancelQuizTaking() {
		System.out.println("\nQuiz Taking Cancelled");
		System.out.println("____________________________");
	}
	
	public void instructions() {
		System.out.println("Welcome to Risky Quizness!");
		System.out.println("\nUser Instructions:");
		System.out.println("- If you would like to create a quiz type 'create'");
		System.out.println("- If you would like to take a quiz type 'take'");
		System.out.println("- To choose an answer choice type the corresponding letter ex. 'A'");
		System.out.println("- To choose an a quiz to take type the corresponding number ex. '1'");
		System.out.println("- At any point in the program if you would like to return to main prompt type 'cancel'");
		System.out.println("Have fun quizzing!");
		System.out.println("\n____________________________\n");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner reader = new Scanner(System.in);
		QuizSystem quiz_list = new QuizSystem();
		
		quiz_list.instructions();

		while (true) {
			quiz_list.quizSelection(reader);
		}

	}

}
