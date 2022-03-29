package quiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Quiz {
	public ArrayList<Question> questions;
	private String quiz_name;
	private int num_questions;
	
	public Quiz() {
		this.questions = new ArrayList<Question>();
		this.quiz_name = "";
		this.num_questions = 0;
	}
	
	public Quiz(ArrayList<Question> questions, String quiz_name, int num_questions) {
		this.questions = questions;
		this.quiz_name = quiz_name;
		this.num_questions = num_questions;
	}
	
	public ArrayList<Question> getQuestions() {
		return this.questions;
	}
	
	public String getQuizName() {
		return this.quiz_name;
	}
	
	public int getNumQuestions() {
		return this.num_questions;
	}
	
	public void setQuizName(String quiz_name) {
		this.quiz_name = quiz_name;
	}
	
	public void setNumQuestions(int num_questions) {
		this.num_questions = num_questions;
	}
		
	public void addQuestion(int num, Scanner reader) {
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
		
		// TODO: complete check for invalid input 
		while (current_question.correct_answer == ' ') {
			System.out.println("What is the correct answer?");
			System.out.println(current_question.possibleAnswersToString());
			
			String inputted_correct_answer = reader.nextLine();
			if (inputted_correct_answer.length() == 1) {
				if (Character.isLetter(inputted_correct_answer.charAt(0))) {
					current_question.correct_answer = Character.toUpperCase(inputted_correct_answer.charAt(0));
				}
				else if (isNumeric(inputted_correct_answer)) {
					invalidInput();
				}
				else {
					invalidInput();
				}
			}
			else if (inputted_correct_answer.isEmpty()){
				invalidInput();
			}
			else {
				invalidInput();
			}
		}
		
		this.questions.add(current_question);
		if (this.questions.size() == this.num_questions) {
			this.num_questions++;
		}

	}
	
	public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }
	
	public void takeQuiz() {
		int score = 0;
		Scanner reader = new Scanner(System.in);
		
		for (int i=0; i<questions.size(); i++) {
			System.out.println(questions.get(i).get_question()); // prompt
			// Here, the user will input
			String userAnswer = reader.nextLine();
			char firstChar = userAnswer.charAt(0);
			int firstIntVal = firstChar;
			// Error when invalid
			while (userAnswer.length()!=1 || firstIntVal<65 || firstIntVal >= 65 + questions.get(i).getNumPossibleAnswers()) {
				System.out.println("Please enter a valid input.");
				userAnswer = reader.nextLine();
				firstChar = userAnswer.charAt(0);
				firstIntVal = firstChar;
			}
			// Now we have valid input
			if (firstChar==questions.get(i).getCorrectAnswer()) {
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
