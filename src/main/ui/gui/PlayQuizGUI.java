package ui.gui;

import model.MCQuestion;
import model.Quiz;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/*
 * Represents a quiz game GUI that allows a user to play through their quiz
 */
public class PlayQuizGUI extends JPanel implements ActionListener {
    private static final String IMAGE = "./data/congratulations.jpeg";
    private static final int BUTTON_HEIGHT = QuizEditorGUI.HEIGHT / 5;
    private static final int BUTTON_WIDTH = QuizEditorGUI.WIDTH / 2;

    private CardLayout cardLayout;
    private JPanel currentPanel;
    private JPanel startPanel;
    private JPanel questionPanel;
    private JPanel correctPanel;
    private JPanel incorrectPanel;
    private JPanel endQuizPanel;
    private QuizEditorGUI quizEditorGUI;
    private Quiz quiz;
    private int correctAnswer;
    private int currentQuestion;
    private int numCorrect;

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
        questionPanel = new JPanel(new BorderLayout());
        createStartPanel();
        createCorrectPanel();
        createIncorrectPanel();
        currentPanel.add("startPanel", startPanel);
        currentPanel.add("questionPanel", questionPanel);
        currentPanel.add("correctPanel", correctPanel);
        currentPanel.add("incorrectPanel", incorrectPanel);
        add(currentPanel);
        cardLayout.show(currentPanel, "startPanel");
    }

    private void createEndQuizPanel() {
        endQuizPanel = new JPanel(new BorderLayout());

        JLabel endQuizLabel = new JLabel("Your score for the quiz \"" + quiz.getQuizName()
                + "\" is: " + numCorrect + "/" + quiz.length());
        endQuizLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel congratulationsImage = new JLabel();
        congratulationsImage.setIcon(new ImageIcon(IMAGE));
        congratulationsImage.setHorizontalAlignment(SwingConstants.CENTER);

        JButton finishButton = new JButton("Finish");
        finishButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        endQuizPanel.add(finishButton);
        finishButton.addActionListener(this);
        finishButton.setActionCommand("No");

        endQuizPanel.add(endQuizLabel, BorderLayout.NORTH);
        endQuizPanel.add(congratulationsImage, BorderLayout.CENTER);
        endQuizPanel.add(finishButton, BorderLayout.SOUTH);

    }

    private void createIncorrectPanel() {
        incorrectPanel = new JPanel(new BorderLayout());

        JLabel incorrectLabel = new JLabel("Incorrect!");
        incorrectLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton nextButton = makeNextButton();

        incorrectPanel.add(incorrectLabel, BorderLayout.CENTER);
        incorrectPanel.add(nextButton, BorderLayout.SOUTH);
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

    private void runQuiz() {
        questionPanel.removeAll();
        cardLayout.show(currentPanel, "questionPanel");
        if (currentQuestion < quiz.length()) {
            MCQuestion question = quiz.getQuestions().get(currentQuestion);
            JLabel questionLabel = new JLabel(question.getQuestion());
            questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
            questionPanel.add(questionLabel, BorderLayout.CENTER);
            displayAnswerButtons(question);
        } else {
            createEndQuizPanel();
            currentPanel.add("endQuizPanel", endQuizPanel);
            cardLayout.show(currentPanel, "endQuizPanel");
        }
    }


    private void checkCorrectAnswer(int i) {
        currentQuestion++;
        if (i == correctAnswer) {
            numCorrect++;
            showCorrectPanel();
        } else {
            showIncorrectPanel();
        }

    }

    private void showIncorrectPanel() {
        cardLayout.show(currentPanel, "incorrectPanel");
    }

    private void showCorrectPanel() {
        cardLayout.show(currentPanel, "correctPanel");
    }

    private void displayAnswerButtons(MCQuestion question) {
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
                currentQuestion = 0;
                runQuiz();
                break;
            case "No":
                quizEditorGUI.displayQuizEditorMenu();
                break;
            case "answer1":
            case "answer2":
            case "answer3":
            case "answer4":
                for (int i = 1; i <= 4; i++) {
                    if (e.getActionCommand().equals("answer" + i)) {
                        checkCorrectAnswer(i);
                    }
                }
                break;
            case "Next":
                runQuiz();
                break;


        }

    }
}
