package test;

import quiz.QuizSystem;
import quiz.Question;
import quiz.Quiz;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuizTest {

	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

	@BeforeEach
	public void setUp() {
	    System.setOut(new PrintStream(outputStreamCaptor));
	}
	
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
	
	@Test
	void testUserInput() {
		String prompt = "Favorite color?";
		String[] possible_answers = {"blue", "green", "red"};
		char correct_answer = 'B';
		Question question1 = new Question(prompt, possible_answers, correct_answer);
		
		String prompt2 = "Favorite food?";
		String[] possible_answers2 = {"orange", "rice", "bread"};
		char correct_answer2 = 'A';
		Question question2 = new Question(prompt2, possible_answers2, correct_answer2);
		
		ArrayList<Question> questions = new ArrayList<Question>();
		questions.add(question1);
		questions.add(question2);
		String quiz_name = "TestQuiz";
		int num_questions = 2;
		Quiz quiz = new Quiz(questions, quiz_name, num_questions);
		
		String userInput = "B"+"\nA";
		quiz.takeQuiz(new Scanner(userInput));
		
		String expectedOutput = "Favorite color?\nA blue\nB green\nC red\n"+"\nFavorite food?\nA orange\nB rice\nC bread\n\nYou got 100.0% of this quiz correct.";
		String display = outputStreamCaptor.toString().trim();
		
		assertTrue(expectedOutput.equals(display));
	}
	
}
