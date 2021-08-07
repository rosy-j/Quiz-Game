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
    private static final String CONGRATULATIONS_IMAGE = "./data/congratulations.jpeg";
    private static final String SAD_FACE_IMAGE = "./data/sadEmoji.png";
    private static final String PARTY_POPPER_IMAGE = "./data/partyPopper.png";
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

    // MODIFIES: this
    // EFFECTS: makes all the panels and adds them to currentPanel, then shows the start panel
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

    // MODIFIES: this
    // EFFECTS: makes the panel that is shown once the quiz is over
    //          informs user of how many questions they got correct and displays a congratulations image
    private void createEndQuizPanel() {
        endQuizPanel = new JPanel(new BorderLayout());

        JLabel endQuizLabel = new JLabel("Your score for the quiz \"" + quiz.getQuizName()
                + "\" is: " + numCorrect + "/" + quiz.length());
        endQuizLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel congratulationsImage = new JLabel();
        congratulationsImage.setIcon(new ImageIcon(CONGRATULATIONS_IMAGE));
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

    // MODIFIES: this
    // EFFECTS: makes the panel that is shown when user chooses an incorrect answer
    private void createIncorrectPanel() {
        incorrectPanel = new JPanel(new BorderLayout());

        JLabel incorrectLabel = new JLabel("Incorrect!");
        incorrectLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel sadFaceImage = new JLabel();
        sadFaceImage.setIcon(new ImageIcon(SAD_FACE_IMAGE));
        sadFaceImage.setHorizontalAlignment(SwingConstants.CENTER);

        JButton nextButton = makeNextButton();

        incorrectPanel.add(incorrectLabel, BorderLayout.NORTH);
        incorrectPanel.add(sadFaceImage, BorderLayout.CENTER);
        incorrectPanel.add(nextButton, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: makes the panel that is shown when user chooses a correct answer
    private void createCorrectPanel() {
        correctPanel = new JPanel(new BorderLayout());

        JLabel correctLabel = new JLabel("Correct!");
        correctLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel partyPopperImage = new JLabel();
        partyPopperImage.setIcon(new ImageIcon(PARTY_POPPER_IMAGE));
        partyPopperImage.setHorizontalAlignment(SwingConstants.CENTER);

        JButton nextButton = makeNextButton();

        correctPanel.add(correctLabel, BorderLayout.NORTH);
        correctPanel.add(partyPopperImage, BorderLayout.CENTER);
        correctPanel.add(nextButton, BorderLayout.SOUTH);
    }

    // EFFECTS: helper method that creates a "next" button that advances the quiz
    private JButton makeNextButton() {
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        nextButton.setActionCommand("Next");
        nextButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        return nextButton;
    }

    // MODIFIES: this
    // EFFECTS: makes the panel that is shown at the start before the quiz game begins
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

    // MODIFIES: this
    // EFFECTS: clears the question panel
    //          if there are still questions on the quiz, displays the next question and four buttons with
    //          the question's correct/incorrect answers on them
    //          otherwise, creates and shows the end quiz panel
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


    // MODIFIES: this
    // EFFECTS: checks if the user chose the right answer
    //          if the answer is correct, increments numCorrect and shows the correct panel
    //          otherwise, shows the incorrect panel
    private void checkCorrectAnswer(int i) {
        currentQuestion++;
        if (i == correctAnswer) {
            numCorrect++;
            showCorrectPanel();
        } else {
            showIncorrectPanel();
        }
    }

    // EFFECTS: shows panel that informs user that their choice was incorrect
    private void showIncorrectPanel() {
        cardLayout.show(currentPanel, "incorrectPanel");
    }

    // EFFECTS: shows panel that informs user that their choice was correct
    private void showCorrectPanel() {
        cardLayout.show(currentPanel, "correctPanel");
    }

    // MODIFIES: this
    // EFFECTS: displays four buttons with the question's correct/incorrect answers on them in a random order
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

    // MODIFIES: this
    // EFFECTS: makes four buttons with the question's correct/incorrect answers on them
    private void makeButtons(String answer1, String answer2, String answer3, String answer4) {
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2));

        makeButton(buttonPanel, answer1, "answer1");
        makeButton(buttonPanel, answer2, "answer2");
        makeButton(buttonPanel, answer3, "answer3");
        makeButton(buttonPanel, answer4, "answer4");

        questionPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // EFFECTS: helper method that creates a button and adds it to the specified panel
    private void makeButton(JPanel panel, String buttonName, String actionCommand) {
        JButton button = new JButton(buttonName);
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        panel.add(button);
        button.addActionListener(this);
        button.setActionCommand(actionCommand);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: plays sound when one of the buttons is pressed
    //          depending on which button is pressed, user can either start the quiz, go back to the quiz editor menu,
    //          check if their answer was correct, or advance to the next question
    public void actionPerformed(ActionEvent e) {
        quizEditorGUI.playButtonSound();
        switch (e.getActionCommand()) {
            case "Yes":
                currentQuestion = 0;
                runQuiz();
                break;
            case "No":
                quizEditorGUI.displayQuizEditorPanel();
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
