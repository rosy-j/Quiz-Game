package ui.gui;

import model.MCQuestion;
import model.Quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Represents a graphical user interface for quiz
 *
 *
 */

public class QuizGUI extends JFrame implements ActionListener {
    private static final int HEIGHT = 500;
    private static final int WIDTH = 700;
    private CardLayout layout;
    private JPanel panel;
    private JPanel menuPanel;
    private MakeQuizPanel makeQuizPanel;
    private JPanel saveLoadPanel;
    //TODO: delete unnecessary stuff
    private JTextField questionText;
    private JTextField correctAnswerText;
    private JTextField wrongAnswer1Text;
    private JTextField wrongAnswer2Text;
    private JTextField wrongAnswer3Text;
    private DefaultListModel defaultListModel;

    private String question;
    private String correctAnswer;
    private String wrongAnswer1;
    private String wrongAnswer2;
    private String wrongAnswer3;
    private Quiz quiz;

    public QuizGUI() {
        super("Quiz");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);

//        createPanels();
        createMenuOptions();
        makeQuiz();
        createQuestionFields();
        viewQuestionList();

        pack();
        setVisible(true);
    }

    private void viewQuestionList() {
        JPanel questionListPanel = new JPanel();
        defaultListModel = new DefaultListModel();

        JList questionList = new JList();
        questionList.setModel(defaultListModel);

        questionList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        questionList.setLayoutOrientation(JList.VERTICAL);
        questionList.setVisibleRowCount(5);

        JScrollPane scrollPane = new JScrollPane(questionList);
        scrollPane.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT / 3));


        questionListPanel.add(scrollPane);
        add(questionListPanel, BorderLayout.CENTER);
    }
//
//    private void createPanels() {
//        layout = new CardLayout();
//        panel = new JPanel(layout);
//
//        menuPanel = new JPanel();
//        makeQuizPanel = new MakeQuizPanel(this);
//
//        panel.add(menuPanel, "Menu");
//        panel.add(makeQuizPanel, "Make Quiz");
//    }

    private void createQuestionFields() {
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.PAGE_AXIS));

        JLabel question = new JLabel("Question");
        questionPanel.add(question);
        questionText = new JTextField(30);
        questionPanel.add(questionText);

        JLabel correctAnswer = new JLabel("Correct Answer");
        questionPanel.add(correctAnswer);
        correctAnswerText = new JTextField(30);
        questionPanel.add(correctAnswerText);

        JLabel wrongAnswer1 = new JLabel("Incorrect Answer 1");
        questionPanel.add(wrongAnswer1);
        wrongAnswer1Text = new JTextField(30);
        questionPanel.add(wrongAnswer1Text);

        JLabel wrongAnswer2 = new JLabel("Incorrect Answer 1");
        questionPanel.add(wrongAnswer2);
        wrongAnswer2Text = new JTextField(30);
        questionPanel.add(wrongAnswer2Text);

        JLabel wrongAnswer3 = new JLabel("Incorrect Answer 1");
        questionPanel.add(wrongAnswer3);
        wrongAnswer3Text = new JTextField(30);
        questionPanel.add(wrongAnswer3Text);

        add(questionPanel, BorderLayout.WEST);

    }

    private void createMenuOptions() {
        menuPanel = new JPanel();
//        layout.show(panel, "Menu");
        menuPanel.setLayout(new GridLayout(0, 1));

        JButton makeQuizButton = new JButton("Make New Quiz");
        JButton addQuestionButton = new JButton("Add Question");
        JButton saveLoadButton = new JButton("Save/Load Quiz");
        menuPanel.add(makeQuizButton);
        menuPanel.add(addQuestionButton);
        menuPanel.add(saveLoadButton);
        makeQuizButton.addActionListener(this);
        addQuestionButton.addActionListener(this);
        saveLoadButton.addActionListener(this);
        makeQuizButton.setActionCommand("Make Quiz");
        addQuestionButton.setActionCommand("Add Question");
        saveLoadButton.setActionCommand("Save Load Quiz");

        add(menuPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Make Quiz":
                makeQuiz();
                defaultListModel.clear();
                break;
            case "Add Question":
                addQuestion();
                break;
            case "Save Load Quiz":
                //TODO: haven't done this one yet
                break;
        }

    }

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

    private void makeQuiz() {
        String userInput = JOptionPane.showInputDialog("Enter a name for your quiz");
        quiz = new Quiz(userInput);
    }


    public static void main(String[] args) {
        new QuizGUI();
    }
}
