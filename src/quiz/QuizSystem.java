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
		new_quiz.setNumQuestions(num_questions);
		
		for (int i = 0; i < num_questions; i++) {
			new_quiz.addQuestion(i, reader);
		}
		
		this.quizzes.add(new_quiz);
						
	}
	
	// TODO: create quiz or take quiz options
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner reader = new Scanner(System.in);
		QuizSystem quiz_list = new QuizSystem();
		
		quiz_list.createQuiz(reader);
		
		quiz_list.quizzes.get(0).takeQuiz();
	}

}
