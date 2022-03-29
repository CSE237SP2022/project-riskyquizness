package quiz;

import java.util.ArrayList;
import java.util.Scanner;

public class QuizSystem {
	
	public ArrayList<Quiz> quizzes;
	
	public QuizSystem() {
		this.quizzes = new ArrayList<Quiz>();
	}
	
	public void createQuiz(Scanner reader) {
		Quiz new_quiz = new Quiz();
		
		System.out.println("What is the name of the quiz?");
		String quiz_name = reader.nextLine();
		new_quiz.setQuizName(quiz_name);
		
		System.out.println("How many questions are in your quiz?");
		int num_questions = Integer.parseInt(reader.nextLine());
		//TODO: check for invalid inputs
		if (num_questions == 0) {
			invalidInput();
		}
		new_quiz.setNumQuestions(num_questions);
		
		for (int i = 0; i < num_questions; i++) {
			new_quiz.addQuestion(i, reader);
		}
		
		this.quizzes.add(new_quiz);
						
	}
	
	public static void invalidInput() {
		System.out.print("Invalid input. ");
	}
	
	public void take_quiz_selection(int quiz_num) {
		this.quizzes.get(quiz_num-1).takeQuiz();
	}
	
	// TODO: create quiz or take quiz options
	public void quiz_selection(Scanner reader) {
		System.out.println("Would you like to create or take a quiz? (type 'create' or 'take')");
		String user_selection  = reader.nextLine();
		if (user_selection.equals("create")) {
			createQuiz(reader);
		}
		else if (user_selection.equals("take")) {
			for (int i = 0; i < this.quizzes.size(); i++) {
				System.out.println((i+1) + " " + this.quizzes.get(i).getQuizName());
			}
			System.out.println("Which quiz would you like to take? Please input the number.");
			int quiz_num = Integer.parseInt(reader.nextLine());
			take_quiz_selection(quiz_num);
		}
		else {
			invalidInput();
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner reader = new Scanner(System.in);
		QuizSystem quiz_list = new QuizSystem();
		
		while (true) {
			quiz_list.quiz_selection(reader);
		}

	}

}
