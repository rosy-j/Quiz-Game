package ui.gui;

import model.MCQuestion;
import model.Quiz;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/*
 * Represents a graphical user interface for making/editing/saving/loading a quiz
 */

public class QuizEditorGUI extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/guiQuiz.json";
    public static final int HEIGHT = 500;
    public static final int WIDTH = 800;

    private CardLayout cardLayout;
    private JPanel currentPanel;
    private JPanel quizEditorPanel;
    private PlayQuizGUI playQuizPanel;
    private JTextField questionText;
    private JTextField correctAnswerText;
    private JTextField wrongAnswer1Text;
    private JTextField wrongAnswer2Text;
    private JTextField wrongAnswer3Text;
    private JList<String> questionList;
    private DefaultListModel<String> defaultListModel;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Quiz quiz;

    // EFFECTS: constructs a quiz gui and prompts user to make a quiz, then shows the main menu
    public QuizEditorGUI() {
        super("Quiz");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setLocationRelativeTo(null);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);


        makeQuiz();
        initializePanels();
        createMenuOptions();
        createTextFields();
        displayQuestionList();

        pack();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets up panels and shows quiz editor panel
    private void initializePanels() {
        cardLayout = new CardLayout();
        currentPanel = new JPanel(cardLayout);

        quizEditorPanel = new JPanel(new BorderLayout());

        currentPanel.add("quizEditorPanel", quizEditorPanel);
        cardLayout.show(currentPanel, "quizEditorPanel");

        add(currentPanel);
    }


    // EFFECTS: displays the quiz editor panel
    public void displayQuizEditorMenu() {
        cardLayout.show(currentPanel, "quizEditorPanel");
    }

    // REQUIRES: a question must be selected
    // EFFECTS: displays question details in the format
    //          Question: question
    //          Correct Answer: answer
    //          Incorrect Answers: answer, answer, answer
    private void viewQuestionDetails(MCQuestion question) {
        String message = "Question: " + question.getQuestion() + "\nCorrect Answer: "
                + question.getCorrectAnswer() + "\nIncorrect Answers: " + question.getWrongAnswer1()
                + ", " + question.getWrongAnswer2() + ", " + question.getWrongAnswer3();
        JOptionPane.showMessageDialog(null, message, "Question Details",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: displays all the questions currently on the quiz
    private void displayQuestionList() {
        JPanel questionListPanel = new JPanel();
        questionListPanel.setBorder(new EmptyBorder(20, 10, 20, 20));
        questionListPanel.setLayout(new BorderLayout());

        questionList = new JList<>();
        defaultListModel = new DefaultListModel<>();
        questionList.setModel(defaultListModel);

        questionList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        questionList.setLayoutOrientation(JList.VERTICAL);

        questionList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int selected = questionList.getSelectedIndex();
                    MCQuestion question = quiz.getQuestions().get(selected);
                    viewQuestionDetails(question);
                }
            }
        });

        JLabel questionListLabel = new JLabel("Questions in quiz:  (Double click to view question details)");
        JScrollPane scrollPane = new JScrollPane(questionList);

        questionListPanel.add(questionListLabel, BorderLayout.NORTH);
        questionListPanel.add(scrollPane, BorderLayout.CENTER);

        quizEditorPanel.add(questionListPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: creates text fields for user to input question and answers
    private void createTextFields() {
        JPanel questionPanel = new JPanel();
        questionPanel.setBorder(new EmptyBorder(20, 20, 20, 10));
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.PAGE_AXIS));

        addLabel(questionPanel, "Question");
        questionText = new JTextField(30);
        questionPanel.add(questionText);

        addLabel(questionPanel, "Correct Answer");
        correctAnswerText = new JTextField(30);
        questionPanel.add(correctAnswerText);

        addLabel(questionPanel, "Incorrect Answer 1");
        wrongAnswer1Text = new JTextField(30);
        questionPanel.add(wrongAnswer1Text);

        addLabel(questionPanel, "Incorrect Answer 2");
        wrongAnswer2Text = new JTextField(30);
        questionPanel.add(wrongAnswer2Text);

        addLabel(questionPanel, "Incorrect Answer 3");
        wrongAnswer3Text = new JTextField(30);
        questionPanel.add(wrongAnswer3Text);

        quizEditorPanel.add(questionPanel, BorderLayout.WEST);
    }

    // MODIFIES: panel
    // EFFECTS: helper method that creates and adds a jLabel to the specified panel
    private void addLabel(JPanel panel, String labelName) {
        JLabel label = new JLabel(labelName);
        panel.add(label);
    }

    // MODIFIES: this
    // EFFECTS: creates menu buttons
    private void createMenuOptions() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(0, 1));

        makeButton(menuPanel, "Play Quiz");
        makeButton(menuPanel, "Make New Quiz");
        makeButton(menuPanel, "Add Question");
        makeButton(menuPanel, "Remove Question");
        makeButton(menuPanel, "Save Quiz");
        makeButton(menuPanel, "Load Quiz");

        quizEditorPanel.add(menuPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: panel
    // EFFECTS: helper method that makes a button with the specified button name
    private void makeButton(JPanel panel, String buttonName) {
        JButton button = new JButton(buttonName);
        panel.add(button);
        button.addActionListener(this);
        button.setActionCommand(buttonName);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: plays sound when one of the menu buttons is pressed
    //          depending on which button is pressed, user can either make a new quiz, add a question,
    //          remove a question, save the quiz, or load the quiz
    public void actionPerformed(ActionEvent e) {
        playButtonSound();
        switch (e.getActionCommand()) {
            case "Play Quiz":
                playQuiz();
                break;
            case "Make New Quiz":
                makeQuiz();
                defaultListModel.clear();
                break;
            case "Add Question":
                addQuestion();
                break;
            case "Remove Question":
                removeQuestion();
                break;
            case "Save Quiz":
                saveQuiz();
                break;
            case "Load Quiz":
                loadQuiz();
                break;
        }

    }

    // EFFECTS: if the quiz does not have any questions, prompts user to add a question
    //          otherwise, starts the quiz
    private void playQuiz() {
        if (quiz.length() == 0) {
            JOptionPane.showMessageDialog(null, "Please add at least one question to your quiz",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            playQuizPanel = new PlayQuizGUI(quiz, this);
            currentPanel.add("playQuizPanel", playQuizPanel);
            cardLayout.show(currentPanel, "playQuizPanel");
        }
    }

    // MODIFIES: this
    // EFFECTS: removes selected question from the quiz,
    //          if a question isn't selected, show an error message
    private void removeQuestion() {
        try {
            int selected = questionList.getSelectedIndex();
            MCQuestion selectedQuestion = quiz.getQuestions().get(selected);
            quiz.removeQuestion(selectedQuestion);
            defaultListModel.remove(selected);
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "Please select a question to remove",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the quiz from file if the user chooses yes
    //          if quiz cannot be loaded, show error message
    private void loadQuiz() {
        int userInput = JOptionPane.showConfirmDialog(null, "Load quiz?",
                "Load", JOptionPane.YES_NO_OPTION);
        if (userInput == JOptionPane.YES_OPTION) {
            try {
                defaultListModel.clear();
                quiz = jsonReader.read();
                for (MCQuestion question : quiz.getQuestions()) {
                    defaultListModel.addElement(question.getQuestion());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Could not load quiz, please try again",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // EFFECTS: saves the quiz to a file if the user chooses yes, then informs the user that save was successful
    //          if the save was unsuccessful, displays error message
    private void saveQuiz() {
        int userInput = JOptionPane.showConfirmDialog(null, "Save quiz?",
                "Save", JOptionPane.YES_NO_OPTION);
        if (userInput == JOptionPane.YES_OPTION) {
            try {
                jsonWriter.open();
                jsonWriter.write(quiz);
                jsonWriter.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Could not save quiz, please try again",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(null, "Save Successful",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a question to the quiz
    private void addQuestion() {
        MCQuestion mcQuestion = new MCQuestion(questionText.getText());
        mcQuestion.setCorrectAnswer(correctAnswerText.getText());
        mcQuestion.setWrongAnswer1(wrongAnswer1Text.getText());
        mcQuestion.setWrongAnswer2(wrongAnswer2Text.getText());
        mcQuestion.setWrongAnswer3(wrongAnswer3Text.getText());

        this.quiz.addQuestion(mcQuestion);
        defaultListModel.addElement(mcQuestion.getQuestion());

        questionText.setText("");
        correctAnswerText.setText("");
        wrongAnswer1Text.setText("");
        wrongAnswer2Text.setText("");
        wrongAnswer3Text.setText("");
    }

    // MODIFIES: this
    // EFFECTS: asks user for quiz name
    //          makes a new empty quiz with user input as the name
    private void makeQuiz() {
        String userInput = JOptionPane.showInputDialog("Enter a name for your quiz");
        quiz = new Quiz(userInput);
    }

    // EFFECTS: plays button press sound effect
    public void playButtonSound() {
        String soundFile = "./data/buttonSound.wav";
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFile));
            Clip audioClip = AudioSystem.getClip();
            audioClip.open(audioInputStream);
            audioClip.start();
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Unsupported Audio File");
        } catch (IOException e) {
            System.err.println("IOException");
        } catch (LineUnavailableException e) {
            System.err.println("LineUnavailableException");
        }
    }
}
