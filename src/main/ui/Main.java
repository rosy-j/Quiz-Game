package ui;
/*
 * Main class that contains main method that starts QuizApp
 */

import ui.gui.QuizEditorGUI;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        new QuizEditorGUI();
        //for console based application
//        try {
//            new QuizApp();
//        } catch (FileNotFoundException e) {
//            System.out.println("Error running application: file not found");
//        }
    }
}
