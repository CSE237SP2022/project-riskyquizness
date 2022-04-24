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


public class Quiz {
	public ArrayList<Question> questions;
	private String quiz_name;
	private int num_questions;
	private Map<String,Double> leaderboard = new HashMap<String,Double>();
	
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
	
	public Map<String,Double> getLeaderboard(){
		return this.leaderboard;
	}
	
	public void setLeaderboard(Map<String,Double> leaderboard) {
		this.leaderboard = leaderboard;
	}
	
	public void previewQuiz(Scanner reader) {
		System.out.println("\n____________________________");

		System.out.println("Preview Quiz\n");
		
		System.out.println("Quiz Name: " + getQuizName());
		for (Question question: this.questions) {
			System.out.println(question.toString());
		}
		System.out.println("____________________________\n");
		
		editQuiz(reader);
	}
	
	public void editQuiz(Scanner reader) {
		String edit = QuizSystem.questionAndReadInput("Confirm quiz? (type 'yes' to confirm, 'no' to edit)", reader, Types.BOOL);
		
		if (edit.toUpperCase().equals("YES")) {
			return;
		}
		else if (edit.toUpperCase().equals("NO")){
			String add_delete = QuizSystem.questionAndReadInput("Add question (1) or delete question (2)? (type '1' or '2')", reader, Types.INT, 2);
			if (Integer.parseInt(add_delete) == 1) {
				addQuestion(1, reader);
			}
			else if (Integer.parseInt(add_delete) == 2) {
				String question_to_del = QuizSystem.questionAndReadInput("Which question would you like to delete?", reader, Types.INT, this.questions.size());
				int question_num = Integer.parseInt(question_to_del);
				this.questions.remove(question_num - 1);
				this.num_questions = this.questions.size();
			}
			
			this.previewQuiz(reader);
		}

	}
	
	public boolean addQuestion(int num_questions, Scanner reader) {
		for (int i = 0; i < num_questions; i++) {
			String question = QuizSystem.questionAndReadInput("Question " + (this.questions.size() + 1), reader, Types.STRING);
			if (question.equals("CANCEL")) {
				return false;
			}
			
			String num_possible_string = QuizSystem.questionAndReadInput("How many possible answers are there?", reader, Types.INT);
			if (num_possible_string.equals("CANCEL")) {
				return false;
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
	
	public void takeQuiz(Scanner reader) {
		int score = 0;
		
		for (int i=0; i<questions.size(); i++) {
			// Here, the user will input
			String userAnswer = QuizSystem.questionAndReadInputTaking(questions.get(i).toString(), reader, Types.CHAR, questions.get(i).getNumPossibleAnswers());
			
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
		storeScore(percentage, reader);
		System.out.println("You got "+ percentage + "% of this quiz correct.");
		printLeaderboard();
		return;
	}
	
	public void storeScore(double percentage, Scanner reader) {
		String userName = askUserName(reader);
		this.leaderboard.put(userName, percentage);
		
		
//		List<Map.Entry<String, Double>> list = new ArrayList<>(this.leaderboard.entrySet());
//        list.sort(Map.Entry.comparingByValue());
//
//        Map<String, Double> result = new LinkedHashMap<>();
//        for (Entry<String, Double> entry : list) {
//        	//System.out.println("key: "+ entry.getKey()+" value: "+entry.getValue());
//            result.put(entry.getKey(), entry.getValue());
//        }
//
//        this.leaderboard = result;
		
		Map<String,Double> result = this.leaderboard.entrySet()
				  .stream()
				  .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				  .collect(Collectors.toMap(
						    Map.Entry::getKey, 
						    Map.Entry::getValue, 
						    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
		
		this.leaderboard = result;
		
	}
	
	public String askUserName(Scanner reader) {
		String response = "";
		boolean validInput = false;
		String trim = "";

		while (!validInput) {
			System.out.println("Please enter a valid username:\n");
			response = reader.nextLine();
			trim = response.replaceAll(" ","");
			if (trim.length()>0) {
				validInput = true;
			}
		}
		return trim;
	}
	
	public void printLeaderboard() {
		Map<String,Double> curLeaderboard = this.leaderboard;
		
		for (Map.Entry<String,Double> entry : curLeaderboard.entrySet()) {
			System.out.println("Player: "+entry.getKey()+ " Score: " + entry.getValue());
		}
	}
}
