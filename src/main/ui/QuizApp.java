package ui;


import model.MCQuestion;
import model.Quiz;

import java.util.Scanner;

/*
 * Represents a quiz application
 */

public class QuizApp {
    private boolean runQuiz; //TODO: keep here or move somewhere else?
    private boolean runQuizApp; //TODO: keep here or move somewhere else?
    private boolean hasMadeQuiz;
    private String input;
    private MCQuestion question;
    private Quiz quiz;
    private Scanner userInput;

    // EFFECTS: constructs a quiz application, creates a scanner, and runs startup
    public QuizApp() {
        userInput = new Scanner(System.in);
        runQuizApp = true;
        runQuiz = false; // TODO: do i even need this?
        hasMadeQuiz = false;
        runApp();
    }

    // EFFECTS: runs startup menu and prompts user to make a selection
    public void runApp() {
        while (runQuizApp) {
            displayStartUpMenu();
            input = userInput.nextLine();
            switch (input) {
                case "A":
                    startQuiz();
                    break;
                case "B":
                    makeNewQuiz();
                    break;
                case "C":
                    viewQuiz();
                case "D":
                    endProgram();
                    break;
                default:
                    System.out.println("Please enter a valid input");
                    runApp();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: makes a new quiz and prompts user to input questions and answers
    public void makeNewQuiz() {
        boolean isMakingQuiz = true;
        System.out.println("Please enter a name for your quiz:");
        quiz = new Quiz(userInput.nextLine());
        makeQuestion();
        while (isMakingQuiz) {
            System.out.println("Please select:");
            System.out.println("A: Add another question\nB: Go back to menu");
            input = userInput.nextLine();
            if (input.equals("A")) {
                makeQuestion();
            } else if (input.equals("B")) {
                isMakingQuiz = false;
                hasMadeQuiz = true;
                runApp();
            } else {
                System.out.println("Please enter a valid input");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: makes a new question and prompts user to enter question and answers
    public void makeQuestion() {
        System.out.println("Please enter your question:");
        question = new MCQuestion(userInput.nextLine());
        System.out.println("Please enter the correct answer:");
        question.setCorrectAnswer(userInput.nextLine());
        System.out.println("Please enter an incorrect answer:");
        question.setWrongAnswer1(userInput.nextLine());
        System.out.println("Please enter an incorrect answer:");
        question.setWrongAnswer2(userInput.nextLine());
        System.out.println("Please enter an incorrect answer:");
        question.setWrongAnswer3(userInput.nextLine());
        quiz.addQuestion(question);
    }

    public void viewQuiz() {
        if (hasMadeQuiz == false) {
            System.out.println("Please make a quiz first");
            runApp();
        } else {
            viewAllQuestions();
            System.out.println("A: View a question in detail\nB: Go Back");
            input = userInput.nextLine();
            if (input.equals("A")) {
                viewQuestion();
            } else if (input.equals("B")) {
                runApp();
            } else {
                System.out.println("Please enter a valid input");
                viewQuiz();
            }
        }
    }

    // TODO: add specifications
    public void endProgram() {
        System.out.println("Goodbye");
        runQuizApp = false;

    }

    public void startQuiz() {
        if (hasMadeQuiz == false) {
            System.out.println("Please make a quiz first");
            runApp();
        } else {
            // TODO: make it select a variation of different arrangements of question
        }

    }

    public void displayStartUpMenu() {
        System.out.println("Please select:");
        System.out.println("A: Start quiz");
        System.out.println("B: Make new quiz");
        System.out.println("C: View quiz");
        System.out.println("D: Quit");
    }


    // REQUIRES: quiz must have at least one multiple choice question
    // EFFECTS: returns the multiple choice question's question, its correct answer,
    //          and its incorrect answers in the format
    //          Question: question
    //          Correct Answer: answer
    //          Incorrect Answers: answer, answer, answer
    public void viewQuestion() {
        System.out.println("Enter question to view in detail:");
        input = userInput.nextLine();
        for (int i = 0; i < quiz.length(); i++) {
            MCQuestion q = quiz.getQuestions().get(i);
            if (input.equals(q.getQuestion())) {
                System.out.println("Question: " + q.getQuestion());
                System.out.println("Correct Answer: " + q.getCorrectAnswer());
                System.out.println("Incorrect Answers: " + q.getWrongAnswer1()  + ", " + q.getWrongAnswer2() + ", "
                                + q.getWrongAnswer3());

                System.out.println("Please Select:");
                System.out.println("A: View another question\nB: Go Back");
                input = userInput.nextLine();
                if (input.equals("A")) {
                    viewAllQuestions();
                    viewQuestion();
                } else if (input.equals("B")) {
                    runApp();
                }
            } else if (i == quiz.length() - 1) {
                System.out.println("Couldn't find question");
                viewQuestion();
            }
        }
    }

    // REQUIRES: quiz must have at least one question
    // EFFECTS: prints each multiple choice question's question
    public void viewAllQuestions() {
        for (MCQuestion q : quiz.getQuestions()) {
            System.out.println(q.getQuestion());
        }
    }

}
