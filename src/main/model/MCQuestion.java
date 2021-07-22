package model;

/*
 * Represents a multiple choice question
 */

public class MCQuestion {
    private String question;
    private String correctAnswer;
    private String wrongAnswer1;
    private String wrongAnswer2;
    private String wrongAnswer3;

    // EFFECTS: constructs a multiple choice question that has a question
    public MCQuestion(String question) {
        this.question = question;
    }

    // setters
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    //REQUIRES: cannot be the same as correct answer
    public void setWrongAnswer1(String wrongAnswer1) {
        this.wrongAnswer1 = wrongAnswer1;
    }

    // REQUIRES: cannot be the same as correct answer
    public void setWrongAnswer2(String wrongAnswer2) {
        this.wrongAnswer2 = wrongAnswer2;
    }

    // REQUIRES: cannot be the same as correct answer
    public void setWrongAnswer3(String wrongAnswer3) {
        this.wrongAnswer3 = wrongAnswer3;
    }

    // getters
    public String getQuestion() {
        return this.question;
    }
    
    public String getCorrectAnswer() {
        return this.correctAnswer;
    }

    public String getWrongAnswer1() {
        return this.wrongAnswer1;
    }

    public String getWrongAnswer2() {
        return this.wrongAnswer2;
    }

    public String getWrongAnswer3() {
        return this.wrongAnswer3;
    }



}
