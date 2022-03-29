package test;

import quiz.QuizSystem;
import quiz.Quiz;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

class QuizTest {

	
	@Test
	void check_set_quiz_name() {
		Quiz quiz = new Quiz();
		String quiz_name = "How well do you know me?";		
		
		quiz.setQuizName(quiz_name);
		String check_name = quiz.getQuizName();
		
		assertEquals(check_name, quiz_name);
	}

	@Test
	void check_set_num_questions() {
		Quiz quiz = new Quiz();
		int num_questions = 3;
		
		quiz.setNumQuestions(num_questions);
		int check_num = quiz.getNumQuestions();
		
		assertEquals(check_num, num_questions);
	}
	
	@Test
	void check_create_quiz_check_size() {
		QuizSystem quiz_system = new QuizSystem();
		String user_inputs = "How well do you know me? \n" 
								+ "1\n"
								+ "What is my favorite color?\n"
								+ "3\n"
								+ "Red\n"
								+ "Green\n"
								+ "Blue\n"
								+ "C\n";
		
		quiz_system.createQuiz(new Scanner(user_inputs));
		
		int num_quizzes = quiz_system.quizzes.size();
			
		assertEquals(1, num_quizzes);
	}
	
	@Test
	void check_create_quiz_check_quiz_name() {
		QuizSystem quiz_system = new QuizSystem();
		String user_inputs = "How well do you know me?\n" 
								+ "1\n"
								+ "What is my favorite color?\n"
								+ "3\n"
								+ "Red\n"
								+ "Green\n"
								+ "Blue\n"
								+ "C\n";
		
		quiz_system.createQuiz(new Scanner(user_inputs));
		
		Quiz quiz = quiz_system.quizzes.get(0);
		String quiz_name = quiz.getQuizName();
			
		assertEquals(quiz_name, "How well do you know me?");
	}
	
	@Test
	void check_add_question() {
		QuizSystem quiz_system = new QuizSystem();
		String user_inputs = "How well do you know me?\n" 
								+ "1\n"
								+ "What is my favorite color?\n"
								+ "3\n"
								+ "Red\n"
								+ "Green\n"
								+ "Blue\n"
								+ "C\n";
		
		quiz_system.createQuiz(new Scanner(user_inputs));
		
		Quiz quiz = quiz_system.quizzes.get(0);
			
		assertEquals(1, quiz.questions.size());
	}
	
	@Test
	void check_add_question_increment_num_questions() {
		QuizSystem quiz_system = new QuizSystem();
		String user_inputs_create_quiz = "How well do you know me?\n" 
								+ "1\n"
								+ "What is my favorite color?\n"
								+ "3\n"
								+ "Red\n"
								+ "Green\n"
								+ "Blue\n"
								+ "C\n";
		
		quiz_system.createQuiz(new Scanner(user_inputs_create_quiz));
		
		Quiz quiz = quiz_system.quizzes.get(0);
		String user_inputs_add_question = "What is my favorite city?\n"
											+ "4\n"
											+ "New York City\n"
											+ "Chicago\n"
											+ "San Francisco\n"
											+ "Los Angeles\n"
											+ "A\n";
		
		quiz.addQuestion(0, new Scanner(user_inputs_add_question));
			
		assertEquals(2, quiz.questions.size());
	}
	
}
