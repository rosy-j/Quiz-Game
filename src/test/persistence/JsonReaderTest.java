package persistence;

import model.MCQuestion;
import model.Quiz;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/*
 * Tests for JsonReader
 *
 * modelled after JsonSerializationDemo
 * Link here: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noFile.json");
        try {
            Quiz q = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyQuiz() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyQuiz.json");
        try {
            Quiz q = reader.read();
            assertEquals("Empty Quiz", q.getQuizName());
            assertEquals(0, q.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    // test for quiz with only one question
    public void testReaderQuizOneQuestion() {
        JsonReader reader = new JsonReader("./data/testReaderQuizOneQuestion.json");
        try {
            Quiz q = reader.read();
            assertEquals("One Question Quiz", q.getQuizName());
            List<MCQuestion> questions = q.getQuestions();
            assertEquals(1, questions.size());
            checkMcQuestion("What is the meaning of life?", questions.get(0),
                    "42", "41", "I don't know", "another wrong answer");
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderMultipleQuestionQuiz() {
        JsonReader reader = new JsonReader("./data/testReaderQuizMultipleQuestions.json");
        try {
            Quiz q = reader.read();
            assertEquals("Quiz With Multiple Questions", q.getQuizName());
            List<MCQuestion> questions = q.getQuestions();
            assertEquals(2, questions.size());
            checkMcQuestion("What is the meaning of life?", questions.get(0),
                    "42", "41", "I don't know", "another wrong answer");
            checkMcQuestion("Who is Gamora?", questions.get(1),
                    "Why is Gamora?", "A teddy bear", "The President", "The CEO of Amazon");
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}