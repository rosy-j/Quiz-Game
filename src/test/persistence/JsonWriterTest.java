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
 * modelled after JsonSerializationDemo
 * Link here: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

class JsonWriterTest extends JsonTest {
    @Test
    public void testWriterInvalidFile() {
        try {
            Quiz q = new Quiz("Illegal");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyQuiz() {
        try {
            Quiz q = new Quiz("Empty Quiz");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyQuiz.json");
            writer.open();
            writer.write(q);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyQuiz.json");
            q = reader.read();
            assertEquals("Empty Quiz", q.getQuizName());
            assertEquals(0, q.length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterNonEmptyQuiz() {
        try {
            Quiz q = new Quiz("Existential Crisis Quiz");
            setUpQuiz(q);
            JsonWriter writer = new JsonWriter("./data/testWriterNonEmptyQuiz.json");
            writer.open();
            writer.write(q);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNonEmptyQuiz.json");
            q = reader.read();
            assertEquals("Existential Crisis Quiz", q.getQuizName());
            List<MCQuestion> questions = q.getQuestions();
            assertEquals(2, questions.size());
            checkMcQuestion("Why are we here?", questions.get(0),
                    "Just to suffer", "To smile", "To waste money on gacha games", "To sleep");
            checkMcQuestion("What is the meaning of life?", questions.get(1),
                    "42", "ice cream", "dogs", "cats");
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    // helper that sets up a quiz
    private Quiz setUpQuiz(Quiz q) {
        MCQuestion question1 = new MCQuestion("Why are we here?");
        question1.setCorrectAnswer("Just to suffer");
        question1.setWrongAnswer1("To smile");
        question1.setWrongAnswer2("To waste money on gacha games");
        question1.setWrongAnswer3("To sleep");
        q.addQuestion(question1);
        MCQuestion question2 = new MCQuestion("What is the meaning of life?");
        question2.setCorrectAnswer("42");
        question2.setWrongAnswer1("ice cream");
        question2.setWrongAnswer2("dogs");
        question2.setWrongAnswer3("cats");
        q.addQuestion(question2);
        return q;
    }
}