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

    // EFFECTS: runs main menu and prompts user to make a selection
    public void runApp() {
        while (runQuizApp) {
            mainMenu();
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
                    break;
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
        System.out.println("Please enter a name for your quiz:");
        quiz = new Quiz(userInput.nextLine());
        makeQuestion();
        displayMakeQuizMenu();
    }

    // EFFECTS: displays menu and allows user to choose to add another question to quiz, or go back to menu
    public void displayMakeQuizMenu() {
        System.out.println("Please select:");
        System.out.println("A: Add another question\nB: Go back to menu");
        input = userInput.nextLine();
        if (input.equals("A")) {
            makeQuestion();
        } else if (input.equals("B")) {
            hasMadeQuiz = true;
            runApp();
        } else {
            System.out.println("Please enter a valid input");
            displayMakeQuizMenu();
        }
    }

    // REQUIRES: correct answer cannot be the same as incorrect answers
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

    // EFFECTS: Presents user with all the quiz questions,
    //          allows user to choose to view a question in detail, edit the quiz, or go back to main menu
    public void viewQuiz() {
        if (!hasMadeQuiz) {
            System.out.println("Please make a quiz first");
            runApp();
        } else {
            viewAllQuestions();
            System.out.println("A: View a question in detail\nB: Edit Quiz\nC: Go Back");
            input = userInput.nextLine();
            switch (input) {
                case "A":
                    viewQuestion();
                    break;
                case "B":
                    editQuiz();
                    break;
                case "C":
                    runApp();
                    break;
                default:
                    System.out.println("Please enter a valid input");
                    viewQuiz();
                    break;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to either remove a question, edit a question, or go back to viewing quiz
    private void editQuiz() {
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Please select:");
        System.out.println("A: Edit a question\nB: Remove a question\nC: Go Back");
        input = userInput.nextLine();
        switch (input) {
            case "A":
                editQuestion();
                break;
            case "B":
                doRemoveQuestion();
                break;
            case "C":
                viewQuiz();
                break;
            default:
                System.out.println("Please enter a valid input");
                editQuiz();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: asks for user input and removes the specified question from quiz
    //          then allows user to choose to remove another question or go back to main menu
    private void doRemoveQuestion() {
        System.out.println("-----------------------------------------------------------------------------------------");
        viewAllQuestions();
        System.out.println("Please enter the question you would like to remove");
        input = userInput.nextLine();
        for (int i = 0; i < quiz.length(); i++) {
            MCQuestion q = quiz.getQuestions().get(i);
            if (input.equals(q.getQuestion())) {
                quiz.removeQuestion(q);
                System.out.println("Removed: " + q.getQuestion());
                if (quiz.length() == 0) {
                    hasMadeQuiz = false;
                }
                displayRemoveQuestionMenu();
            } else if (i == quiz.length() - 1) {
                System.out.println("Couldn't find question");
                doRemoveQuestion();
            }
        }
    }

    // EFFECTS: displays menu that allows user to choose to remove another question or go back to main menu
    public void displayRemoveQuestionMenu() {
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Please select:");
        System.out.println("A: Remove another question\nB: Go Back");
        input = userInput.nextLine();
        if (input.equals("A")) {
            doRemoveQuestion();
        } else if (input.equals("B")) {
            runApp();
        } else {
            System.out.println("Please enter a valid input");
            displayRemoveQuestionMenu();
        }
    }

    private void editQuestion() {
    }

    // EFFECTS: displays "Quitting application" and ends the program
    public void endProgram() {
        System.out.println("Quitting application");
        runQuizApp = false;

    }

    // EFFECTS: starts the quiz //TODO: add a better specification
    public void startQuiz() {
        if (!hasMadeQuiz) {
            System.out.println("Please make a quiz first");
            runApp();
        } else {
            // TODO: make it select a variation of different arrangements of question
        }

    }

    // EFFECTS: displays main menu of application //TODO: make this specification better?
    public void mainMenu() {
        System.out.println("-----------------------------------------------------------------------------------------");
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
        System.out.println("-----------------------------------------------------------------------------------------");
        viewAllQuestions();
        System.out.println("Enter which question you would like to view in detail:");
        input = userInput.nextLine();
        for (int i = 0; i < quiz.length(); i++) {
            MCQuestion q = quiz.getQuestions().get(i);
            if (input.equals(q.getQuestion())) {
                System.out.println("Question: " + q.getQuestion());
                System.out.println("Correct Answer: " + q.getCorrectAnswer());
                System.out.println("Incorrect Answers: " + q.getWrongAnswer1()  + ", " + q.getWrongAnswer2() + ", "
                                + q.getWrongAnswer3());
                displayViewQuestionMenu();
            } else if (i == quiz.length() - 1) {
                System.out.println("Couldn't find question");
                viewQuestion();
            }
        }
    }

    // EFFECTS: displays the view question menu and allows user to view another question or go back to main menu
    public void displayViewQuestionMenu() {
        System.out.println("Please Select:");
        System.out.println("A: View another question\nB: Go Back");
        input = userInput.nextLine();
        if (input.equals("A")) {
            viewAllQuestions();
            viewQuestion();
        } else if (input.equals("B")) {
            runApp();
        } else {
            System.out.println("Please enter a valid input");
            displayViewQuestionMenu();
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
