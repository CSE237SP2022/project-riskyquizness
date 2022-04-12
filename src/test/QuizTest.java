package test;

import quiz.QuizSystem;
import quiz.QuizSystem.Types;
import quiz.Question;
import quiz.Quiz;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;
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
		String num_questions = "3";
		
		quiz.setNumQuestions(num_questions);
		int check_num = quiz.getNumQuestions();
		
		assertEquals(check_num, 3);
	}
	
	@Test
	void check_question_read_input() {
		QuizSystem quiz_system = new QuizSystem();
		String user_input = "How well do you know me?";
		
		String quiz_name = quiz_system.questionAndReadInput("What is the name of the quiz?", new Scanner(user_input), Types.STRING);
		
		assertEquals(quiz_name, user_input);
	}
	
	@Test
	void check_question_read_input_with_invalid_input() {
		QuizSystem quiz_system = new QuizSystem();
		String user_input = "three\n3";
		
		String num_questions = quiz_system.questionAndReadInput("How many questions are in your quiz?", new Scanner(user_input), Types.INT);
		
		assertEquals(num_questions, "3");
	}
	
	@Test
	void check_question_read_input_cancel() {
		QuizSystem quiz_system = new QuizSystem();
		String user_input = "cancel";
		
		String cancel = quiz_system.questionAndReadInput("How many questions are in your quiz?", new Scanner(user_input), Types.INT);
		
		assertEquals(cancel, "CANCEL");
	}
	
	@Test
	void check_question_read_input_cancel_display_message() {
		QuizSystem quiz_system = new QuizSystem();
		String user_input = "cancel";
		
		quiz_system.questionAndReadInput("How many questions are in your quiz?", new Scanner(user_input), Types.INT);
		
		String expected_output = "How many questions are in your quiz?\n" + "\nQuiz Creation Cancelled\n____________________________";
		String display = outputStreamCaptor.toString().trim();
		
		assertEquals(expected_output, display);
	}
	
	
	@Test
	void check_create_quiz_cancel() {
		QuizSystem quiz_system = new QuizSystem();
		String user_inputs = "How well do you know me?\n" 
								+ "1\n"
								+ "What is my favorite color?\n"
								+ "3\n"
								+ "Red\n"
								+ "cancel";
		
		quiz_system.createQuiz(new Scanner(user_inputs));
		
		String expected_output = "What is the name of the quiz?\n" + 
				"How many questions are in your quiz?\n" +
				"Question 1\n" +
				"How many possible answers are there?\n" +
				"Answer Choice 1\n" + 
				"Answer Choice 2\n" +
				"\nQuiz Creation Cancelled\n____________________________";
		String display = outputStreamCaptor.toString().trim();
		
		assertEquals(expected_output, display);
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
	void check_preview_quiz() {
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
		outputStreamCaptor.reset();
		
		quiz_system.quizzes.get(0).previewQuiz();
		
		String expectedOutput = "____________________________\n"
				+ "Preview Quiz\n\n"
				+ "Quiz Name: How well do you know me?\n"
				+ "What is my favorite color?\n"
				+ "A Red\n"
				+ "B Green\n"
				+ "C Blue\n\n"
				+ "____________________________";
		String display = outputStreamCaptor.toString().trim();
		assertEquals(expectedOutput, display);
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
		
		quiz.addQuestion(1, new Scanner(user_inputs_add_question));
			
		assertEquals(2, quiz.questions.size());
	}
	
	@Test
	void check_invalid_int1() {
		QuizSystem quiz_system = new QuizSystem();
		Boolean non_int = quiz_system.checkValidInt("a");
		
		assertFalse(non_int);
	}
	
	@Test
	void check_invalid_int2() {
		QuizSystem quiz_system = new QuizSystem();
		Boolean non_int = quiz_system.checkValidInt("!AC12");
		
		assertFalse(non_int);
	}
	
	@Test
	void check_valid_int() {
		QuizSystem quiz_system = new QuizSystem();
		Boolean number = quiz_system.checkValidInt("3");
		
		assertTrue(number);
	}
	
	@Test
	void check_invalid_char1() {
		QuizSystem quiz_system = new QuizSystem();
		Boolean number = quiz_system.checkValidChar("3", 3);
		
		assertFalse(number);
	}
	
	@Test
	void check_invalid_char2() {
		QuizSystem quiz_system = new QuizSystem();
		Boolean word = quiz_system.checkValidChar("hello", 3);
		
		assertFalse(word);
	}
	
	@Test
	void check_invalid_char3() {
		QuizSystem quiz_system = new QuizSystem();
		Boolean ignores_constraint = quiz_system.checkValidChar("z", 3);
		
		assertFalse(ignores_constraint);
	}
	
	@Test
	void check_valid_char() {
		QuizSystem quiz_system = new QuizSystem();
		Boolean valid_char = quiz_system.checkValidChar("b", 3);
		
		assertTrue(valid_char);
	}
	
	@Test
	void testTakeQuizUserInput() {
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
	
	@Test
	void testUserInvalidInput() {
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
		
		String userInput = "1"+"\nB"+"\nA";
		quiz.takeQuiz(new Scanner(userInput));
		
		String expectedOutput = "Favorite color?\nA blue\nB green\nC red\n"+"\nInvalid input. Favorite color?\nA blue\nB green\nC red\n\n"+"Favorite food?\nA orange\nB rice\nC bread\n\nYou got 100.0% of this quiz correct.";
		String display = outputStreamCaptor.toString().trim();
		
		assertTrue(expectedOutput.equals(display));
	}
	
	
	@Test
	void testTakeQuizUserInvalidLengthInput() {
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
		
		String userInput = "Blue"+"\nB"+"\nA";
		quiz.takeQuiz(new Scanner(userInput));
		
		String expectedOutput = "Favorite color?\nA blue\nB green\nC red\n"+"\nInvalid input. Favorite color?\nA blue\nB green\nC red\n\n"+"Favorite food?\nA orange\nB rice\nC bread\n\nYou got 100.0% of this quiz correct.";
		String display = outputStreamCaptor.toString().trim();
		
		assertTrue(expectedOutput.equals(display));
	}
	
	@Test
	void testTakeQuizUserMultipleInvalidInput() {
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
		
		String userInput = "Blue"+"\ngreen"+"\nB"+"\nA";
		quiz.takeQuiz(new Scanner(userInput));
		
		String expectedOutput = "Favorite color?\nA blue\nB green\nC red\n"+"\nInvalid input. Favorite color?\nA blue\nB green\nC red\n"+"\nInvalid input. Favorite color?\nA blue\nB green\nC red\n"+"\nFavorite food?\nA orange\nB rice\nC bread\n\nYou got 100.0% of this quiz correct.";
		String display = outputStreamCaptor.toString().trim();
		
		assertTrue(expectedOutput.equals(display));
	}
	
	@Test
	void testTakeQuizUserGrading() {
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
		
		String userInput = "A"+"\nA";
		quiz.takeQuiz(new Scanner(userInput));
		
		String expectedOutput = "Favorite color?\nA blue\nB green\nC red\n"+"\nFavorite food?\nA orange\nB rice\nC bread\n\nYou got 50.0% of this quiz correct.";
		String display = outputStreamCaptor.toString().trim();
		
		assertTrue(expectedOutput.equals(display));
	}
}
