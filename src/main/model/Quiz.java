package model;


import exceptions.NotInQuizException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

/*
 * Represents a quiz that contains multiple choice questions
 *
 * toJson() and questionsToJson() are modelled after JsonSerializationDemo.
 * Link here: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

public class Quiz implements Writable {
    private String quizName;
    private List<MCQuestion> questions;

    // EFFECTS: constructs a quiz that has a name, and no multiple choice questions
    public Quiz(String quizName) {
        this.quizName = quizName;
        questions = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a multiple choice question to the quiz
    public void addQuestion(MCQuestion question) {
        questions.add(question);
    }

    // MODIFIES: this
    // EFFECTS: if the question is in the quiz, removes the question from the quiz
    //          otherwise throws NotInQuizException
    public void removeQuestion(MCQuestion question) throws NotInQuizException {
        if (questions.contains(question)) {
            questions.remove(question);
        } else {
            throw new NotInQuizException();
        }
    }

    // EFFECTS: returns how many questions are currently in quiz
    public int length() {
        return questions.size();
    }

    // getter for quiz name
    public String getQuizName() {
        return this.quizName;
    }

    // getter for list of multiple choice questions
    public List<MCQuestion> getQuestions() {
        return this.questions;
    }

    // EFFECTS: returns true if multiple choice question is in quiz, false otherwise
    public boolean isInQuiz(MCQuestion question) {
        if (questions.contains(question)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    // EFFECTS: returns quiz as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("quizName", quizName);
        json.put("questions", questionsToJson());
        return json;
    }

    // EFFECTS: returns questions in quiz as a JSON array
    public JSONArray questionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (MCQuestion q : questions) {
            jsonArray.put(q.toJson());
        }
        return jsonArray;
    }
}
