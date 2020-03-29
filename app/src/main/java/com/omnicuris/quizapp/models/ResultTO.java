package com.omnicuris.quizapp.models;

import java.io.Serializable;

public class ResultTO implements Serializable {
    private String questionNumber;
    private String correctAnswer;
    private String yourAnswer;
    private int mark = 0;

    public String getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(String questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getYourAnswer() {
        return yourAnswer;
    }

    public void setYourAnswer(String yourAnswer) {
        this.yourAnswer = yourAnswer;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "ResultTO{" +
                "questionNumber='" + questionNumber + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", yourAnswer='" + yourAnswer + '\'' +
                ", mark=" + mark +
                '}';
    }
}
