package quiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

import quiz.QuizSystem.Types;
/**
 * A class representing a Quiz with the questions, quiz name, and its leader board
 * @author Christina Cai, Guenevere Chang, Jiwon Choi, Katherine Zhou
 *
 */

public class Quiz {
	public ArrayList<Question> questions;
	private String quiz_name;
	private int num_questions;
	private Map<String,Double> leaderboard = new HashMap<String,Double>();
	private QuizSystem quiz_system;
	
	public Quiz(QuizSystem quiz_system) {
		this.questions = new ArrayList<Question>();
		this.quiz_name = "";
		this.num_questions = 0;
		this.quiz_system = quiz_system;
	}
	
	public Quiz(ArrayList<Question> questions, String quiz_name, int num_questions, QuizSystem quiz_system) {
		this.questions = questions;
		this.quiz_name = quiz_name;
		this.num_questions = num_questions;
		this.quiz_system = quiz_system;
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
	
	public Map<String,Double> getLeaderboard(){
		return this.leaderboard;
	}
	
	public void setLeaderboard(Map<String,Double> leaderboard) {
		this.leaderboard = leaderboard;
	}
	
	/**
	 * A method to preview the created quiz and displays all of its questions and possible answers
	 */
	public void previewQuiz() {
		System.out.println("\n____________________________");

		System.out.println("Preview Quiz\n");
		
		System.out.println("Quiz Name: " + getQuizName());
		for (Question question: this.questions) {
			System.out.println(question.toString());
		}
		System.out.println("____________________________\n");
		
		editQuiz();
	}
	
	/**
	 * A method that allows users to edit a created quiz during the preview quiz process. Users are able to add or delete questions.
	 */
	public void editQuiz() {
		
		String edit = quiz_system.questionAndReadInput("Confirm quiz? (type 'yes' to confirm, 'no' to edit)", Types.BOOL);
		
		if (edit.toUpperCase().equals("YES")) {
			return;
		}
		else if (edit.toUpperCase().equals("NO")){
			String add_delete = quiz_system.questionAndReadInput("Add question (1) or delete question (2)? (type '1' or '2')", Types.INT, 2);
			if (Integer.parseInt(add_delete) == 1) {
				addQuestion(1);
			}
			else if (Integer.parseInt(add_delete) == 2) {
				String question_to_del = quiz_system.questionAndReadInput("Which question would you like to delete?", Types.INT, this.questions.size());
				int question_num = Integer.parseInt(question_to_del);
				this.questions.remove(question_num - 1);
				this.num_questions = this.questions.size();
			}
			
			this.previewQuiz();
		}

	}
	
	/**
	 * A method that adds a question to the quiz
	 * @param num_questions int object with the number of questions to add
	 * @return boolean indicated whether or not the question was sucessfully added
	 */
	public boolean addQuestion(int num_questions) {
		for (int i = 0; i < num_questions; i++) {
			String question = quiz_system.questionAndReadInput("Question " + (this.questions.size() + 1), Types.STRING);
			if (question.equals("CANCEL")) {
				return false;
			}
			
			String num_possible_string = quiz_system.questionAndReadInput("How many possible answers are there?", Types.INT);
			if (num_possible_string.equals("CANCEL")) {
				return false;
			}
			int num_possible = Integer.parseInt(num_possible_string);
			
			String[] possible_answers = new String[num_possible];
			for (int j = 0; j < num_possible; j++) {
				String answer_choice = quiz_system.questionAndReadInput("Answer Choice " + (j + 1), Types.STRING);
				if (answer_choice.equals("CANCEL")) {
					return false;
				}
				possible_answers[j] = answer_choice;
			}
			
			Question current_question = new Question(question, possible_answers);
			
			String prompt = "What is the correct answer?\n" + current_question.possibleAnswersToString();
			String inputted_correct_answer = quiz_system.questionAndReadInput(prompt, Types.CHAR, num_possible);
			if (inputted_correct_answer.equals("CANCEL")) {
				return false;
			}
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
	
	/**
	 * A method for taking the quiz. Users will be shown the quiz questions and give answers. The score is calculated and printed out to the console.
	 */
	public void takeQuiz() {
		int score = 0;
		
		for (int i=0; i<questions.size(); i++) {
			// Here, the user will input
			String userAnswer = quiz_system.questionAndReadInputTaking(questions.get(i).toString(), Types.CHAR, questions.get(i).getNumPossibleAnswers());
			
			// Quiz taking cancel
			if (userAnswer.equals("CANCEL")) {
				return;
			}
			
			char firstChar = userAnswer.charAt(0);

			// Now we have valid input
			if (firstChar==questions.get(i).getCorrectAnswer()) {
				score++;
			}
		}
		
		double percentage = (double)score/this.num_questions * 100.0;
		storeScore(percentage);
		System.out.println("You got "+ percentage + "% of this quiz correct.");
		printLeaderboard();
		return;
	}
	
	/**
	 * A method to store the score for the leader board.
	 * @param percentage double variable containing the score that the user got
	 */
	public void storeScore(double percentage) {
		String userName = askUserName();
		this.leaderboard.put(userName, percentage);
		
		Map<String,Double> result = this.leaderboard.entrySet()
				  .stream()
				  .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				  .collect(Collectors.toMap(
						    Map.Entry::getKey, 
						    Map.Entry::getValue, 
						    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
		
		this.leaderboard = result;
		
	}
	
	/**
	 * A method that prompts the user to enter a user name
	 * @return String with the valid user name
	 */
	public String askUserName() {
		String response = "";
		boolean validInput = false;
		String trim = "";

		while (!validInput) {
			System.out.println("Please enter a valid username:\n");
			response = quiz_system.getReader().nextLine();
			trim = response.replaceAll(" ","");
			if (trim.length()>0) {
				validInput = true;
			}
		}
		return trim;
	}
	
	/**
	 * A method that prints out the leader board after a user has completed taking a quiz 
	 */
	public void printLeaderboard() {
		Map<String,Double> curLeaderboard = this.leaderboard;
		
		for (Map.Entry<String,Double> entry : curLeaderboard.entrySet()) {
			System.out.println("Player: "+entry.getKey()+ " Score: " + entry.getValue());
		}
	}
}
