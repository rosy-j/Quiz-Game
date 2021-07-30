package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Tests for Quiz
 */

public class QuizTest {
    private Quiz testQuiz;
    private MCQuestion testQuestion1;
    private MCQuestion testQuestion2;
    private MCQuestion testQuestion3;

    @BeforeEach
    public void setUp() {
        testQuiz = new Quiz("Trivia");

        testQuestion1 = new MCQuestion("Why is Gamora?");
        testQuestion1.setCorrectAnswer("I don't know");
        testQuestion1.setWrongAnswer1("Potato");
        testQuestion1.setWrongAnswer2("Yes");
        testQuestion1.setWrongAnswer3("No");

        testQuestion2 = new MCQuestion(("What is the capital city of Spain?"));
        testQuestion2.setCorrectAnswer("Madrid");
        testQuestion2.setWrongAnswer1("Toronto");
        testQuestion2.setWrongAnswer2("Las Vegas");
        testQuestion2.setWrongAnswer3("Antarctica");

        testQuestion3 = new MCQuestion("How much does the Chewbacca costume weigh?");
        testQuestion3.setCorrectAnswer("Eight pounds");
        testQuestion3.setWrongAnswer1("Fifty pounds");
        testQuestion3.setWrongAnswer2("Five pounds");
        testQuestion3.setWrongAnswer3("Two pounds");
    }

    @Test
    public void testQuiz() {
        assertEquals("Trivia", testQuiz.getQuizName());
        assertEquals(0, testQuiz.length());

    }

    @Test
    // test that adds one mc question to an empty quiz
    public void testAddQuestionEmptyQuizAddOne() {
        testQuiz.addQuestion(testQuestion1);
        assertEquals(1, testQuiz.length());
        assertTrue(testQuiz.isInQuiz(testQuestion1));

    }

    @Test
    // test that adds multiple mc questions to an empty quiz
    public void testAddQuestionEmptyQuizAddMany(){
        addThreeQuestions(testQuestion1, testQuestion2, testQuestion3);

        assertEquals(3, testQuiz.length());
        assertTrue(testQuiz.isInQuiz(testQuestion1));
        assertTrue(testQuiz.isInQuiz(testQuestion2));
        assertTrue(testQuiz.isInQuiz(testQuestion3));
    }

    @Test
    // test that adds one mc questions to a non empty quiz
    public void testAddQuestionNonEmptyQuizAddOne() {
        testQuiz.addQuestion(testQuestion2);
        assertEquals(1, testQuiz.length());
        assertTrue(testQuiz.isInQuiz(testQuestion2));

        testQuiz.addQuestion(testQuestion1);
        assertEquals(2, testQuiz.length());
        assertTrue(testQuiz.isInQuiz(testQuestion1));
        assertTrue(testQuiz.isInQuiz(testQuestion2));

    }

    @Test
    // test that adds multiple mc questions to a non empty quiz
    public void testAddQuestionNonEmptyQuizAddMany() {
        testQuiz.addQuestion(testQuestion2);
        assertEquals(1, testQuiz.length());
        assertTrue(testQuiz.isInQuiz(testQuestion2));

        testQuiz.addQuestion(testQuestion1);
        testQuiz.addQuestion(testQuestion3);
        assertEquals(3, testQuiz.length());
        assertTrue(testQuiz.isInQuiz(testQuestion1));
        assertTrue(testQuiz.isInQuiz(testQuestion2));
        assertTrue(testQuiz.isInQuiz(testQuestion3));

    }

    @Test
    // test that removes a question from an empty quiz
    public void testRemoveQuestionEmptyQuiz() {
        assertFalse(testQuiz.removeQuestion(testQuestion1));
        assertFalse(testQuiz.removeQuestion(testQuestion2));
        assertFalse(testQuiz.removeQuestion(testQuestion3));

        assertEquals(0, testQuiz.length());
        assertFalse(testQuiz.isInQuiz(testQuestion1));
        assertFalse(testQuiz.isInQuiz(testQuestion2));
        assertFalse(testQuiz.isInQuiz(testQuestion3));
    }

    @Test
    // test that removes the only question on the quiz
    public void testRemoveQuestionOneQuestion() {
        testQuiz.addQuestion(testQuestion1);

        assertTrue(testQuiz.removeQuestion(testQuestion1));
        assertFalse(testQuiz.isInQuiz(testQuestion1));
        assertEquals(0, testQuiz.length());
    }

    @Test
    // test that removes some questions from a quiz with many questions
    public void testRemoveQuestionManyRemoveSome() {
        addThreeQuestions(testQuestion1, testQuestion2, testQuestion3);

        assertTrue(testQuiz.removeQuestion(testQuestion2));
        assertTrue(testQuiz.removeQuestion(testQuestion3));

        assertEquals(1, testQuiz.length());
        assertTrue(testQuiz.isInQuiz(testQuestion1));
        assertFalse(testQuiz.isInQuiz(testQuestion2));
        assertFalse(testQuiz.isInQuiz(testQuestion3));
    }

    @Test
    // test that removes all questions from quiz
    public void testRemoveQuestionRemoveAll() {
        addThreeQuestions(testQuestion3, testQuestion2, testQuestion1);

        assertTrue(testQuiz.removeQuestion(testQuestion1));
        assertTrue(testQuiz.removeQuestion(testQuestion2));
        assertTrue(testQuiz.removeQuestion(testQuestion3));

        assertEquals(0, testQuiz.length());
        assertFalse(testQuiz.isInQuiz(testQuestion1));
        assertFalse(testQuiz.isInQuiz(testQuestion2));
        assertFalse(testQuiz.isInQuiz(testQuestion3));

    }

    @Test
    // test that tries to remove a question that's not in quiz
    public void testRemoveQuestionNotInQuiz() {
        testQuiz.addQuestion(testQuestion1);
        testQuiz.addQuestion(testQuestion3);

        assertFalse(testQuiz.removeQuestion(testQuestion2));

        assertEquals(2, testQuiz.length());
        assertTrue(testQuiz.isInQuiz(testQuestion1));
        assertTrue(testQuiz.isInQuiz(testQuestion3));
    }

    @Test
    // test that tries to remove a question twice
    public void testRemoveQuestionRemoveTwice() {
        testQuiz.addQuestion(testQuestion1);
        testQuiz.addQuestion(testQuestion2);

        assertTrue(testQuiz.removeQuestion(testQuestion2));
        assertFalse(testQuiz.removeQuestion(testQuestion2));

        assertEquals(1, testQuiz.length());
        assertTrue(testQuiz.isInQuiz(testQuestion1));
        assertFalse(testQuiz.isInQuiz(testQuestion2));
    }

    @Test
    public void testGetQuestions() {
        addThreeQuestions(testQuestion1, testQuestion2, testQuestion3);
        assertEquals(3, testQuiz.getQuestions().size());
        assertTrue(testQuiz.getQuestions().contains(testQuestion1));
        assertTrue(testQuiz.getQuestions().contains(testQuestion2));
        assertTrue(testQuiz.getQuestions().contains(testQuestion3));
    }


    @Test
    public void testToJsonEmptyQuiz() {
        JSONObject testJson = testQuiz.toJson();
        assertEquals("Trivia", testJson.get("quizName"));
        JSONArray jsonArray = (JSONArray) testJson.get("questions");
        assertEquals(0, jsonArray.length());

    }

    @Test
    // test for toJson when quiz has one question
    public void testToJsonQuizWithOneQuestion() {
        testQuiz.addQuestion(testQuestion1);
        JSONObject testJson = testQuiz.toJson();
        assertEquals("Trivia", testJson.get("quizName"));
        JSONArray jsonArray = (JSONArray) testJson.get("questions");
        assertEquals(1, jsonArray.length());

        JSONObject testJsonQuestion = jsonArray.getJSONObject(0);
        assertEquals("Why is Gamora?", testJsonQuestion.get("question"));
        assertEquals("I don't know", testJsonQuestion.get("correctAnswer"));
        assertEquals("Potato", testJsonQuestion.get("wrongAnswer1"));
        assertEquals("Yes", testJsonQuestion.get("wrongAnswer2"));
        assertEquals("No", testJsonQuestion.get("wrongAnswer3"));
    }

    @Test
    // test for toJson when quiz has multiple questions
    public void testToJsonQuizMultipleQuestions() {
        testQuiz.addQuestion(testQuestion1);
        testQuiz.addQuestion(testQuestion2);
        JSONObject testJson = testQuiz.toJson();
        assertEquals("Trivia", testJson.get("quizName"));
        JSONArray jsonArray = (JSONArray) testJson.get("questions");
        assertEquals(2, jsonArray.length());

        JSONObject testJsonQuestion = jsonArray.getJSONObject(0);
        assertEquals("Why is Gamora?", testJsonQuestion.get("question"));
        assertEquals("I don't know", testJsonQuestion.get("correctAnswer"));
        assertEquals("Potato", testJsonQuestion.get("wrongAnswer1"));
        assertEquals("Yes", testJsonQuestion.get("wrongAnswer2"));
        assertEquals("No", testJsonQuestion.get("wrongAnswer3"));

        JSONObject testJsonQuestion2 = jsonArray.getJSONObject(1);
        assertEquals("What is the capital city of Spain?", testJsonQuestion2.get("question"));
        assertEquals("Madrid", testJsonQuestion2.get("correctAnswer"));
        assertEquals("Toronto", testJsonQuestion2.get("wrongAnswer1"));
        assertEquals("Las Vegas", testJsonQuestion2.get("wrongAnswer2"));
        assertEquals("Antarctica", testJsonQuestion2.get("wrongAnswer3"));
    }

    @Test
    public void testQuestionsToJsonEmpty() {
        JSONArray testArray = testQuiz.questionsToJson();
        assertEquals(0, testArray.length());
    }

    @Test
    // test for questionsToJson when quiz has one question
    public void testQuestionsToJsonOneQuestion() {
        testQuiz.addQuestion(testQuestion1);
        JSONArray testArray = testQuiz.questionsToJson();
        assertEquals(1, testArray.length());

        JSONObject testJsonQuestion = testArray.getJSONObject(0);
        assertEquals("Why is Gamora?", testJsonQuestion.get("question"));
        assertEquals("I don't know", testJsonQuestion.get("correctAnswer"));
        assertEquals("Potato", testJsonQuestion.get("wrongAnswer1"));
        assertEquals("Yes", testJsonQuestion.get("wrongAnswer2"));
        assertEquals("No", testJsonQuestion.get("wrongAnswer3"));
    }

    @Test
    // test for questionsToJson when quiz has multiple questions
    public void testQuestionsToJsonMultipleQuestions() {
        testQuiz.addQuestion(testQuestion1);
        testQuiz.addQuestion(testQuestion2);
        JSONArray testArray = testQuiz.questionsToJson();
        assertEquals(2, testArray.length());

        JSONObject testJsonQuestion = testArray.getJSONObject(0);
        assertEquals("Why is Gamora?", testJsonQuestion.get("question"));
        assertEquals("I don't know", testJsonQuestion.get("correctAnswer"));
        assertEquals("Potato", testJsonQuestion.get("wrongAnswer1"));
        assertEquals("Yes", testJsonQuestion.get("wrongAnswer2"));
        assertEquals("No", testJsonQuestion.get("wrongAnswer3"));

        JSONObject testJsonQuestion2 = testArray.getJSONObject(1);
        assertEquals("What is the capital city of Spain?", testJsonQuestion2.get("question"));
        assertEquals("Madrid", testJsonQuestion2.get("correctAnswer"));
        assertEquals("Toronto", testJsonQuestion2.get("wrongAnswer1"));
        assertEquals("Las Vegas", testJsonQuestion2.get("wrongAnswer2"));
        assertEquals("Antarctica", testJsonQuestion2.get("wrongAnswer3"));
    }


    // helper method to add three questions to quiz
    private void addThreeQuestions(MCQuestion question1, MCQuestion question2, MCQuestion question3) {
        testQuiz.addQuestion(question1);
        testQuiz.addQuestion(question2);
        testQuiz.addQuestion(question3);
    }



}
