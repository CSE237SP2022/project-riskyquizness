package test;
import quiz.Quiz;
import quiz.Question;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QuestionTest {
	
	@Test
	void get_question_test() {
		String question = "Favorite color?";
		String[] possible_answers = {"blue", "green", "red"};
		char correct_answer = 'B';
		
		Question question1 = new Question(question, possible_answers, correct_answer);
		
		String get_question = question1.get_question();
		
		assertEquals(get_question, question);
	}
	
	@Test
	void get_possible_answers_test() {
		String question = "Favorite color?";
		String[] possible_answers = {"blue", "green", "red"};
		char correct_answer = 'B';
		
		Question question1 = new Question(question, possible_answers, correct_answer);
		
		String[] get_possible_answers = question1.get_possible_answers();
		
		assertEquals(possible_answers, get_possible_answers);
	}
	
	@Test
	void get_correct_answer_test() {
		String question = "Favorite color?";
		String[] possible_answers = {"blue", "green", "red"};
		char correct_answer = 'B';
		
		Question question1 = new Question(question, possible_answers, correct_answer);
		
		String[] get_possible_answers = question1.get_possible_answers();
		
		assertEquals(possible_answers, get_possible_answers);
	}
	
	@Test
	void testPrintQuestion() {
		String question = "Favorite color?";
		String[] possible_answers = {"blue", "green", "red"};
		char correct_answer = 'B';
		
		Question question1 = new Question(question, possible_answers, correct_answer);
		String display = question1.toString();
		String expected = "Favorite color?\nA blue\nB green\nC red\n";
		assertTrue(expected.equals(display));
	}

}
