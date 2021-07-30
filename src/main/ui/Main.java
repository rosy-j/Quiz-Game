package ui;
/*
 * Main class that contains main method that starts QuizApp
 */

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new QuizApp();
        } catch (FileNotFoundException e) {
            System.out.println("Error running application: file not found");
        }
    }
}
