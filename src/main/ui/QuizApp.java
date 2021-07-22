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
    private MCQuestion question;
    private Quiz quiz;
    private Scanner userInput;

    // EFFECTS: constructs a quiz application, creates a scanner, and runs startup
    public QuizApp() {
        userInput = new Scanner(System.in);
        runQuizApp = true;
        runQuiz = false;
        hasMadeQuiz = false;
        runApp();
    }

    // EFFECTS: runs startup menu and prompts user to make a selection
    public void runApp() {
        displayStartUpMenu();
        int input = userInput.nextInt();
        switch (input) {
            case 0:
                startQuiz();
                break;
            case 1:
                makeNewQuiz();
                break;
            case 2:
                viewQuiz();
            case 3:
                endProgram();
                break;
            default:
                System.out.println("Please enter a valid input");
                runApp();
        }

    }

    // MODIFIES: this
    // EFFECTS: makes a new quiz and prompts user to input questions and answers
    public void makeNewQuiz() {
        boolean isMakingQuiz = true;
        System.out.println("Please enter a name for your quiz:");
        String name = userInput.next();
        quiz = new Quiz(name);
        makeQuestion();
        while (isMakingQuiz) {
            System.out.println("Please select:");
            System.out.println("0: Add another question\n1: Go back to menu");
            if (userInput.nextInt() == 0) {
                makeQuestion();
                quiz.addQuestion(question);
            } else if (userInput.nextInt() == 1) {
                isMakingQuiz = false;
                hasMadeQuiz = true;
                runApp();
            } else {
                System.out.println("Please enter a valid input");
            }
        }

    }

    // EFFECTS: makes a new question and prompts user to enter question and answers
    public void makeQuestion() {
        System.out.println("Please enter your question:");
        question = new MCQuestion(userInput.next());
        System.out.println("Please enter the correct answer:");
        question.setCorrectAnswer(userInput.next());
        System.out.println("Please enter an incorrect answer:");
        question.setWrongAnswer1(userInput.next());
        System.out.println("Please enter an incorrect answer:");
        question.setWrongAnswer2(userInput.next());
        System.out.println("Please enter an incorrect answer:");
        question.setWrongAnswer3(userInput.next());
    }

    public void viewQuiz() {

    }

    public void endProgram() {

    }

    public void startQuiz() {
        if (hasMadeQuiz == false) {
            System.out.println("Please make a quiz first");
        } else {
            // TODO: make it select a variation of different arrangements of question
        }

    }

    public void displayStartUpMenu() {
        System.out.println("Please select:");
        System.out.println("0: Start quiz");
        System.out.println("1: Make new quiz");
        System.out.println("2: View quiz");
        System.out.println("3: Quit");
    }


    // REQUIRES: quiz must have at least one multiple choice question
    // EFFECTS: returns the multiple choice question's question, its correct answer,
    //          and its incorrect answers in the format
    //          Question: question
    //          Correct Answer: answer
    //          Incorrect Answers: answer, answer, answer
    public void viewQuestion(MCQuestion question) {

    }

    // REQUIRES: quiz must have at least one question
    // EFFECTS: prints each multiple choice question's question
    public void viewAllQuestions() {
    }

}
