//package ui.gui;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class MenuPanel extends JPanel implements ActionListener {
//
//    private QuizGUI quizGUI;
//
//    public MenuPanel(QuizGUI quizGUI) {
//        this.quizGUI = quizGUI;
//    }
//
//    private void createMenuOptions() {
//        setLayout(new GridLayout(0, 1));
//
//        JButton makeQuizButton = new JButton("Make Quiz");
//        JButton viewQuizButton = new JButton("View Quiz");
//        JButton saveLoadButton = new JButton("Save/Load Quiz");
//        add(makeQuizButton);
//        add(viewQuizButton);
//        add(saveLoadButton);
//        makeQuizButton.addActionListener(this);
//        viewQuizButton.addActionListener(this);
//        saveLoadButton.addActionListener(this);
//        makeQuizButton.setActionCommand("Make Quiz");
//        viewQuizButton.setActionCommand("View Quiz");
//        saveLoadButton.setActionCommand("Save Load Quiz");
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//    }
//}
