package ui;


import model.MCQuestion;
import model.Quiz;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/*
 * Represents a quiz application that can make a quiz, view/edit a quiz, and run a quiz
 */

public class QuizApp {
    private static final String lineBreak = "-------------------------------------------------------------------------";

    private int numCorrect;
    private String input;
    private boolean hasMadeQuiz;
    private Quiz quiz;
    private MCQuestion question;
    private Scanner userInput;

    // EFFECTS: constructs a quiz application, and runs the quiz application
    public QuizApp() {
        userInput = new Scanner(System.in);
        hasMadeQuiz = false;
        runQuiz();
    }

    // MODIFIES: this
    // EFFECTS: displays the main menu and prompts user to make a selection
    private void runQuiz() {
        boolean runQuizApp = true;
        while (runQuizApp) {
            displayMainMenu();
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
                    runQuizApp = false;
                    endProgram();
                    break;
                default:
                    System.out.println("Please enter a valid input");
                    runQuiz();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: makes a new quiz and prompts user to input a name for the quiz
    private void makeNewQuiz() {
        System.out.println(lineBreak);
        System.out.println("Please enter a name for your quiz:");
        quiz = new Quiz(userInput.nextLine());
        hasMadeQuiz = true;
        makeQuestion();
    }

    // MODIFIES: this
    // EFFECTS: displays menu and allows user to choose to add another question to quiz, or go back to main menu
    private void displayAddQuestionMenu() {
        System.out.println(lineBreak);
        System.out.println("Please select:");
        System.out.println("A: Add another question\nB: Go back to menu");
        input = userInput.nextLine();
        if (input.equals("A")) {
            makeQuestion();
        } else if (input.equals("B")) {
            runQuiz();
        } else {
            System.out.println("Please enter a valid input");
            displayAddQuestionMenu();
        }
    }

    // REQUIRES: correct answer should not be the same as incorrect answers
    // MODIFIES: this
    // EFFECTS: makes a new question and prompts user to enter
    //          a question, a correct answer, and three incorrect answers
    private void makeQuestion() {
        System.out.println(lineBreak);
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
        displayAddQuestionMenu();
    }

    // MODIFIES: this
    // EFFECTS: if user has not made a quiz, prompts user to make quiz
    //          otherwise, presents user with all the quiz questions,
    //          then allows user to choose to view a question in detail, edit the quiz, or go back to main menu
    private void viewQuiz() {
        if (!hasMadeQuiz) {
            System.out.println("Please make a quiz first");
            makeNewQuiz();
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
                    runQuiz();
                    break;
                default:
                    System.out.println("Please enter a valid input");
                    viewQuiz();
                    break;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to choose to remove a question, add a question, or go back to main menu
    private void editQuiz() {
        System.out.println(lineBreak);
        System.out.println("Please select:");
        System.out.println("A: Remove a question\nB: Add a question\nC: Go Back");
        input = userInput.nextLine();
        switch (input) {
            case "A":
                doRemoveQuestion();
                break;
            case "B":
                makeQuestion();
                break;
            case "C":
                runQuiz();
                break;
            default:
                System.out.println("Please enter a valid input");
                editQuiz();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: if quiz does not have questions, prompts user to make quiz
    //          otherwise asks for user input and removes the specified question from quiz if it is found
    //          if the question is not found, go back to the edit quiz menu
    private void doRemoveQuestion() {
        if (quiz.length() == 0) {
            System.out.println("There are no questions on this quiz");
            hasMadeQuiz = false;
            runQuiz();
        } else {
            System.out.println(lineBreak);
            viewAllQuestions();
            System.out.println("Please enter the question you would like to remove");
            input = userInput.nextLine();
            for (int i = 0; i < quiz.length(); i++) {
                MCQuestion q = quiz.getQuestions().get(i);
                if (input.equals(q.getQuestion())) {
                    quiz.removeQuestion(q);
                    System.out.println("Removed: " + q.getQuestion());
                    checkMadeQuiz();
                    displayRemoveQuestionMenu();
                } else if (i == quiz.length() - 1) {
                    System.out.println("Couldn't find question");
                    editQuiz();
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: checks to see if user has already made a quiz
    private void checkMadeQuiz() {
        if (quiz.length() == 0) {
            hasMadeQuiz = false;
        } else {
            hasMadeQuiz = true;
        }
    }

    // MODIFIES: this
    // EFFECTS: displays menu that allows user to choose to remove another question or go back to main menu
    private void displayRemoveQuestionMenu() {
        System.out.println(lineBreak);
        System.out.println("Please select:");
        System.out.println("A: Remove another question\nB: Go Back");
        input = userInput.nextLine();
        if (input.equals("A")) {
            doRemoveQuestion();
        } else if (input.equals("B")) {
            runQuiz();
        } else {
            System.out.println("Please enter a valid input");
            displayRemoveQuestionMenu();
        }
    }

    // EFFECTS: displays "Quitting application" and ends the program
    private void endProgram() {
        System.out.println(lineBreak);
        System.out.println("Quitting application");
        System.exit(0);

    }

    // MODIFIES: this
    // EFFECTS: if a quiz has not been made, prompt user to make quiz first
    //          otherwise, start the quiz
    private void startQuiz() {
        numCorrect = 0;
        if (!hasMadeQuiz) {
            System.out.println("Please make a quiz first");
            makeNewQuiz();
        } else {
            System.out.println(lineBreak);
            System.out.println("Quiz: " + quiz.getQuizName());
            for (MCQuestion question : quiz.getQuestions()) {
                getRandomQuestionConfig(question);
            }
            displayFinishedQuizMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the menu after finishing a quiz and informs user of how many questions they got correct
    //          allows user to choose to restart quiz or return to main menu
    private void displayFinishedQuizMenu() {
        System.out.println(lineBreak);
        System.out.println("Congratulations, you finished the quiz!");
        System.out.println("Your score is: " + numCorrect + "/" + quiz.length());
        System.out.println(lineBreak);
        System.out.println("Please select: ");
        System.out.println("A: Restart Quiz\nB: Go Back To Menu");
        input = userInput.nextLine();
        if (input.equals("A")) {
            startQuiz();
        } else if (input.equals("B")) {
            runQuiz();
        } else {
            System.out.println("Please enter a valid input");
            displayFinishedQuizMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: displays a question then displays its answers in a random order
    private void getRandomQuestionConfig(MCQuestion question) {
        System.out.println(lineBreak);
        System.out.println("Question: " + question.getQuestion());
        int randomNum = ThreadLocalRandom.current().nextInt(1, 5);
        switch (randomNum) {
            case 1:
                selectAnswerV1(question);
                break;
            case 2:
                selectAnswerV2(question);
                break;
            case 3:
                selectAnswerV3(question);
                break;
            case 4:
                selectAnswerV4(question);
                break;
        }
    }

    // Version 1 of selectAnswer
    // MODIFIES: this
    // EFFECTS: allows user to choose an answer and informs the user that they are correct/incorrect
    //          if the answer is correct, the correct answers go up by one
    private void selectAnswerV1(MCQuestion question) {
        System.out.println("Please select: ");
        System.out.println("A: " + question.getCorrectAnswer());
        System.out.println("B: " + question.getWrongAnswer1());
        System.out.println("C: " + question.getWrongAnswer2());
        System.out.println("D: " + question.getWrongAnswer3());
        input = userInput.nextLine();
        switch (input) {
            case "A":
                System.out.println("Correct!");
                numCorrect++;
                break;
            case "B":
            case "C":
            case "D":
                System.out.println("Incorrect. The right answer is: " + question.getCorrectAnswer());
                break;
            default:
                System.out.println("Please enter a valid input");
                selectAnswerV1(question);
                break;
        }
    }

    // Version 2 of selectAnswer
    // MODIFIES: this
    // EFFECTS: allows user to choose an answer and informs the user that they are correct/incorrect
    //          if the answer is correct, the correct answers go up by one
    private void selectAnswerV2(MCQuestion question) {
        System.out.println("Please select: ");
        System.out.println("A: " + question.getWrongAnswer3());
        System.out.println("B: " + question.getCorrectAnswer());
        System.out.println("C: " + question.getWrongAnswer1());
        System.out.println("D: " + question.getWrongAnswer2());
        input = userInput.nextLine();
        switch (input) {
            case "B":
                System.out.println("Correct!");
                numCorrect++;
                break;
            case "A":
            case "C":
            case "D":
                System.out.println("Incorrect. The right answer is: " + question.getCorrectAnswer());
                break;
            default:
                System.out.println("Please enter a valid input");
                selectAnswerV2(question);
                break;
        }
    }

    // Version 3 of selectAnswer
    // MODIFIES: this
    // EFFECTS: allows user to choose an answer and informs the user that they are correct/incorrect
    //          if the answer is correct, the correct answers go up by one
    private void selectAnswerV3(MCQuestion question) {
        System.out.println("Please select: ");
        System.out.println("A: " + question.getWrongAnswer2());
        System.out.println("B: " + question.getWrongAnswer1());
        System.out.println("C: " + question.getCorrectAnswer());
        System.out.println("D: " + question.getWrongAnswer3());
        input = userInput.nextLine();
        switch (input) {
            case "C":
                System.out.println("Correct!");
                numCorrect++;
                break;
            case "A":
            case "B":
            case "D":
                System.out.println("Incorrect. The right answer is: " + question.getCorrectAnswer());
                break;
            default:
                System.out.println("Please enter a valid input");
                selectAnswerV3(question);
                break;
        }
    }

    // Version 4 of selectAnswer
    // MODIFIES: this
    // EFFECTS: allows user to choose an answer and informs the user that they are correct/incorrect
    //          if the answer is correct, the correct answers go up by one
    private void selectAnswerV4(MCQuestion question) {
        System.out.println("Please select: ");
        System.out.println("A: " + question.getWrongAnswer1());
        System.out.println("B: " + question.getWrongAnswer3());
        System.out.println("C: " + question.getWrongAnswer2());
        System.out.println("D: " + question.getCorrectAnswer());
        input = userInput.nextLine();
        switch (input) {
            case "D":
                System.out.println("Correct!");
                numCorrect++;
                break;
            case "A":
            case "B":
            case "C":
                System.out.println("Incorrect. The right answer is: " + question.getCorrectAnswer());
                break;
            default:
                System.out.println("Please enter a valid input");
                selectAnswerV4(question);
                break;
        }
    }

    // EFFECTS: displays main menu of application with options to start quiz, make new quiz, view quiz and quit
    private void displayMainMenu() {
        System.out.println(lineBreak);
        System.out.println("Please select:");
        System.out.println("A: Start quiz");
        System.out.println("B: Make new quiz");
        System.out.println("C: View quiz");
        System.out.println("D: Quit");
    }


    // REQUIRES: quiz must have at least one multiple choice question
    // MODIFIES: this
    // EFFECTS: prompts user to choose a question to view
    //          if the question is found, displays the multiple choice question's question, its correct answer,
    //          and its incorrect answers in the format
    //          Question: question
    //          Correct Answer: answer
    //          Incorrect Answers: answer, answer, answer
    //          if it is not found, inform the user that the question could not be found and go back to view quiz
    private void viewQuestion() {
        System.out.println(lineBreak);
        viewAllQuestions();
        System.out.println("Enter which question you would like to view in detail:");
        input = userInput.nextLine();
        for (int i = 0; i < quiz.length(); i++) {
            MCQuestion q = quiz.getQuestions().get(i);
            if (input.equals(q.getQuestion())) {
                System.out.println(lineBreak);
                System.out.println("Question: " + q.getQuestion());
                System.out.println("Correct Answer: " + q.getCorrectAnswer());
                System.out.println("Incorrect Answers: " + q.getWrongAnswer1()  + ", " + q.getWrongAnswer2() + ", "
                                + q.getWrongAnswer3());
                displayViewQuestionMenu();
            } else if (i == quiz.length() - 1) {
                System.out.println("Couldn't find question");
                viewQuiz();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the view question menu and allows user to view another question or go back to main menu
    private void displayViewQuestionMenu() {
        System.out.println(lineBreak);
        System.out.println("Please Select:");
        System.out.println("A: View another question\nB: Go Back");
        input = userInput.nextLine();
        if (input.equals("A")) {
            viewQuestion();
        } else if (input.equals("B")) {
            runQuiz();
        } else {
            System.out.println("Please enter a valid input");
            displayViewQuestionMenu();
        }
    }

    // REQUIRES: quiz must have at least one question
    // EFFECTS: prints each multiple choice question's question
    private void viewAllQuestions() {
        System.out.println(lineBreak);
        System.out.println("Questions:");
        for (MCQuestion q : quiz.getQuestions()) {
            System.out.println(q.getQuestion());
        }
        System.out.println(lineBreak);
    }

}
