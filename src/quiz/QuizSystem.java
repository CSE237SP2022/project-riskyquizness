package quiz;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class that represents the Quiz management system that runs the entire program
 * @author Christina Cai, Guenevere Chang, Jiwon Choi, Katherine Zhou
 *
 */
public class QuizSystem {

	public enum Types {
		INT,
		STRING,
		CHAR,
		BOOL
	}

	public ArrayList<Quiz> quizzes;
	private Scanner reader;

	public QuizSystem() {
		this.quizzes = new ArrayList<Quiz>();
		this.reader = new Scanner(System.in);
	}
	
	public QuizSystem(Scanner reader) {
		this.quizzes = new ArrayList<Quiz>();
		this.reader = reader;
	}

	public Scanner getReader() {
		return reader;
	}

	/**
	 * A method that allows users to create a quiz. Guides users through the creation process in the command line.
	 */
	public void createQuiz() {
		Quiz new_quiz = new Quiz(this);

		String quiz_name = questionAndReadInput("What is the name of the quiz?", Types.STRING);
		if (!new_quiz.setQuizName(quiz_name)){
			return;
		}

		String num_questions_string = questionAndReadInput("How many questions are in your quiz?", Types.INT);
		if (!new_quiz.setNumQuestions(num_questions_string)) {
			return;
		}

		if (!new_quiz.addQuestion(new_quiz.getNumQuestions())) {
			return;	
		}

		this.quizzes.add(new_quiz);
		this.quizzes.get(this.quizzes.size()-1).previewQuiz();

	}
	
	/**
	 * A method that displays the question, and reads in the input. If the input is invalid it will repeatedly ask the question.
	 * @param question String variable with the question
	 * @param t the expected type of the user input
	 * @return the user response if the input is valid
	 */
	public String questionAndReadInput(String question, Types t) {
		String response = "";
		boolean validInput = false;
		
		while (!validInput) {
			System.out.println(question);
			response = this.reader.nextLine();

			if (response.toUpperCase().equals("CANCEL")) {
				cancelQuiz();
				return "CANCEL";
			}
			
			if (t == Types.STRING) {
				validInput = checkValidString(response);
			}
			if (t == Types.INT) {
				validInput = checkValidInt(response);
			} 
			else if (t == Types.BOOL) {
				validInput = checkValidBool(response);
			}
		}
		return response;
	}
	
	/**
	 * A method that displays the question, and reads in the input. If the input is invalid it will repeatedly ask the question.
	 * @param question String variable with the question
	 * @param t the expected type of the user input
	 * @param constraint int variable containing the constraint on the input
	 * @return the user response if the input is valid
	 */
	public String questionAndReadInput(String question, Types t, int constraint) {
		String response = "";
		boolean validInput = false;

		while (!validInput) {
			System.out.println(question);
			response = this.reader.nextLine();

			if (response.toUpperCase().equals("CANCEL")) {
				cancelQuiz();
				return "CANCEL";
			}
			else {
				if (t == Types.CHAR) {
					validInput = checkValidChar(response, constraint);
				}
				if (t == Types.INT) {
					validInput = checkValidInt(response, constraint);
				}
			} 
		}
		return response.toUpperCase();
	}
	
	/**
	 * A method that displays the question, and reads in the input. If the input is invalid it will repeatedly ask the question.
	 * @param question String variable with the question
	 * @param t the expected type of the user input
	 * @param constraint int variable containing the constraint on the input
	 * @return the user response if the input is valid
	 */
	public String questionAndReadInputTaking(String question, Types t, int constraint) {
		String response = "";
		boolean validInput = false;

		while (!validInput) {
			System.out.println(question);
			response = this.reader.nextLine();

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

	public static boolean checkValidString(String valueToCheck) {
		if (valueToCheck.length()>0) {
			return true;
		}
		else {
			invalidInput();
			return false;
		}
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
	
	public static boolean checkValidInt(String valueToCheck, int constraint) {
		try {
			int value = Integer.parseInt(valueToCheck);
			if (value == 0 || value > constraint) {
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
	
	public static boolean checkValidBool(String valueToCheck) {
		if (valueToCheck.toUpperCase().equals("YES") || valueToCheck.toUpperCase().equals("NO")) {
			return true;
		}
		else {
			invalidInput();
			return false;
		}
	}

	/**
	 * A method that calls the takeQuiz function for the choosen quiz
	 * @param quizNum
	 */
	public void takeQuizSelection(int quizNum) {
		this.quizzes.get(quizNum-1).takeQuiz();
	}

	/**
	 * A method that prompts the user to select whether or not they want to create or take a quiz, and prompts the corresponding methods
	 */
	public void quizSelection() {
		String user_selection  = questionAndReadInput("Would you like to create or take a quiz? (type 'create' or 'take')", Types.STRING);

		if (user_selection.toLowerCase().equals("create")) {
			createQuiz();
		}
		else if (user_selection.toLowerCase().equals("take")) {
			if (this.quizzes.size() == 0) {
				System.out.println("No quizzes available.");
				return;
			}
			displayQuizzes();
			int quiz_num = Integer.parseInt(questionAndReadInput("Which quiz would you like to take? Please input the number.", Types.INT));
			takeQuizSelection(quiz_num);
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
		System.out.println("- After creating your quiz you will be shown a preview and at this point you make choose to edit your quiz by answering the prompt");
		System.out.println("- At any point in the program if you would like to return to main prompt type 'cancel'");
		System.out.println("Have fun quizzing!");
		System.out.println("\n____________________________\n");
	}

	public static void main(String[] args) {
		QuizSystem quiz_list = new QuizSystem();
		
		quiz_list.instructions();

		while (true) {
			quiz_list.quizSelection();
		}

	}

}
