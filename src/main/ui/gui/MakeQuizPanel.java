package ui.gui;

import model.Quiz;

import javax.swing.*;
import java.awt.*;

public class MakeQuizPanel extends JPanel {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private QuizGUI quizGUI;

    public MakeQuizPanel(QuizGUI quizGUI) {
        this.quizGUI = quizGUI;
        JPanel makeQuizPanel = new JPanel();
        makeQuizPanel.setSize(WIDTH, HEIGHT);
        makeQuizPanel.setBackground(Color.BLUE);

        JButton finishQuiz = new JButton("Finish");
        JButton goBack = new JButton("Back");
        makeQuizPanel.add(finishQuiz);

//        this.quizGUI.add(makeQuizPanel, BorderLayout.CENTER);
    }

}
