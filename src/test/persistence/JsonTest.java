package persistence;


/*
 * Tests for Json
 *
 * modelled after JsonSerializationDemo
 * Link here: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

import model.MCQuestion;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkMcQuestion(String question, MCQuestion mcQuestion, String c, String w1, String w2, String w3) {
        assertEquals(question, mcQuestion.getQuestion());
        assertEquals(c, mcQuestion.getCorrectAnswer());
        assertEquals(w1, mcQuestion.getWrongAnswer1());
        assertEquals(w2, mcQuestion.getWrongAnswer2());
        assertEquals(w3, mcQuestion.getWrongAnswer3());
    }
}