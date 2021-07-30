package persistence;

import model.MCQuestion;
import model.Quiz;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

/*
 * Represents a reader that reads workroom from JSON data stored in file
 *
 * modelled after JsonSerializationDemo.
 * Link here: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads quiz from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Quiz read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseQuiz(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses quiz from JSON object and returns it
    private Quiz parseQuiz(JSONObject jsonObject) {
        String name = jsonObject.getString("quizName");
        Quiz q = new Quiz(name);
        addQuestions(q, jsonObject);
        return q;
    }

    // MODIFIES: q
    // EFFECTS: parses questions from JSON object and adds them to quiz
    private void addQuestions(Quiz q, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("questions");
        for (Object json : jsonArray) {
            JSONObject nextMcQuestion = (JSONObject) json;
            addMcQuestion(q, nextMcQuestion);
        }
    }

    // MODIFIES: q
    // EFFECTS: parses MCQuestion from JSON object and adds it to quiz
    private void addMcQuestion(Quiz q, JSONObject jsonObject) {
        String name = jsonObject.getString("question");
        MCQuestion mcQuestion = new MCQuestion(name);
        mcQuestion.setCorrectAnswer(jsonObject.getString("correctAnswer"));
        mcQuestion.setWrongAnswer1(jsonObject.getString("wrongAnswer1"));
        mcQuestion.setWrongAnswer2(jsonObject.getString("wrongAnswer2"));
        mcQuestion.setWrongAnswer3(jsonObject.getString("wrongAnswer3"));
        q.addQuestion(mcQuestion);
    }
}
