package model;
/*
 * Tests for MCQuestion
 */

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MCQuestionTest {
    private MCQuestion testQuestion;

    @BeforeEach
    public void setUp() {
        testQuestion = new MCQuestion("What is the meaning of life?");
    }

    @Test
    public void testMCQuestion() {
        assertEquals("What is the meaning of life?", testQuestion.getQuestion());
        MCQuestion testQuesion2 = new MCQuestion("What colour is the moon?");
        assertEquals("What colour is the moon?", testQuesion2.getQuestion());
    }

    // tests for getters and setters

    @Test
    public void testSetCorrectAnswer() {
        testQuestion.setCorrectAnswer("42");
        assertEquals("42", testQuestion.getCorrectAnswer());
        testQuestion.setCorrectAnswer("Blurb");
        assertEquals("Blurb", testQuestion.getCorrectAnswer());
    }

    @Test
    public void testSetWrongAnswer1() {
        testQuestion.setWrongAnswer1("Dogs");
        assertEquals("Dogs", testQuestion.getWrongAnswer1());
        testQuestion.setWrongAnswer1("Cats");
        assertEquals("Cats", testQuestion.getWrongAnswer1());
    }

    @Test
    public void testSetWrongAnswer2() {
        testQuestion.setWrongAnswer2("I don't know");
        assertEquals("I don't know", testQuestion.getWrongAnswer2());
        testQuestion.setWrongAnswer2("Lasagna");
        assertEquals("Lasagna", testQuestion.getWrongAnswer2());
    }

    @Test
    public void testSetWrongAnswer3() {
        testQuestion.setWrongAnswer3("Money");
        assertEquals("Money", testQuestion.getWrongAnswer3());
        testQuestion.setWrongAnswer3("Happiness");
        assertEquals("Happiness", testQuestion.getWrongAnswer3());
    }

    @Test
    public void testToJson() {
        testQuestion.setCorrectAnswer("42");
        testQuestion.setWrongAnswer1("I don't know");
        testQuestion.setWrongAnswer2("Cheese");
        testQuestion.setWrongAnswer3("Flamingo");
        JSONObject jsonTest = testQuestion.toJson();
        assertEquals("What is the meaning of life?", jsonTest.get("question"));
        assertEquals("42", jsonTest.get("correctAnswer"));
        assertEquals("I don't know", jsonTest.get("wrongAnswer1"));
        assertEquals("Cheese", jsonTest.get("wrongAnswer2"));
        assertEquals("Flamingo", jsonTest.get("wrongAnswer3"));
    }
}
