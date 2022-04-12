package quiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import quiz.QuizSystem.Types;


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
	
	public boolean setQuizName(String quiz_name) {
		if (!quiz_name.equals("CANCEL")) {
			this.quiz_name = quiz_name;
			return true;
		}	
		return false;
	}
	
	public boolean setNumQuestions(String num_questions) {
		if (!num_questions.equals("CANCEL")) {
			this.num_questions = Integer.parseInt(num_questions);
			return true;
		}
		return false;
	}
	
	public void previewQuiz() {
		System.out.println("\n____________________________");

		System.out.println("Preview Quiz\n");
		
		System.out.println("Quiz Name: " + getQuizName());
		for (Question question: this.questions) {
			System.out.println(question.toString());
		}
		System.out.println("____________________________\n");

	}
	
	public boolean addQuestion(int num_questions, Scanner reader) {
		for (int i = 0; i < num_questions; i++) {
			String question = QuizSystem.questionAndReadInput("Question " + (i + 1), reader, Types.STRING);
			if (question.equals("CANCEL")) {
				return false;
			}
			
			String num_possible_string = QuizSystem.questionAndReadInput("How many possible answers are there?", reader, Types.INT);
			if (num_possible_string.equals("CANCEL")) {
				break;
			}
			int num_possible = Integer.parseInt(num_possible_string);
			
			String[] possible_answers = new String[num_possible];
			for (int j = 0; j < num_possible; j++) {
				String answer_choice = QuizSystem.questionAndReadInput("Answer Choice " + (j + 1), reader, Types.STRING);
				if (answer_choice.equals("CANCEL")) {
					return false;
				}
				possible_answers[j] = answer_choice;
			}
			
			Question current_question = new Question(question, possible_answers);
			
			String prompt = "What is the correct answer?\n" + current_question.possibleAnswersToString();
			String inputted_correct_answer = QuizSystem.questionAndReadInput(prompt, reader, Types.CHAR, num_possible);
			current_question.correct_answer = inputted_correct_answer.charAt(0);
			
			if (this.questions.size() == this.num_questions) {
				this.num_questions++;
			}
	
			this.questions.add(current_question);
		}
		return true;
	}
	
	
	public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }
	
	public void takeQuiz(Scanner reader) {
		int score = 0;
		
		for (int i=0; i<questions.size(); i++) {
			// Here, the user will input
			String userAnswer = QuizSystem.questionAndReadInputTaking(questions.get(i).toString(), reader, Types.CHAR, questions.get(i).getNumPossibleAnswers());
			char firstChar = userAnswer.charAt(0);

			// Now we have valid input
			if (firstChar==questions.get(i).getCorrectAnswer()) {
				score++;
			}
		}
		
		double percentage = (double)score/this.num_questions * 100.0;
		System.out.println("You got "+ percentage + "% of this quiz correct.");
		return;
	}
}
