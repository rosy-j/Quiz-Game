package model;
/*
 * Unit tests for MCQuestion
 */

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
    public void testMCQuestionConstructor() {
        assertEquals("What is the meaning of life?", testQuestion.getQuestion());
        MCQuestion testQuesion2 = new MCQuestion("What colour is the moon?");
        assertEquals("What colour is the moon?", testQuesion2.getQuestion());
    }

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

}