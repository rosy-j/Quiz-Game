package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Unit tests for Quiz
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
        testQuestion1.setWrongAnswer1("Who knows");
        testQuestion1.setWrongAnswer2("Yes");
        testQuestion1.setWrongAnswer3("No");

        testQuestion2 = new MCQuestion(("What is the rarest M&M colour?"));
        testQuestion2.setCorrectAnswer("I don't know");
        testQuestion2.setWrongAnswer1("Who knows");
        testQuestion2.setWrongAnswer2("Yes");
        testQuestion2.setWrongAnswer3("No");

        testQuestion3 = new MCQuestion("Who invented ice cream?");
        testQuestion3.setCorrectAnswer("I don't know");
        testQuestion3.setWrongAnswer1("Who knows");
        testQuestion3.setWrongAnswer2("Yes");
        testQuestion3.setWrongAnswer3("No");
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
    // test that adds mc questions to a non empty quiz
    public void testAddQuestionNonEmptyQuiz() {
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

    //TODO: view one quiz question
    //TODO: view many quiz questions

    //TODO: viewAllQuestions

    // helper method to add three questions to quiz
    private void addThreeQuestions(MCQuestion question1, MCQuestion question2, MCQuestion question3) {
        testQuiz.addQuestion(question1);
        testQuiz.addQuestion(question2);
        testQuiz.addQuestion(question3);
    }

}
