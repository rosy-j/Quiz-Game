package ui.gui;

import model.MCQuestion;
import model.Quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;

/*
 * Represents a quiz game GUI that allows a user to play through their quiz
 */
public class PlayQuizGUI extends JPanel implements ActionListener {
    private static final int BUTTON_HEIGHT = QuizEditorGUI.HEIGHT / 5;
    private static final int BUTTON_WIDTH = QuizEditorGUI.WIDTH / 2;

    private CardLayout cardLayout;
    private JPanel currentPanel;
    private JPanel startPanel;
    private JPanel questionPanel;
    private JPanel correctPanel;
    private JPanel incorrectPanel;
    private QuizEditorGUI quizEditorGUI;
    private Quiz quiz;
    private int correctAnswer;

    // EFFECTS: constructs a play quiz gui
    public PlayQuizGUI(Quiz quiz, QuizEditorGUI quizEditorGUI) {
        super(new BorderLayout());
        this.quiz = quiz;
        this.quizEditorGUI = quizEditorGUI;
        initializePanels();
    }

    private void initializePanels() {
        cardLayout = new CardLayout();
        currentPanel = new JPanel(cardLayout);
        createStartPanel();
        questionPanel = new JPanel();
        createCorrectPanel();
        createIncorrectPanel();
//        createEndQuizPanel(); //TODO:
        currentPanel.add("startPanel", startPanel);
        currentPanel.add("questionPanel", questionPanel);
        currentPanel.add("correctPanel", correctPanel);
        currentPanel.add("incorrectPanel", incorrectPanel);
        add(currentPanel);
        cardLayout.show(currentPanel, "startPanel");
    }

    private void createIncorrectPanel() {
        incorrectPanel = new JPanel(new BorderLayout());

        JLabel incorrectLabel = new JLabel("Incorrect!");
        incorrectLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton nextButton = makeNextButton();

        correctPanel.add(incorrectLabel, BorderLayout.CENTER);
        correctPanel.add(nextButton, BorderLayout.SOUTH);
    }

    private void createCorrectPanel() {
        correctPanel = new JPanel(new BorderLayout());

        JLabel correctLabel = new JLabel("Correct!");
        correctLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton nextButton = makeNextButton();

        correctPanel.add(correctLabel, BorderLayout.CENTER);
        correctPanel.add(nextButton, BorderLayout.SOUTH);
    }

    private JButton makeNextButton() {
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        nextButton.setActionCommand("Next");
        nextButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        return nextButton;
    }

    private void createStartPanel() {
        startPanel = new JPanel(new BorderLayout());
        JPanel yesNoPanel = new JPanel(new GridLayout(1, 2));

        JLabel label = new JLabel("Start Quiz?");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        makeButton(yesNoPanel, "No", "No");
        makeButton(yesNoPanel, "Yes", "Yes");

        startPanel.add(label, BorderLayout.CENTER);
        startPanel.add(yesNoPanel, BorderLayout.SOUTH);

    }

    private void startQuiz() {
        cardLayout.show(currentPanel, "questionPanel");
        for (MCQuestion question : quiz.getQuestions()) {
            JLabel questionLabel = new JLabel(question.getQuestion());
            questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
            questionPanel.add(questionLabel, BorderLayout.CENTER);
            nextQuestion(question);
        }
    }


    private void checkCorrectAnswer(int i) {
        if (i == correctAnswer) {
            showCorrectPanel();
        } else {
            showIncorrectPanel();
        }

    }

    private void showIncorrectPanel() {
        questionPanel.removeAll();
        cardLayout.show(currentPanel, "incorrectPanel");
    }

    private void showCorrectPanel() {
        questionPanel.removeAll();
        cardLayout.show(currentPanel, "correctPanel");
    }

    private void nextQuestion(MCQuestion question) {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 5);
        switch (randomNum) {
            case 1:
                makeButtons(question.getCorrectAnswer(), question.getWrongAnswer1(),
                        question.getWrongAnswer2(), question.getWrongAnswer3());
                correctAnswer = 1;
                break;
            case 2:
                makeButtons(question.getWrongAnswer2(), question.getCorrectAnswer(),
                        question.getWrongAnswer1(), question.getWrongAnswer3());
                correctAnswer = 2;
                break;
            case 3:
                makeButtons(question.getWrongAnswer3(), question.getWrongAnswer1(),
                        question.getCorrectAnswer(), question.getWrongAnswer2());
                correctAnswer = 3;
                break;
            case 4:
                makeButtons(question.getWrongAnswer1(), question.getWrongAnswer3(),
                        question.getWrongAnswer2(), question.getCorrectAnswer());
                correctAnswer = 4;
                break;
        }
    }

    private void makeButtons(String answer1, String answer2, String answer3, String answer4) {
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2));

        makeButton(buttonPanel, answer1, "answer1");
        makeButton(buttonPanel, answer2, "answer2");
        makeButton(buttonPanel, answer3, "answer3");
        makeButton(buttonPanel, answer4, "answer4");

        questionPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void makeButton(JPanel panel, String buttonName, String actionCommand) {
        JButton button = new JButton(buttonName);
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        panel.add(button);
        button.addActionListener(this);
        button.setActionCommand(actionCommand);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        quizEditorGUI.playButtonSound();
        switch (e.getActionCommand()) {
            case "Yes":
                startQuiz();
                break;
            case "No":
                quizEditorGUI.displayQuizEditorMenu();
                break;
            case "answer1":
                checkCorrectAnswer(1);
                break;
            case "answer2":
                checkCorrectAnswer(2);
                break;
            case "answer3":
                checkCorrectAnswer(3);
                break;
            case "answer4":
                checkCorrectAnswer(4);
                break;
            case "Next":
                cardLayout.show(currentPanel, "questionPanel");
                break;


        }

    }
}
