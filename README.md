# My Personal Project: Multiple Choice Quiz Game

## Project Description:

<p>This application will be a multiple choice quiz game where users can add questions to a quiz and then be able to play
through the entire quiz.</p>

- **What will the application do?** <br>
  User's will be able to add multiple choice questions with corresponding correct/incorrect answers to a quiz. After 
  they are done, they can then play the quiz game. They will be presented with a question and be required to *select* 
  the correct answer. After they provide an answer, they will be informed that their answer was correct or incorrect. 
  They will then move onto the next question.
- **Who will use it?** <br>
  This application will be used by students or any other people who need a way to study for any upcoming quizzes, tests,
  or exams.
- **Why is this project of interest to you?** <br>
  This project interests me because back in high school I always enjoyed playing Kahoot (a quiz game) in class, so I 
  wanted to try to make something similar to it. 
  
## User Stories

- As a user, I want to be able to add a question to my quiz
- As a user, I want to be able to remove a question from my quiz
- As a user, I want to be able to select one question from my quiz to view its contents
- As a user, I want to be able to view all the questions in my quiz
- As a user, I want to be able to save my quiz (and all of its questions/answers) to a file.
- As a user, I want to be able to load my quiz (and all of its questions/answers) from a file.


## Phase 4: Task 2

<p>I chose to test and design a class in my model package that is robust.</p>

- Class that has a robust design: Quiz
- Method that has a robust design: removeQuestion(MCQuestion question)
- Test class that tests the robust design: QuizTest
- Test methods that test the robust design: testRemoveQuestionEmptyQuizExceptionExpected(), 
  testRemoveQuestionOneQuestionNoException(), testRemoveQuestionManyRemoveSomeNoException(),
  testRemoveQuestionRemoveAllNoException(), testRemoveQuestionNotInQuizExceptionExpected(),
  testRemoveQuestionRemoveTwiceExceptionExpected()
  
## Phase 4: Task 3

- I would try to reduce coupling by removing the association between PlayQuizGUI and Quiz since PlayQuizGUI already has
an association with QuizEditorGUI, and QuizEditorGUI has an association with Quiz. Since PlayQuizGUI has an association 
  with QuizEditorGUI, it would be able to get the information it needed through QuizEditorGUI if I were to introduce 
  a getter.
- Although it isn't apparent on the UML diagram, I would also split the QuizApp into two classes to increase cohesion 
  because right now it has two responsibilities: making/editing the quiz, and playing through the quiz. If I were to 
  refactor it, I would split it similarly to how the two GUIs are split up. One class would be for making/editing the
  quiz, while a second class would actually play through the quiz game.