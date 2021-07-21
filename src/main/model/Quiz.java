package model;
/*
 * Represents a quiz that contains multiple choice questions
 */

import java.util.List;

public class Quiz {
    private String quizName;
    private List<MCQuestion> questions;

    // EFFECTS: constructs a quiz that has a name, and no multiple choice questions
    public Quiz(String quizName) {

    }

    // MODIFIES: this
    // EFFECTS: adds a multiple choice question to the quiz
    public void addQuestion(MCQuestion q) {

    }

    // MODIFIES: this
    // EFFECTS: if the multiple choice question is in the quiz, remove it and return true
    //          otherwise return false
    public boolean removeQuestion(MCQuestion q) {
        return false;
    }

    // REQUIRES: quiz must have at least one multiple choice question
    // EFFECTS: returns the multiple choice question's question, its correct answer,
    //          and its incorrect answers in the format
    //          Question: question?
    //          Correct Answer: answer
    //          Incorrect Answers: answer, answer, answer
    public String viewQuestion(MCQuestion q) {
        return null;
    }

    // EFFECTS: returns each multiple choice question's question
    public String viewAllQuestions() {
        return null;
    }

    // EFFECTS: returns how many questions are currently in quiz
    public int length() {
        return 0;
    }

    // getter for quiz name
    public String getQuizName() {
        return null;
    }

    // EFFECTS: returns true if multiple choice question is in quiz, false otherwise
    public boolean isInQuiz(MCQuestion q) {
        return false;
    }

}
